package ru.otus.core.service;

import ru.otus.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    long persistAccount(Account account);

    Optional<Account> getAccount(long id);

}
