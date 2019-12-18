package ru.otus.hw06.atm.model;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(long value) {
        super("Невозможно выдать требуемую сумму:\t" + value);
    }
}
