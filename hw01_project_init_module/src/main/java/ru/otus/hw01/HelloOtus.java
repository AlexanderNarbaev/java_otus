package ru.otus.hw01;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        sayHello();
    }

    private static void sayHello() {
        List<String> helloWords = new ArrayList<>(2);
        helloWords.add("Hello");
        helloWords.add("I'am first program on Java Course in Otus");
        System.out.println(Joiner.on(", ").join(helloWords));
    }
}