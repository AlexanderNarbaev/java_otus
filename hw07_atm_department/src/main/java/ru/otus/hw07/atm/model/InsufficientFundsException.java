package ru.otus.hw07.atm.model;

public class InsufficientFundsException extends ATMException {
    public InsufficientFundsException(long value) {
        super("Невозможно выдать требуемую сумму:\t" + value);
    }
}
