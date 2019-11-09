package ru.otus.hw04.classloader;

public class SampleClassImpl implements SampleClass {
    @Override
    @Log
    public void calculation(int param) {
        System.out.println("Our calculation:\t" + (param * 2));
    }
}
