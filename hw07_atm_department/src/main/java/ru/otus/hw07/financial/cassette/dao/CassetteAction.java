package ru.otus.hw07.financial.cassette.dao;

import ru.otus.hw07.financial.cassette.model.Cassette;
import ru.otus.hw07.financial.model.ATMException;

/**
 * Действие над кассетой
 */
public interface CassetteAction<T extends Cassette> {
    /**
     * Произвести дествие над кпереданной ячейкой
     *
     * @param cassetteToDoAction ячейка, для произведения действия
     * @return ячейка, над которой было произведено действие
     * @throws ATMException - ошибка при невозможности совершить действие
     */
    T doCassetteAction(T cassetteToDoAction) throws ATMException;
}
