package ru.otus.messagesystem.client;

import ru.otus.model.ResultDataType;

import java.util.function.Consumer;

public interface MessageCallback<T extends ResultDataType> extends Consumer<T> {
}
