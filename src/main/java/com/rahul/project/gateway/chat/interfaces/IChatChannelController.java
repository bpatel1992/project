package com.rahul.project.gateway.chat.interfaces;

import com.rahul.project.gateway.chat.exception.IsSameUserException;
import com.rahul.project.gateway.chat.dto.ChatChannelInitializationDTO;
import com.rahul.project.gateway.chat.dto.ChatMessageDTO;
import com.rahul.project.gateway.chat.dto.PageRequestDTO;
import org.springframework.beans.BeansException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IChatChannelController {
    ChatMessageDTO chatMessage(@DestinationVariable String channelId, ChatMessageDTO message)
        throws BeansException;

    ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationDTO chatChannelInitialization)
        throws IsSameUserException;

    ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid, PageRequestDTO pageRequestDTO);
}
