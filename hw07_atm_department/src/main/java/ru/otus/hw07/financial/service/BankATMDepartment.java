package ru.otus.hw07.financial.service;

import ru.otus.hw07.financial.model.ATM;

public interface BankATMDepartment {

    void addATMToDepartment(ATM atm);

    long totalSumInDepartment();

    void restoreInitialState();
}
