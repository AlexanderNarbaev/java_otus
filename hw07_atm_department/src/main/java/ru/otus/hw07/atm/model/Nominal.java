package ru.otus.hw07.atm.model;

/**
 * Номинал купюр
 */
public enum Nominal implements Comparable<Nominal> {
    TEN("Десять", 10),
    FIFTY("Пятьдесят", 50),
    ONE_HUNDRED("Сто", 100),
    TWO_HUNDRED("Двести", 200),
    FIVE_HUNDRED("Пятьсот", 500),
    ONE_THOUSAND("Одна тысяча", 1000),
    TWO_THOUSAND("Две тысячи", 2000),
    FIVE_THOUSAND("Пять тысяч", 5000);

    /**
     * Наименование номинала
     */
    private String name;
    /**
     * Числовое значение номинала
     */
    private Integer value;

    Nominal(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}