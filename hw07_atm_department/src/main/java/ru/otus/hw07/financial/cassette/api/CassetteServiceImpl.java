package ru.otus.hw07.financial.cassette.api;

import lombok.extern.log4j.Log4j2;
import ru.otus.hw07.financial.cassette.dao.AddBanknotesAction;
import ru.otus.hw07.financial.cassette.dao.GetBanknotesAction;
import ru.otus.hw07.financial.cassette.model.RussianRubbleCassette;
import ru.otus.hw07.financial.model.ATMException;
import ru.otus.hw07.financial.model.IllegalATMOperation;
import ru.otus.hw07.financial.model.InsufficientFundsException;
import ru.otus.hw07.financial.model.Nominal;

@Log4j2
public class CassetteServiceImpl implements CassetteService<RussianRubbleCassette> {

    private final CassetteStateManager stateManager;
    private RussianRubbleCassette cassette;

    public CassetteServiceImpl(Nominal nominal) {
        this(nominal, 0);
    }

    public CassetteServiceImpl(Nominal nominal, int initialCapacity) {
        this.stateManager = new RussianRubbleCassetteStateManagerImpl();
        cassette = RussianRubbleCassette.builder().nominal(nominal).currentCapacity(initialCapacity).build();
    }

    @Override
    public void addBanknotes(long banknotesCount) throws IllegalATMOperation {
        AddBanknotesAction addBanknotesAction = new AddBanknotesAction(stateManager, banknotesCount);
        try {
            cassette = addBanknotesAction.doCassetteAction(cassette);
        } catch (ATMException e) {
            log.error("", e);
        }
    }

    @Override
    public void getBanknotes(long banknotesCount) throws InsufficientFundsException, IllegalATMOperation {
        GetBanknotesAction getBanknotesAction = new GetBanknotesAction(stateManager, banknotesCount);
        try {
            cassette = getBanknotesAction.doCassetteAction(cassette);
        } catch (ATMException e) {
            log.error("", e);
        }
    }

    @Override
    public void restoreInitialState() {

    }

    @Override
    public long getCurrentCapacity() {
        return cassette.getCurrentCapacity();
    }

    @Override
    public Nominal getNominal() {
        return cassette.getNominal();
    }
}
