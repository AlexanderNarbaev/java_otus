package ru.otus.hw04.asm;

import ru.otus.hw04.classloader.Log;

import java.util.Calendar;

public class CalculationClassImpl {
    @Log
    public void calculation(int param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(long param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(byte param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(short param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(double param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(float param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(char param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Log
    public void calculation(boolean param) {
        System.out.println("Our calculation:\t" + param);
    }

    @Log
    public void calculation(Object param) {
        System.out.println("Our calculation:\t" + param);
    }

    @Log
    public void calculation(String param) {
        System.out.println("Our calculation:\t" + param);
    }

    @Log
    public void calculation(Calendar param) {
        System.out.println("Our calculation:\t" + param);
    }

    public void calculation(int param, int param2) {
        System.out.println("Our calculation with two param:\t" + (param * 2));
        System.out.println("Our calculation with two param:\t" + (param2 * 2));
    }
}
