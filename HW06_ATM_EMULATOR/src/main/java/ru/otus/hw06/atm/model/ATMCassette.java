package ru.otus.hw06.atm.model;

import java.io.Serializable;

/**
 * Кассета для хранения купюр определенного номинала
 */
public class ATMCassette implements Serializable, Comparable<ATMCassette> {

    private final Nominal nominal;
    private long currentCapacity = 0;

    public ATMCassette(Nominal nominal) {
        this.nominal = nominal;
    }

    public long getCurrentCapacity() {
        return currentCapacity;
    }

    public void addBanknotes(long banknotesCount) {
        setCurrentCapacity(getCurrentCapacity() + banknotesCount);
    }

    public void getBanknotes(long banknotesCount) {
        setCurrentCapacity(getCurrentCapacity() - banknotesCount);
    }

    private void setCurrentCapacity(long currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public int compareTo(ATMCassette anotherCassette) {
        return anotherCassette.getNominal().getValue() - this.nominal.getValue();
    }
}
