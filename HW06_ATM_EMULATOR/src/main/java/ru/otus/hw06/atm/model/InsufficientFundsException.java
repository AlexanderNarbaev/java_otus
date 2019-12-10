package ru.otus.hw06.atm.model;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException() {
        super("Невозможно выдать требуемую сумму");
    }
}
