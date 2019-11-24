package ru.otus.hw04.asm;

import ru.otus.hw04.classloader.Log;

public class CalculationClassImpl {
    @Log
    public void calculation(int param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    public void calculation(int param, int param2) {
        System.out.println("Our calculation with two param:\t" + (param * 2));
    }
}
