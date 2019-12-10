package ru.otus.hw06.atm.service;

import ru.otus.hw06.atm.model.InsufficientFundsException;
import ru.otus.hw06.atm.model.Nominal;

import java.io.Serializable;
import java.util.Map;

public interface BankATMService extends Serializable {

    void addMoney(Map<Nominal, Long> banknotes);

    Map<Nominal, Long> getMoney(long moneySum) throws InsufficientFundsException;

    long getCurrentMoneySum();
}
