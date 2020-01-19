package ru.otus.hw07.financial.cassette.model;

public class RussianRubbleCassetteStateImpl implements CassetteState<RussianRubbleCassette> {
    private final RussianRubbleCassette state;

    public RussianRubbleCassetteStateImpl(RussianRubbleCassette state) {
        this.state = RussianRubbleCassette.builder().currentCapacity(state.getCurrentCapacity()).nominal(state.getNominal()).build();
    }

    public RussianRubbleCassette getCassette() {
        return state;
    }
}
