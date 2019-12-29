package ru.otus.hw04.classloader;

public class LogAnnotationDemo {
    public static void main(String[] args) {
        SampleClass sample = CustomLoader.createInstance(SampleClassImpl.class);
        sample.calculation(1);
        sample.calculation(1, 2);
        SampleClass sample2 = CustomLoader.createInstance(TestSampleClassImpl.class);
        sample2.calculation(1);
        sample2.calculation(1, 2);
    }
}
