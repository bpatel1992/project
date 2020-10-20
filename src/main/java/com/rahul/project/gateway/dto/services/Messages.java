package com.rahul.project.gateway.dto.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author rahul malhotra
 * date 2019-05-21
 */
public class Messages {
    @SerializedName("message-price")
    @Expose
    private Double message_price;
    @SerializedName("message-id")
    @Expose
    private String message_id;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("remaining-balance")
    @Expose
    private Double remaining_balance;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("network")
    @Expose
    private Integer network;

    @SerializedName("error-text")
    @Expose
    private String errorText;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNetwork() {
        return network;
    }

    public void setNetwork(Integer network) {
        this.network = network;
    }

    public Double getMessage_price() {
        return message_price;
    }

    public void setMessage_price(Double message_price) {
        this.message_price = message_price;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public Double getRemaining_balance() {
        return remaining_balance;
    }

    public void setRemaining_balance(Double remaining_balance) {
        this.remaining_balance = remaining_balance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
