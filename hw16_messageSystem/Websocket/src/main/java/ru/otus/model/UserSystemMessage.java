package ru.otus.model;

import ru.otus.messagesystem.model.ResultDataType;

import java.util.Objects;

public class UserSystemMessage extends ResultDataType {

    private User user;

    public UserSystemMessage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSystemMessage)) return false;
        UserSystemMessage that = (UserSystemMessage) o;
        return Objects.equals(getUser(), that.getUser());
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
