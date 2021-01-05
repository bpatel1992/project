package com.rahul.project.gateway.chat.service;


import com.google.common.collect.Lists;
import com.rahul.project.gateway.chat.exception.IsSameUserException;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.chat.dto.ChatChannelInitializationDTO;
import com.rahul.project.gateway.chat.dto.ChatMessageDTO;
import com.rahul.project.gateway.chat.dto.NotificationDTO;
import com.rahul.project.gateway.chat.dto.PageRequestDTO;
import com.rahul.project.gateway.chat.interfaces.IChatService;
import com.rahul.project.gateway.chat.model.ChatChannel;
import com.rahul.project.gateway.chat.model.ChatMessage;
import com.rahul.project.gateway.chat.repository.ChatChannelRepository;
import com.rahul.project.gateway.chat.repository.ChatMessageRepository;
import com.rahul.project.gateway.chat.utility.ChatMessageMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements IChatService {
  private ChatChannelRepository chatChannelRepository;

  private ChatMessageRepository chatMessageRepository;

  private ChatUserService userService;
  private AbstractDao abstractDao;
  
  private final int MAX_PAGABLE_CHAT_MESSAGES = 100;

  @Autowired
  public ChatService(
      ChatChannelRepository chatChannelRepository,
      ChatMessageRepository chatMessageRepository,
      ChatUserService userService,
      AbstractDao abstractDao) {
    this.chatChannelRepository = chatChannelRepository;
    this.chatMessageRepository = chatMessageRepository;
    this.userService = userService;
    this.abstractDao = abstractDao;
  }

  private String getExistingChannel(ChatChannelInitializationDTO chatChannelInitializationDTO) {
    List<ChatChannel> channel = chatChannelRepository
      .findExistingChannel(
        chatChannelInitializationDTO.getSenderId(),
        chatChannelInitializationDTO.getReceiverId()
      );
    
    return (channel != null && !channel.isEmpty()) ? channel.get(0).getChannelId().toString() : null;
  }

  private String newChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
      throws BeansException {
    ChatChannel channel = new ChatChannel(chatChannelInitializationDTO.getSenderId()
            ,chatChannelInitializationDTO.getReceiverId());
    chatChannelRepository.save(channel);
    return channel.getChannelId().toString();
  }

  public String establishChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
      throws IsSameUserException, BeansException {
    if (chatChannelInitializationDTO.getSenderId() == chatChannelInitializationDTO.getReceiverId()) {
      throw new IsSameUserException();
    }

    String uuid = getExistingChannel(chatChannelInitializationDTO);

    // If channel doesn't already exist, create a new one
    return (uuid != null) ? uuid : newChatSession(chatChannelInitializationDTO);
  }
  
  public void submitMessage(ChatMessageDTO chatMessageDTO)
      throws BeansException {
    ChatMessage chatMessage = ChatMessageMapper.mapChatDTOtoMessage(chatMessageDTO);
    abstractDao.saveOrUpdateEntity(chatMessage);
    userService.notifyUser(chatMessage.getReceiverId(),
      new NotificationDTO(
        "ChatMessageNotification",
              chatMessage.getSenderName() + " has sent you a message",
        chatMessage.getReceiverId()
      )
    );
  }
 
  public List<ChatMessageDTO> getExistingChatMessages(String channelUuid, PageRequestDTO pageRequestDTO) {
    ChatChannel channel = chatChannelRepository.getChannelDetails(channelUuid);
    List<ChatMessage> chatMessages =
      chatMessageRepository.getExistingChatMessages(
        channel.getSenderId(),
        channel.getReceiverId(),
        PageRequest.of(pageRequestDTO.getPageNo(), pageRequestDTO.getPageSize())
      );

    // TODO: fix this
    List<ChatMessage> messagesByLatest = Lists.reverse(chatMessages);

    return ChatMessageMapper.mapMessagesToChatDTOs(messagesByLatest);
  }
}