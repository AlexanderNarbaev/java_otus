package ru.otus.hw05.test.framework.processor;

import ru.otus.hw05.test.framework.exceptions.TestUnsuccessfulException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            long totalTestsCount = 0;
            long failTestsCount = 0;
            long successTestsCount = 0;
            if (testPreparationResult.getBeforeAllMethod() != null) {
                if (testPreparationResult.getBeforeAllMethod() != null) {
                    try {
                        testPreparationResult.getBeforeAllMethod().invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (testPreparationResult.getTestMethods() != null && !testPreparationResult.getTestMethods().isEmpty()) {
                    totalTestsCount = testPreparationResult.getTestMethods().size();
                    boolean testFailed = false;
                    for (Method method : testPreparationResult.getTestMethods()) {
                        Object newObjectOfClazz = null;
                        try {
                            newObjectOfClazz = clazzToTest.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (testPreparationResult.getBeforeEachMethod() != null) {
                            try {
                                testPreparationResult.getBeforeEachMethod().invoke(newObjectOfClazz);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            method.invoke(newObjectOfClazz);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            if (e.getCause() instanceof TestUnsuccessfulException) {
                                failTestsCount++;
                            }
                            testFailed = true;
                            e.printStackTrace();
                        }
                        if (testPreparationResult.getAfterEachMethod() != null) {
                            try {
                                testPreparationResult.getAfterEachMethod().invoke(newObjectOfClazz);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (!testFailed) {
                            successTestsCount++;
                        }
                    }
                }
                if (testPreparationResult.getAfterAllMethod() != null) {
                    try {
                        testPreparationResult.getAfterAllMethod().invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Всего тестов:" + totalTestsCount);
                System.out.println("Не прошло тестов:" + failTestsCount);
                System.out.println("Успешно тестов:" + successTestsCount);
            }
        }
    }
}
