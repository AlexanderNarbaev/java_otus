package ru.otus.hw05.test.framework.processor;

import java.lang.reflect.Method;
import java.util.List;

class TestPreparationResult {

    private final Method beforeAllMethod;
    private final Method beforeEachMethod;
    private final Method afterAllMethod;
    private final Method afterEachMethod;
    private final List<Method> testMethods;

    public TestPreparationResult(Method beforeAllMethod, Method beforeEachMethod, Method afterAllMethod, Method afterEachMethod, List<Method> testMethods) {
        this.beforeAllMethod = beforeAllMethod;
        this.beforeEachMethod = beforeEachMethod;
        this.afterAllMethod = afterAllMethod;
        this.afterEachMethod = afterEachMethod;
        this.testMethods = testMethods;
    }

    public Method getBeforeAllMethod() {
        return beforeAllMethod;
    }

    public Method getBeforeEachMethod() {
        return beforeEachMethod;
    }

    public Method getAfterAllMethod() {
        return afterAllMethod;
    }

    public Method getAfterEachMethod() {
        return afterEachMethod;
    }

    public List<Method> getTestMethods() {
        return testMethods;
    }
}
