package ru.otus.hw07.financial.cassette.api;

import ru.otus.hw07.financial.cassette.model.RussianRubbleCassette;
import ru.otus.hw07.financial.cassette.model.RussianRubbleCassetteStateImpl;

import java.util.ArrayDeque;
import java.util.Deque;

public class RussianRubbleCassetteStateManagerImpl implements CassetteStateManager<RussianRubbleCassette> {

    private final Deque<RussianRubbleCassetteStateImpl> cassetteStates = new ArrayDeque<>();

    @Override
    public void saveCassetteState(RussianRubbleCassette cassetteState) {
        cassetteStates.push(new RussianRubbleCassetteStateImpl(cassetteState));
    }

    @Override
    public RussianRubbleCassette restoreCassetteState() {
        return cassetteStates.pop().getCassette();
    }
}
