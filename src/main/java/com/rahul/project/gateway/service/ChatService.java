package com.rahul.project.gateway.service;


import com.google.common.collect.Lists;
import com.rahul.project.gateway.configuration.IsSameUserException;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.chat.ChatChannelInitializationDTO;
import com.rahul.project.gateway.dto.chat.ChatMessageDTO;
import com.rahul.project.gateway.dto.chat.NotificationDTO;
import com.rahul.project.gateway.dto.chat.PageRequestDTO;
import com.rahul.project.gateway.interfaces.IChatService;
import com.rahul.project.gateway.model.ChatChannel;
import com.rahul.project.gateway.model.ChatMessage;
import com.rahul.project.gateway.repository.ChatChannelRepository;
import com.rahul.project.gateway.repository.ChatMessageRepository;
import com.rahul.project.gateway.utility.ChatMessageMapper;
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