package ru.otus.hw05.test.framework.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestFramework {
    private final TestPreparationResult testPreparationResult;
    private final Class<?> clazzToTest;

    public TestFramework(Class<?> clazzToTest) {
        FrameworkProcessor processor = new FrameworkProcessor(clazzToTest);
        testPreparationResult = processor.processClass();
        this.clazzToTest = clazzToTest;
    }

    public void doTest() {
        if (testPreparationResult != null
                && testPreparationResult.getTestMethods() != null) {
            long totalTestsCount = testPreparationResult.getTestMethods().size();
            long failTestsCount = 0;
            try {
                if (testPreparationResult.getBeforeAllMethod() != null) {
                    testPreparationResult.getBeforeAllMethod().invoke(null);
                }
                failTestsCount = testAnnotatedMethods(testPreparationResult.getTestMethods());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (testPreparationResult.getAfterAllMethod() != null) {
                    try {
                        testPreparationResult.getAfterAllMethod().invoke(null);
                    } catch (Exception e) {
                        failTestsCount++;
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Всего тестов:" + totalTestsCount);
            System.out.println("Не прошло тестов:" + failTestsCount);
            System.out.println("Успешно тестов:" + (totalTestsCount - failTestsCount));
        }
    }

    private long testAnnotatedMethods(List<Method> testMethods) throws Exception {
        long failTestsCount = 0;
        for (Method method : testMethods) {
            Object newObjectOfClazz = createNewClassInstance();
            try {
                doRegularTest(method, newObjectOfClazz);
            } catch (Exception e) {
                failTestsCount++;
                e.printStackTrace();
            } finally {
                if (testPreparationResult.getAfterEachMethod() != null) {
                    try {
                        testPreparationResult.getAfterEachMethod().invoke(newObjectOfClazz);
                    } catch (Exception e) {
                        failTestsCount++;
                        e.printStackTrace();
                    }
                }
            }
        }
        return failTestsCount;
    }

    private void doRegularTest(Method method, Object newObjectOfClazz) throws Exception {
        if (testPreparationResult.getBeforeEachMethod() != null) {
            testPreparationResult.getBeforeEachMethod().invoke(newObjectOfClazz);
        }
        method.invoke(newObjectOfClazz);
    }

    private Object createNewClassInstance() {
        Object newObjectOfClazz = null;
        try {
            newObjectOfClazz = clazzToTest.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newObjectOfClazz;
    }
}
