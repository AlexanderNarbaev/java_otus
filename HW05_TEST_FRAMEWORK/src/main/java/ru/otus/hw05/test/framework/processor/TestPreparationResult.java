package ru.otus.hw05.test.framework.processor;

import java.lang.reflect.Method;
import java.util.List;

class TestPreparationResult {

    private Method beforeAllMethod;
    private Method beforeEachMethod;
    private Method afterAllMethod;
    private Method afterEachMethod;
    private List<Method> testMethods;

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
