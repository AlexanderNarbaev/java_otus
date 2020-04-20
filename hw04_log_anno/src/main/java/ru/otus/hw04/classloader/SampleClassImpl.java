package ru.otus.hw04.classloader;

public class SampleClassImpl implements SampleClass {
    @Override
    @Log
    public void calculation(int param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }

    @Override
    public void calculation(int param, int param2) {
        System.out.println("Our calculation with two param:\t" + (param * 2));
        System.out.println("Our calculation with two param2:\t" + (param2 * 2));
    }
}
