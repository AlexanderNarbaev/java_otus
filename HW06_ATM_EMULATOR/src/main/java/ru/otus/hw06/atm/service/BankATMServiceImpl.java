package ru.otus.hw06.atm.service;

import ru.otus.hw06.atm.model.ATM;
import ru.otus.hw06.atm.model.ATMCassette;
import ru.otus.hw06.atm.model.InsufficientFundsException;
import ru.otus.hw06.atm.model.Nominal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.otus.hw06.atm.model.Nominal.*;

public class BankATMServiceImpl implements BankATMService {
    private final ATM myATM;

    public BankATMServiceImpl() {
        HashSet<ATMCassette> cassettes = new HashSet<>();
        cassettes.add(new ATMCassette(TEN));
        cassettes.add(new ATMCassette(FIFTY));
        cassettes.add(new ATMCassette(ONE_HUNDRED));
        cassettes.add(new ATMCassette(TWO_HUNDRED));
        cassettes.add(new ATMCassette(FIVE_HUNDRED));
        cassettes.add(new ATMCassette(ONE_THOUSAND));
        cassettes.add(new ATMCassette(TWO_THOUSAND));
        cassettes.add(new ATMCassette(FIVE_THOUSAND));
        this.myATM = new ATM(cassettes);
    }

    @Override
    public void addMoney(Map<Nominal, Long> banknotes) {
        for (ATMCassette cassette : myATM.getAtmCassettes()) {
            if (banknotes.containsKey(cassette.getNominal())) {
                cassette.setCurrentCapacity(cassette.getCurrentCapacity() + banknotes.get(cassette.getNominal()));
            }
        }
    }

    @Override
    public Map<Nominal, Long> getMoney(BigDecimal moneySum) throws InsufficientFundsException {
        HashMap<Nominal, Long> returnValue = new HashMap<>();
        Set<ATMCassette> atmCassettes = myATM.getAtmCassettes();

//        HashMap<Nominal, BigDecimal[]> optimalValue = new HashMap<>();
//        for (Nominal nominal : Nominal.values()) {
//            optimalValue.put(nominal, moneySum.divideAndRemainder(BigDecimal.valueOf(nominal.getValue())));
//        }
//        long additionalValueCount = 0;
//        BigDecimal sumReady = BigDecimal.ZERO;
//        for (Nominal optimum : optimalValue.keySet()) {
//            if (sumReady.compareTo(moneySum) != 0) {
//                BigDecimal[] optimumValue = optimalValue.get(optimum);
//                if (optimumValue != null) {
//                    if (optimumValue[0] != null && optimumValue[0].compareTo(BigDecimal.ZERO) > 0) {
//                        if (optimumValue[1] == null || optimumValue[1].compareTo(BigDecimal.ZERO) <= 0) {
//                            returnValue.put(optimum, optimumValue[0].longValue());
//                            sumReady.add(optimumValue[0].multiply(BigDecimal.valueOf(optimum.getValue())));
//                        } else {
//                            additionalValueCount = optimumValue[1].longValue();
//                        }
//                        returnValue.put(optimum, optimumValue[0].longValue() - additionalValueCount);
//                    }
//                }
//            }
//        }
        if (returnValue.isEmpty()) {
            throw new InsufficientFundsException();
        }
        return returnValue;
    }

    @Override
    public BigDecimal getCurrentMoneySum() {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (ATMCassette cassette : myATM.getAtmCassettes()) {
            totalSum = totalSum.add(BigDecimal.valueOf(cassette.getCurrentCapacity() * cassette.getNominal().getValue()));
        }
        return totalSum;
    }
}
