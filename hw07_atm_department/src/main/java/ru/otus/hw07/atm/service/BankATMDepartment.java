package ru.otus.hw07.atm.service;

import ru.otus.hw07.atm.model.ATM;

public interface BankATMDepartment {

    void addATMToDepartment(ATM atm);

    long totalSumInDepartment();

    void restoreInitialState();
}
