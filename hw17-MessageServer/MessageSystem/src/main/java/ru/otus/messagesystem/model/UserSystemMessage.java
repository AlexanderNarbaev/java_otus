package ru.otus.messagesystem.model;

import ru.otus.messagesystem.protobuf.generated.UserMessage;

import java.util.Objects;

public class UserSystemMessage extends ResultDataType {

    private UserMessage user;

    public UserSystemMessage(UserMessage user) {
        this.user = user;
    }

    public UserMessage getUser() {
        return user;
    }

    public void setUser(UserMessage user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemMessage)) return false;
        UserSystemMessage that = (UserSystemMessage) o;
        return getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser());
    }

    @Override
    public String toString() {
        return "UserSystemMessage{" +
                "user=" + user +
                '}';
    }
}
