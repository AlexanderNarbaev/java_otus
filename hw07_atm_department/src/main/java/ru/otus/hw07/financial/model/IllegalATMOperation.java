package ru.otus.hw07.financial.model;

public class IllegalATMOperation extends ATMException {
    public IllegalATMOperation(long value) {
        super("Недопустимая операция для банкомата/ячейки для значения:\t" + value);
    }
}
