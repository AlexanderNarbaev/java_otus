package ru.otus.hw06.atm.model;

import java.util.List;

public class ATM {

    private final List<ATMCassette> atmCassettes;

    public ATM(List<ATMCassette> atmCassettes) {
        this.atmCassettes = atmCassettes;
    }

    public List<ATMCassette> getAtmCassettes() {
        return atmCassettes;
    }
}
