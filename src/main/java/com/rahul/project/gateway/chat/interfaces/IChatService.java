package com.rahul.project.gateway.chat.interfaces;


import com.rahul.project.gateway.chat.exception.IsSameUserException;
import com.rahul.project.gateway.chat.dto.ChatChannelInitializationDTO;
import com.rahul.project.gateway.chat.dto.ChatMessageDTO;
import com.rahul.project.gateway.chat.dto.PageRequestDTO;
import org.springframework.beans.BeansException;

import java.util.List;

public interface IChatService {
  String establishChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
      throws IsSameUserException, BeansException;

  void submitMessage(ChatMessageDTO chatMessageDTO)
      throws BeansException;
  
  List<ChatMessageDTO> getExistingChatMessages(String channelUuid, PageRequestDTO pageRequestDTO);
}