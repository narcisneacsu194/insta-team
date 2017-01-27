package com.teamtreehouse.instateam.web;
// This is a pojo used for the flash message that pops up whenever an action is done by the client, notifying the results.
// It also has an inner enum, that helps the view determine if it is a positive or an error message,
// and therefore take the styling decision.
public class FlashMessage {
    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status{
        SUCCESS, FAILURE
    }
}
