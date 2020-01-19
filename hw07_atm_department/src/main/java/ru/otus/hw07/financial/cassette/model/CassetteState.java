package ru.otus.hw07.financial.cassette.model;

/**
 * Класс-маркер объекта - хранящего состояния своей ячейки
 */
public interface CassetteState<T extends Cassette> {
    /**
     * Получить хранимую ячейку
     *
     * @return ячейка, которая хранится в определенном состоянии
     */
    T getCassette();
}
