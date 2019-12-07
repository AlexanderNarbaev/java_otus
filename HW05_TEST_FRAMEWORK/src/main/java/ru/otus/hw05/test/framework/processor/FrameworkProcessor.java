package ru.otus.hw05.test.framework.processor;

import ru.otus.hw05.test.framework.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class FrameworkProcessor {
    private final Class<?> clazzToTest;


    public FrameworkProcessor(Class<?> clazzToTest) {
        this.clazzToTest = clazzToTest;
    }

    public TestPreparationResult processClass() {
        Method beforeAllMethod = null;
        Method beforeEachMethod = null;
        Method afterAllMethod = null;
        Method afterEachMethod = null;
        List<Method> testMethods = new ArrayList<>();
        for (Method method : clazzToTest.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(BeforeAll.class)) {
                beforeAllMethod = method;
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                beforeEachMethod = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                afterEachMethod = method;
            } else if (method.isAnnotationPresent(AfterAll.class)) {
                afterAllMethod = method;
            }
        }
        return new TestPreparationResult(beforeAllMethod, beforeEachMethod, afterAllMethod, afterEachMethod, testMethods);
    }
}
