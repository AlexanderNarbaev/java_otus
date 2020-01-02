package ru.otus.hw07.atm.model;

/**
 * Кассета для хранения купюр определенного номинала
 */
public class ATMCassette implements Cassette, Comparable<ATMCassette> {

    private final Nominal nominal;
    private final long initialState;
    private long currentCapacity = 0;

    public ATMCassette(Nominal nominal, long initialState) {
        this.nominal = nominal;
        this.initialState = initialState;
        this.currentCapacity = initialState;
    }

    @Override
    public long getCurrentCapacity() {
        return currentCapacity;
    }

    private void setCurrentCapacity(long currentCapacity) {
        this.currentCapacity = currentCapacity;
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

    @Override
    public void restoreInitialState() {
        this.currentCapacity = initialState;
    }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public int compareTo(ATMCassette anotherCassette) {
        return anotherCassette.getNominal().compareTo(this.nominal);
    }
}
