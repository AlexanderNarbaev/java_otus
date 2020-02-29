package ru.otus.hw07.financial.cassette.api;

import ru.otus.hw07.financial.cassette.model.Cassette;

/**
 * Сервис управляет состоянием ячейки банкомат
 */
public interface CassetteStateManager<T extends Cassette> {
    /**
     * Сохранить измененное состояние ячейки
     *
     * @param cassetteState ячейка в новом соятнии
     */
    void saveCassetteState(T cassetteState);

    /**
     * Получить последнее сохраненное состояние ячейки
     *
     * @return ячейка в последнем сохраненном состоянии
     */
    T restoreCassetteState();

}
