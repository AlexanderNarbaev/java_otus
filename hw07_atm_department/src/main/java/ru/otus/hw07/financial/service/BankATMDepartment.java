package ru.otus.hw07.financial.service;

public interface BankATMDepartment {

    void addATMToDepartment(BankATMService atm);

    long totalSumInDepartment();

    void restoreInitialState();
}
