package com.rahul.project.gateway.chat.service;


import com.rahul.project.gateway.chat.dto.NotificationDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class ChatUserService {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  
  @Autowired
  private BeanFactory beanFactory;


  public void notifyUser(Long receiverId, NotificationDTO notification) {
     /* simpMessagingTemplate
        .convertAndSend("/queue/private.chat/" + receiverId, notification);*/
  }
}