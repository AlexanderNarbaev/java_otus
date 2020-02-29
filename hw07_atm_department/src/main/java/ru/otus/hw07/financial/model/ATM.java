package ru.otus.hw07.financial.model;

import ru.otus.hw07.financial.cassette.api.CassetteService;

import java.util.List;

public class ATM {

    private final List<CassetteService> atmCassettes;

    public ATM(List<CassetteService> atmCassettes) {
        this.atmCassettes = atmCassettes;
    }

    public List<CassetteService> getAtmCassettes() {
        return atmCassettes;
    }
}
