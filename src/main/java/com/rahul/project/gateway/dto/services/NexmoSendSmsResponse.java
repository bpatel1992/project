package com.rahul.project.gateway.dto.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
public class NexmoSendSmsResponse {

    @SerializedName("message-count")
    @Expose
    private Integer messageCount;

    @SerializedName("messages")
    @Expose
    private List<Messages> messages;

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }
}