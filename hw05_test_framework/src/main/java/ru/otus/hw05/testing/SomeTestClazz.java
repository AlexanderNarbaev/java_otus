package ru.otus.hw05.testing;

import ru.otus.hw05.test.framework.annotations.*;
import ru.otus.hw05.test.framework.exceptions.TestUnsuccessfulException;

public class SomeTestClazz {

    private ClazzNeedToBeTested testingClazz;

    @BeforeAll
    public static void startTest() {
        System.out.println("Начали прогон тестов...");
    }

    @AfterAll
    public static void endTest() {
        System.out.println("Закончили прогон тестов...");
    }

    @BeforeEach
    public void initializeTestingClass() {
        testingClazz = new ClazzNeedToBeTested("TestValue", 0L);
        System.out.println(testingClazz);
    }

    @Test
    public void testGetInterestingValueOne() throws TestUnsuccessfulException {
        if (!testingClazz.getInterestingFieldOne().equalsIgnoreCase("TestValue")) {
            throw new TestUnsuccessfulException();
        }
        System.out.println(testingClazz.getInterestingFieldOne());
    }

    @Test
    public void testGetInterestingValueTwo() throws TestUnsuccessfulException {
        if (testingClazz.getInterestingFieldTwo() != 0L) {
            throw new TestUnsuccessfulException();
        }
        System.out.println(testingClazz.getInterestingFieldTwo());
    }

    @Test
    public void testSetInterestingValueOne() throws TestUnsuccessfulException {
        testingClazz.setInterestingFieldOne("ADEFRAF");
        if (!testingClazz.getInterestingFieldOne().equalsIgnoreCase("ADEFRAF")) {
            throw new TestUnsuccessfulException();
        }
        System.out.println(testingClazz);
    }

    @Test
    public void testSetInterestingValueTwo() throws TestUnsuccessfulException {
        testingClazz.setInterestingFieldTwo(2L);
        if (testingClazz.getInterestingFieldTwo() != 2L) {
            throw new TestUnsuccessfulException();
        }
        System.out.println(testingClazz);
    }

    @AfterEach
    public void deInitializeTestingClass() {
        testingClazz = null;
        System.out.println(testingClazz);
    }
}
