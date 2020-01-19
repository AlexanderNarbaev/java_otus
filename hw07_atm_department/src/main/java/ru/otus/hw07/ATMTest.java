package ru.otus.hw07;

import ru.otus.hw07.financial.model.IllegalATMOperation;
import ru.otus.hw07.financial.model.InsufficientFundsException;
import ru.otus.hw07.financial.model.Nominal;
import ru.otus.hw07.financial.service.BankATMService;
import ru.otus.hw07.financial.service.BankATMServiceImpl;

import java.util.HashMap;
import java.util.Random;

public class ATMTest {
    public static void main(String[] args) {
        BankATMService atmService = new BankATMServiceImpl();
        atmService.addMoney(loadBankNotes(2));
        atmService.addMoney(loadBankNotes(3));
        atmService.addMoney(loadBankNotes(4));
        System.out.println("Summary at start to work:\t" + atmService.getCurrentMoneySum());
        try {
            for (int i = 0; i < 10; i++) {
                int requestedSum = new Random().nextInt(1000) * 10;
                System.out.println("Requested sum:\t" + requestedSum);
                atmService.getMoney(requestedSum);
                System.out.println("Current summary:\t" + atmService.getCurrentMoneySum());
            }

        } catch (InsufficientFundsException | IllegalATMOperation e) {
            e.printStackTrace();
        }
        System.out.println("Summary at an end of work:\t" + atmService.getCurrentMoneySum());
    }

    private static HashMap<Nominal, Long> loadBankNotes(int bound) {
        HashMap<Nominal, Long> banknotes = new HashMap<>();
        Random randomizer = new Random();
        banknotes.put(Nominal.TEN, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.FIFTY, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.ONE_HUNDRED, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.TWO_HUNDRED, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.FIVE_HUNDRED, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.ONE_THOUSAND, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.TWO_THOUSAND, (long) randomizer.nextInt(bound));
        banknotes.put(Nominal.FIVE_THOUSAND, (long) randomizer.nextInt(bound));
        return banknotes;
    }
}
