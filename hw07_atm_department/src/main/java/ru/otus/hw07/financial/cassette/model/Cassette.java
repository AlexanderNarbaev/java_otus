package ru.otus.hw07.financial.cassette.model;

import ru.otus.hw07.financial.model.Nominal;

/**
 * Сущность представляющая собой ячейку банкомата
 */
public interface Cassette {
    /**
     * Получить номинал загружаемых в ячейку банкнот
     *
     * @return номнал, допустимый к загрузкев ячейку банкнот
     */
    Nominal getNominal();

    /**
     * Получить текущее количество банкнот, загруженное в ячейку
     * @return количество банкнот, загруженное в ячейку
     */
    long getCurrentCapacity();
}
