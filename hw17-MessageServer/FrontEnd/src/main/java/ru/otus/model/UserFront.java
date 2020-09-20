package ru.otus.model;

import java.io.Serializable;
import java.util.Objects;

public class UserFront implements Serializable {
    private long id;
    private String name;
    private String login;
    private String password;

    public UserFront() {
    }

    public UserFront(long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFront)) return false;
        UserFront userFront = (UserFront) o;
        return getId() == userFront.getId() &&
                getName().equals(userFront.getName()) &&
                getLogin().equals(userFront.getLogin()) &&
                getPassword().equals(userFront.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return "UserFront{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
