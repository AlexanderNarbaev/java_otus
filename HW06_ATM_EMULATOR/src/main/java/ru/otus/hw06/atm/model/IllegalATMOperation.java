package ru.otus.hw06.atm.model;

public class IllegalATMOperation extends Exception {
    public IllegalATMOperation(long value) {
        super("Недопустимая операция для банкомата/ячейки для значения:\t" + value);
    }
}
