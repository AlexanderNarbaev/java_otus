package ru.otus.hw05;

import ru.otus.hw05.test.framework.processor.TestFramework;
import ru.otus.hw05.testing.SomeTestClazz;

public class TestRunner {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework(SomeTestClazz.class);
        testFramework.doTest();
    }
}
