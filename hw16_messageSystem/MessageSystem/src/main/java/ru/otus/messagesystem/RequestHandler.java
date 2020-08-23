package ru.otus.messagesystem;

import ru.otus.messagesystem.message.Message;
import ru.otus.model.ResultDataType;

import java.util.Optional;


public interface RequestHandler<T extends ResultDataType> {
    Optional<Message> handle(Message msg);
}
