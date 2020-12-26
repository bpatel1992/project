package com.rahul.project.gateway.utility;



import com.rahul.project.gateway.dto.chat.ChatMessageDTO;
import com.rahul.project.gateway.model.ChatMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ChatMessageMapper {

  private static DateTimeFormatter timeFormatter
          = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

  public static List<ChatMessageDTO> mapMessagesToChatDTOs(List<ChatMessage> chatMessages) {
    List<ChatMessageDTO> dtos = new ArrayList<>();
    Calendar time = Calendar.getInstance();
    LocalDateTime localDateTime = null;

    for(ChatMessage chatMessage : chatMessages) {

      time.setTime(chatMessage.getTimeSent());
      localDateTime = LocalDateTime.ofInstant(time.toInstant(),time.getTimeZone().toZoneId());

      dtos.add(
        new ChatMessageDTO(
          chatMessage.getSenderId(),chatMessage.getSenderName(),
          chatMessage.getReceiverName(),chatMessage.getReceiverId(),
          chatMessage.getContents(),
          localDateTime.toLocalDate().toString(),
          localDateTime.toLocalTime().format(timeFormatter)
        )
      );
    }

    return dtos;
  }

  public static ChatMessage mapChatDTOtoMessage(ChatMessageDTO dto) {
    return new ChatMessage(dto.getSenderId(),dto.getSenderName(),
            dto.getReceiverName(),dto.getReceiverId(),
            dto.getMessage());
  }
}
