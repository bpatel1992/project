package com.rahul.project.gateway.interfaces;


import com.rahul.project.gateway.configuration.IsSameUserException;
import com.rahul.project.gateway.dto.chat.ChatChannelInitializationDTO;
import com.rahul.project.gateway.dto.chat.ChatMessageDTO;
import com.rahul.project.gateway.dto.chat.PageRequestDTO;
import org.springframework.beans.BeansException;

import java.util.List;

public interface IChatService {
  String establishChatSession(ChatChannelInitializationDTO chatChannelInitializationDTO)
      throws IsSameUserException, BeansException;

  void submitMessage(ChatMessageDTO chatMessageDTO)
      throws BeansException;
  
  List<ChatMessageDTO> getExistingChatMessages(String channelUuid, PageRequestDTO pageRequestDTO);
}