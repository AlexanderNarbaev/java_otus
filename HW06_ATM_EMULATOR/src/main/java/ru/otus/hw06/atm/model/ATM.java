package ru.otus.hw06.atm.model;

import java.io.Serializable;
import java.util.Set;

public class ATM implements Serializable {

    private final Set<ATMCassette> atmCassettes;

    public ATM(Set<ATMCassette> atmCassettes) {
        this.atmCassettes = atmCassettes;
    }

    public Set<ATMCassette> getAtmCassettes() {
        return atmCassettes;
    }
}
