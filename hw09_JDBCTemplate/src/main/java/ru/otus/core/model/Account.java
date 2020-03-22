package ru.otus.core.model;

import ru.otus.jdbc.template.Id;

public class Account {
    @Id
    private final long no;
    private final String type;
    private final Number rest;

    public Account(long no, String type, Number rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public Number getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
