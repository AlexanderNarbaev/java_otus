package ru.otus.hw04.classloader;

import java.util.Random;

public class LogAnnotationDemo {
    public static void main(String[] args) {
        SampleClass sample = CustomLoader.createInterfaceInstance();
        sample.calculation(new Random().nextInt(100));
        sample.calculation(new Random().nextInt(100), new Random().nextInt(100));
    }
}
