package ru.otus.hw06.atm.service;

import ru.otus.hw06.atm.model.InsufficientFundsException;
import ru.otus.hw06.atm.model.Nominal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public interface BankATMService extends Serializable {

    void addMoney(Map<Nominal, Long> banknotes);

    Map<Nominal, Long> getMoney(BigDecimal moneySum) throws InsufficientFundsException;

    BigDecimal getCurrentMoneySum();
}
