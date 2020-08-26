package ru.otus.messagesystem;

import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagesystem.model.ResultDataType;

public interface HandlersStore {
    RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName);

    void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler);
}
