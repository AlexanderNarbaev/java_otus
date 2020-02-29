package ru.otus.hw07.financial.cassette.dao;

import ru.otus.hw07.financial.cassette.api.CassetteStateManager;
import ru.otus.hw07.financial.cassette.model.RussianRubbleCassette;
import ru.otus.hw07.financial.model.ATMException;
import ru.otus.hw07.financial.model.IllegalATMOperation;

public class AddBanknotesAction implements CassetteAction<RussianRubbleCassette> {
    private final CassetteStateManager stateManager;
    private final long banknotesCount;

    public AddBanknotesAction(CassetteStateManager stateManager, long banknotesCount) {
        this.stateManager = stateManager;
        this.banknotesCount = banknotesCount;
    }

    @Override
    public RussianRubbleCassette doCassetteAction(RussianRubbleCassette cassetteToDoAction) throws ATMException {
        if (banknotesCount > 0) {
            RussianRubbleCassette russianRubbleCassette = RussianRubbleCassette.builder()
                    .currentCapacity(cassetteToDoAction.getCurrentCapacity() + banknotesCount)
                    .nominal(cassetteToDoAction.getNominal()).build();
            stateManager.saveCassetteState(russianRubbleCassette);
            return russianRubbleCassette;
        } else throw new IllegalATMOperation(banknotesCount);
    }
}
