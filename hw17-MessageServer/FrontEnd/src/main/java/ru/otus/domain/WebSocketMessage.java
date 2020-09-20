package ru.otus.domain;

import ru.otus.model.UserFront;

import java.util.List;

public class WebSocketMessage {
    private List<UserFront> messageStr;

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "messageStr=" + messageStr +
                '}';
    }

    public List<UserFront> getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(List<UserFront> messageStr) {
        this.messageStr = messageStr;
    }

    public WebSocketMessage() {
    }

    public WebSocketMessage(List<UserFront> messageStr) {
        this.messageStr = messageStr;
    }
}