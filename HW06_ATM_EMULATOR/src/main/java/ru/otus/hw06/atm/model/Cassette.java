package ru.otus.hw06.atm.model;

public interface Cassette {

    void addBanknotes(long banknotesCount) throws IllegalATMOperation;

    void getBanknotes(long banknotesCount) throws InsufficientFundsException, IllegalATMOperation;
}
