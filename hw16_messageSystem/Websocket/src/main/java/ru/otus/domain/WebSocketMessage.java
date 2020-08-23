package ru.otus.domain;

import ru.otus.model.User;

import java.util.List;

public class WebSocketMessage {
    private List<User> messageStr;

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "messageStr=" + messageStr +
                '}';
    }

    public List<User> getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(List<User> messageStr) {
        this.messageStr = messageStr;
    }

    public WebSocketMessage() {
    }

    public WebSocketMessage(List<User> messageStr) {
        this.messageStr = messageStr;
    }
}