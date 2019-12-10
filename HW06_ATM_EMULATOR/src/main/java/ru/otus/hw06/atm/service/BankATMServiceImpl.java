package ru.otus.hw06.atm.service;

import ru.otus.hw06.atm.model.ATM;
import ru.otus.hw06.atm.model.ATMCassette;
import ru.otus.hw06.atm.model.InsufficientFundsException;
import ru.otus.hw06.atm.model.Nominal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.hw06.atm.model.Nominal.*;

public class BankATMServiceImpl implements BankATMService {
    private final ATM myATM;

    public BankATMServiceImpl() {
        ArrayList<ATMCassette> cassettes = new ArrayList<>();
        cassettes.add(new ATMCassette(TEN));
        cassettes.add(new ATMCassette(FIFTY));
        cassettes.add(new ATMCassette(ONE_HUNDRED));
        cassettes.add(new ATMCassette(TWO_HUNDRED));
        cassettes.add(new ATMCassette(FIVE_HUNDRED));
        cassettes.add(new ATMCassette(ONE_THOUSAND));
        cassettes.add(new ATMCassette(TWO_THOUSAND));
        cassettes.add(new ATMCassette(FIVE_THOUSAND));
        Collections.sort(cassettes);
        this.myATM = new ATM(cassettes);
    }

    @Override
    public void addMoney(Map<Nominal, Long> banknotes) {
        for (ATMCassette cassette : myATM.getAtmCassettes()) {
            if (banknotes.containsKey(cassette.getNominal())) {
                cassette.addBanknotes(banknotes.get(cassette.getNominal()));
            }
        }
    }

    @Override
    public Map<Nominal, Long> getMoney(long moneySum) throws InsufficientFundsException {
        HashMap<Nominal, Long> returnValue = new HashMap<>();
        HashMap<ATMCassette, Long> reduceCassettes = new HashMap<>();
        for (ATMCassette cassette : myATM.getAtmCassettes()) {
            int nominalValue = cassette.getNominal().getValue();
            if (nominalValue <= moneySum) {
                long wholeCount = moneySum / nominalValue;
                if (moneySum % nominalValue == 0) {
                    if (cassette.getCurrentCapacity() >= wholeCount) {
                        returnValue.put(cassette.getNominal(), wholeCount);
                        reduceCassettes.put(cassette, wholeCount);
                        break;
                    }
                } else {
                    if (cassette.getCurrentCapacity() >= wholeCount) {
                        returnValue.put(cassette.getNominal(), wholeCount);
                        reduceCassettes.put(cassette, wholeCount);
                        moneySum -= wholeCount * nominalValue;
                        break;
                    } else throw new InsufficientFundsException();
                }
            }
        }
        if (returnValue.isEmpty()) {
            throw new InsufficientFundsException();
        }
        for (ATMCassette cassette : reduceCassettes.keySet()) {
            myATM.getAtmCassettes().get(myATM.getAtmCassettes().indexOf(cassette)).getBanknotes(reduceCassettes.get(cassette));
        }
        return returnValue;
    }

    @Override
    public long getCurrentMoneySum() {
        long totalSum = 0L;
        for (ATMCassette cassette : myATM.getAtmCassettes()) {
            totalSum += (cassette.getCurrentCapacity() * cassette.getNominal().getValue());
        }
        return totalSum;
    }
}
