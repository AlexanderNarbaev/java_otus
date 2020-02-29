package ru.otus.hw07;

import lombok.extern.log4j.Log4j2;
import ru.otus.hw07.financial.model.IllegalATMOperation;
import ru.otus.hw07.financial.model.InsufficientFundsException;
import ru.otus.hw07.financial.model.Nominal;
import ru.otus.hw07.financial.service.BankATMDepartment;
import ru.otus.hw07.financial.service.BankATMDepartmentImpl;
import ru.otus.hw07.financial.service.BankATMService;
import ru.otus.hw07.financial.service.BankATMServiceImpl;

import java.util.HashMap;
import java.util.Random;

@Log4j2
public class DepartmentTest {
    public static void main(String[] args) {
        BankATMDepartment department = new BankATMDepartmentImpl();
        for (int i = 0; i < 10; i++) {
            BankATMService atmService = createATM();
            department.addATMToDepartment(atmService);
            Runnable atmLifeCycleThread = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Summary at start to work:\t" + atmService.getCurrentMoneySum());
                    try {
                        for (int i = 0; i < 10; i++) {
                            int requestedSum = new Random().nextInt(1000) * 10;
                            System.out.println("Requested sum:\t" + requestedSum);
                            atmService.getMoney(requestedSum);
                            System.out.println("Current summary:\t" + atmService.getCurrentMoneySum());
                        }

                    } catch (InsufficientFundsException | IllegalATMOperation e) {
                        log.error("Ошибка при работе с банкоматом", e);
                    }
                    System.out.println("Summary at an end of work:\t" + atmService.getCurrentMoneySum());
                }
            };
            atmLifeCycleThread.run();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Ошибка при попытке остановки основного потока выполнения программы", e);
            }
            System.out.println("Current sum money in ATM Department:\t " + department.totalSumInDepartment());
        }
        department.restoreInitialState();
        System.out.println("Current sum money in ATM Department after restore:\t " + department.totalSumInDepartment());

    }

    private static BankATMService createATM() {
        BankATMService atmService = new BankATMServiceImpl();
        atmService.addMoney(loadBankNotes(2));
        atmService.addMoney(loadBankNotes(3));
        atmService.addMoney(loadBankNotes(4));
        return atmService;
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
