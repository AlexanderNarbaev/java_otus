package ru.otus.hw07.atm.service;

import ru.otus.hw07.atm.model.IllegalATMOperation;
import ru.otus.hw07.atm.model.InsufficientFundsException;
import ru.otus.hw07.atm.model.Nominal;

import java.io.Serializable;
import java.util.Map;

/**
 * Взаимодействие с банкоматом
 */
public interface BankATMService extends Serializable {

    /**
     * Добавить денег в банкомат
     *
     * @param banknotes карта ключ значение - номинал/количество
     */
    void addMoney(Map<Nominal, Long> banknotes);

    /**
     * Получить деньги
     *
     * @param moneySum требуемая сумма
     * @return карта ключ значение - номинал/количество
     * @throws InsufficientFundsException выбрасывается, когда в банкомате недостаточно средств для исполнения запроса
     * @throws IllegalATMOperation        когда сумма запрашиваемых средств меньше либо равно 0
     */
    Map<Nominal, Long> getMoney(long moneySum) throws InsufficientFundsException, IllegalATMOperation;

    /**
     * Получить сумму денег в банкомате
     *
     * @return общая сумма денег во всех кассетах банкомата
     */
    long getCurrentMoneySum();

    /**
     * Восстановить исходное состояние банкомата
     */
    void restoreATMInitialState();
}
