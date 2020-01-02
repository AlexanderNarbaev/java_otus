package ru.otus.hw07.atm.model;

/**
 * Сущность представляющая собой ячейку банкомата
 */
public interface Cassette {

    /**
     * Добавить в ячейку банкнот
     *
     * @param banknotesCount - количество добавляемых банкнот
     * @throws IllegalATMOperation - исключение при недопустимости операции, если число добавляемых банкнот меньше либо равно 0
     */
    void addBanknotes(long banknotesCount) throws IllegalATMOperation;

    /**
     * Получить банкноты из ячейки
     *
     * @param banknotesCount количество банкнот для выдачи
     * @throws InsufficientFundsException выбрасывается, когда в ячейке недостаточно банкнот для исполнения запроса
     * @throws IllegalATMOperation        когда число запрашиваемых банкнот меньше либо равно 0
     */
    void getBanknotes(long banknotesCount) throws InsufficientFundsException, IllegalATMOperation;

    /**
     * Восстановить исходное состояние ячейки
     */
    void restoreInitialState();

    /**
     * Получить число банкнот в ячейке
     *
     * @return количество банкнот, находящееся в ячейке
     */
    long getCurrentCapacity();
}
