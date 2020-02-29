package ru.otus.hw07.financial.cassette.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.otus.hw07.financial.model.Nominal;

@Setter
@Getter
@Builder
public class RussianRubbleCassette implements Cassette {
    private final Nominal nominal;
    private final long currentCapacity;

}
