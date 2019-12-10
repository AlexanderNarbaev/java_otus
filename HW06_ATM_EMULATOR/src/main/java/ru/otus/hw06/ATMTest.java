package ru.otus.hw06;

import ru.otus.hw06.atm.model.InsufficientFundsException;
import ru.otus.hw06.atm.model.Nominal;
import ru.otus.hw06.atm.service.BankATMService;
import ru.otus.hw06.atm.service.BankATMServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

public class ATMTest {
    public static void main(String[] args) {
        BankATMService atmService = new BankATMServiceImpl();
        HashMap<Nominal, Long> banknotes = new HashMap<>();
        Random randomizer = new Random();
        banknotes.put(Nominal.TEN, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.FIFTY, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.ONE_HUNDRED, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.TWO_HUNDRED, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.FIVE_HUNDRED, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.ONE_THOUSAND, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.TWO_THOUSAND, (long) randomizer.nextInt(10));
        banknotes.put(Nominal.FIVE_THOUSAND, (long) randomizer.nextInt(10));
        atmService.addMoney(banknotes);
        System.out.println(atmService.getCurrentMoneySum());
        try {
            atmService.getMoney(BigDecimal.valueOf(randomizer.nextInt(100)));
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
        }
        System.out.println(atmService.getCurrentMoneySum());
    }
}
