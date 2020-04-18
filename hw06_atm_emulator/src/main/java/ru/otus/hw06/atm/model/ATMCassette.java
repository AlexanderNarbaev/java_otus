package ru.otus.hw06.atm.model;

/**
 * Кассета для хранения купюр определенного номинала
 */
public class ATMCassette implements Cassette, Comparable<ATMCassette> {

    private final Nominal nominal;
    private long currentCapacity = 0;

    public ATMCassette(Nominal nominal) {
        this.nominal = nominal;
    }

    public long getCurrentCapacity() {
        return currentCapacity;
    }

    @Override
    public void addBanknotes(long banknotesCount) throws IllegalATMOperation {
        if (banknotesCount > 0) {
            setCurrentCapacity(getCurrentCapacity() + banknotesCount);
        } else throw new IllegalATMOperation(banknotesCount);
    }

    @Override
    public void getBanknotes(long banknotesCount) throws InsufficientFundsException, IllegalATMOperation {
        if (banknotesCount > 0) {
            if (banknotesCount <= currentCapacity) {
                setCurrentCapacity(getCurrentCapacity() - banknotesCount);
            } else throw new InsufficientFundsException(banknotesCount);
        } else throw new IllegalATMOperation(banknotesCount);
    }

    private void setCurrentCapacity(long currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public int compareTo(ATMCassette anotherCassette) {
        return anotherCassette.getNominal().compareTo(this.nominal);
    }
}
