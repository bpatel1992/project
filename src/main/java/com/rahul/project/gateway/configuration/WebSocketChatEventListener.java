package com.rahul.project.gateway.configuration;

import com.rahul.project.gateway.dto.chat.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketChatEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String channelId = (String) headerAccessor.getSessionAttributes().get("channelId");
        String username = (String) headerAccessor.getSessionAttributes().get("senderName");
        if(channelId != null) {

            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setType("Leave");
            chatMessage.setSenderName(username);

            messagingTemplate.convertAndSend("/secured/room/queue/private.chat."+channelId, chatMessage);
        }
    }
}
