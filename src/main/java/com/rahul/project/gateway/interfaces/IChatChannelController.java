package com.rahul.project.gateway.interfaces;

import com.rahul.project.gateway.configuration.IsSameUserException;
import com.rahul.project.gateway.dto.chat.ChatChannelInitializationDTO;
import com.rahul.project.gateway.dto.chat.ChatMessageDTO;
import com.rahul.project.gateway.dto.chat.PageRequestDTO;
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
