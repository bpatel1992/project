package com.rahul.project.gateway.chat.controller;


import com.rahul.project.gateway.chat.service.ChatService;
import com.rahul.project.gateway.chat.exception.IsSameUserException;
import com.rahul.project.gateway.chat.dto.ChatChannelInitializationDTO;
import com.rahul.project.gateway.chat.dto.ChatMessageDTO;
import com.rahul.project.gateway.chat.dto.EstablishedChatChannelDTO;
import com.rahul.project.gateway.chat.dto.PageRequestDTO;
import com.rahul.project.gateway.chat.interfaces.IChatChannelController;
import com.rahul.project.gateway.chat.utility.JSONResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Fetch channel details by senderId and receiverId, " + "\n" +
                "2. Fetch chat message by channelId, pageNo and pageSize",tags = { "Chat services" })
public class ChatChannelController implements IChatChannelController {

    private ChatService chatService;

    @Autowired
    public ChatChannelController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/private.chat/{channelId}")
    @SendTo("/queue/private.chat/{channelId}")
    public ChatMessageDTO chatMessage(@DestinationVariable String channelId, @RequestBody ChatMessageDTO message)
            throws BeansException {
        chatService.submitMessage(message);
        return message;
    }

    @ApiOperation(value = "fetch channel details by senderId and receiverId", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/private-chat/channel", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationDTO chatChannelInitialization)
            throws IsSameUserException {
        String channelUuid = chatService.establishChatSession(chatChannelInitialization);
        EstablishedChatChannelDTO establishedChatChannel = new EstablishedChatChannelDTO(
                channelUuid, chatChannelInitialization.getSenderId(), chatChannelInitialization.getSenderName(),
                chatChannelInitialization.getReceiverId(), chatChannelInitialization.getReceiverName());

        return JSONResponseHelper.createResponse(establishedChatChannel, HttpStatus.OK);
    }

    @ApiOperation(value = "fetch chat message by channelId, pageNo and pageSize", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(value = "/oauth2/api/private-chat/channel/{channelUuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid, @RequestBody PageRequestDTO pageRequestDTO) {
        List<ChatMessageDTO> messages = chatService.getExistingChatMessages(channelUuid, pageRequestDTO);

        return JSONResponseHelper.createResponse(messages, HttpStatus.OK);
    }

}