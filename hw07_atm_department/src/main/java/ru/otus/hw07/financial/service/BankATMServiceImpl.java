package ru.otus.hw07.financial.service;

import ru.otus.hw07.financial.cassette.api.CassetteService;
import ru.otus.hw07.financial.cassette.api.CassetteServiceImpl;
import ru.otus.hw07.financial.model.ATM;
import ru.otus.hw07.financial.model.IllegalATMOperation;
import ru.otus.hw07.financial.model.InsufficientFundsException;
import ru.otus.hw07.financial.model.Nominal;

import java.util.*;

import static ru.otus.hw07.financial.model.Nominal.*;

public class BankATMServiceImpl implements BankATMService {
    private final ATM myATM;

    public BankATMServiceImpl() {
        ArrayList<CassetteService> cassettes = new ArrayList<>();
        cassettes.add(new CassetteServiceImpl(TEN));
        cassettes.add(new CassetteServiceImpl(FIFTY));
        cassettes.add(new CassetteServiceImpl(ONE_HUNDRED, 0));
        cassettes.add(new CassetteServiceImpl(TWO_HUNDRED, 0));
        cassettes.add(new CassetteServiceImpl(FIVE_HUNDRED, 0));
        cassettes.add(new CassetteServiceImpl(ONE_THOUSAND, 0));
        cassettes.add(new CassetteServiceImpl(TWO_THOUSAND, 0));
        cassettes.add(new CassetteServiceImpl(FIVE_THOUSAND, 0));
        Collections.sort(cassettes, new Comparator<CassetteService>() {
            @Override
            public int compare(CassetteService first, CassetteService second) {
                return second.getNominal().compareTo(first.getNominal());
            }
        });
        this.myATM = new ATM(cassettes);
    }

    @Override
    public void addMoney(Map<Nominal, Long> banknotes) {
        for (CassetteService cassette : myATM.getAtmCassettes()) {
            if (banknotes.containsKey(cassette.getNominal())) {
                try {
                    cassette.addBanknotes(banknotes.get(cassette.getNominal()));
                } catch (IllegalATMOperation illegalATMOperation) {
                    illegalATMOperation.printStackTrace();
                }
            }
        }
    }

    @Override
    public Map<Nominal, Long> getMoney(long moneySum) throws InsufficientFundsException, IllegalATMOperation {
        HashMap<Nominal, Long> returnValue = new HashMap<>();
        HashMap<CassetteService, Long> reduceCassettes = new HashMap<>();
        for (CassetteService cassette : myATM.getAtmCassettes()) {
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
                    } else throw new InsufficientFundsException(moneySum);
                }
            }
        }
        if (returnValue.isEmpty()) {
            throw new InsufficientFundsException(moneySum);
        }
        for (CassetteService cassette : reduceCassettes.keySet()) {
            try {
                myATM.getAtmCassettes().get(myATM.getAtmCassettes().indexOf(cassette)).getBanknotes(reduceCassettes.get(cassette));
            } catch (IllegalATMOperation | InsufficientFundsException e) {
                throw e;
            }
        }
        return returnValue;
    }

    @Override
    public long getCurrentMoneySum() {
        long totalSum = 0L;
        for (CassetteService cassette : myATM.getAtmCassettes()) {
            totalSum += (cassette.getCurrentCapacity() * cassette.getNominal().getValue());
        }
        return totalSum;
    }

    @Override
    public void restoreATMInitialState() {

    }
}
