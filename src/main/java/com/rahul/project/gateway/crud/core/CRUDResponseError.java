package com.rahul.project.gateway.crud.core;

import java.sql.Timestamp;

/**
 * This class holds all the required parameters to be sent in case error
 * occurred on server.
 *
 * @author Rahul Malhotra
 */
public class CRUDResponseError {

    private Timestamp occurredAt;
    private int code;
    private String message;
    private String description;

    public CRUDResponseError() {

    }

    public CRUDResponseError(CRUDResponseBuilder builder) {
        this.occurredAt = builder.occurredAt;
        this.code = builder.code;
        this.message = builder.message;
        this.description = builder.description;
    }

    public static CRUDResponseBuilder builder() {
        return new CRUDResponseBuilder();
    }

    public Timestamp getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(Timestamp occurredAt) {
        this.occurredAt = occurredAt;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CRUDResponseBuilder {

        private Timestamp occurredAt;
        private int code;
        private String message;
        private String description;

        public CRUDResponseError build() {
            return new CRUDResponseError(this);
        }

        public CRUDResponseError build(Timestamp occurredAt, int code) {
            this.occurredAt = occurredAt;
            this.code = code;
            return this.build();
        }

        public CRUDResponseError build(Timestamp occurredAt, String message) {
            this.occurredAt = occurredAt;
            this.message = message;
            return this.build();
        }

        public CRUDResponseError build(Timestamp occurredAt, int code, String message) {
            this.build(occurredAt, code);
            this.message = message;
            return this.build();
        }

        public CRUDResponseError build(Timestamp occurredAt, String message, String description) {
            this.build(occurredAt, message);
            this.description = description;
            return this.build();
        }

        public CRUDResponseError build(Timestamp occurredAt, int code, String message, String description) {
            this.build(occurredAt, message, description);
            this.code = code;
            return this.build();
        }

    }
}
