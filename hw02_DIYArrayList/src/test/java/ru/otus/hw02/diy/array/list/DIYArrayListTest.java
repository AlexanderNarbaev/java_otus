package ru.otus.hw02.diy.array.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class DIYArrayListTest {

    private DIYArrayList diyArrayListUnderTest;

    @BeforeEach
    void setUp() {
        diyArrayListUnderTest = new DIYArrayList(0);
    }

    @Test
    void testSize() {
        // Setup
        final int expectedResult = 0;

        // Run the test
        final int result = diyArrayListUnderTest.size();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIsEmpty() {
        // Setup

        // Run the test
        final boolean result = diyArrayListUnderTest.isEmpty();

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testContains() {
        // Setup
        final Object containsValue = "containsValue";
        diyArrayListUnderTest.add(containsValue);
        // Run the test
        final boolean result = diyArrayListUnderTest.contains(containsValue);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testIterator() {
        // Setup
        final Iterator expectedResult = null;

        // Run the test
        final Iterator result = diyArrayListUnderTest.iterator();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToArray() {
        // Setup
        final Object[] expectedResult = new Object[]{};

        // Run the test
        final Object[] result = diyArrayListUnderTest.toArray();

        // Verify the results
        assertArrayEquals(expectedResult, result);
    }

    @Test
    void testAdd() {
        // Setup
        final Object valueToAdd = "valueToAdd";

        // Run the test
        final boolean result = diyArrayListUnderTest.add(valueToAdd);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testRemove() {
        // Setup
        final Object valueToRemove = "valueToRemove";
        diyArrayListUnderTest.add(valueToRemove);
        // Run the test
        final boolean result = diyArrayListUnderTest.remove(valueToRemove);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testClear() {
        // Setup

        // Run the test
        diyArrayListUnderTest.clear();

        // Verify the results
    }

    @Test
    void testGet() {
        // Setup
        final int index = 0;
        final Object expectedResult = "result";
        diyArrayListUnderTest.add(expectedResult);
        // Run the test
        final Object result = diyArrayListUnderTest.get(index);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSet() {
        // Setup
        final int index = 0;
        final Object element = "resultElement";
        final Object expectedResult = "resultElement";

        // Run the test
        final Object result = diyArrayListUnderTest.set(index, element);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testAddAtIndex() {
        // Setup
        final int index = 0;
        final Object element = "element";

        // Run the test
        diyArrayListUnderTest.add(index, element);

        // Verify the results
    }

    @Test
    void testRemoveOnIndex() {
        // Setup
        final int index = 0;
        final Object expectedResult = "result";
        diyArrayListUnderTest.add(expectedResult);
        // Run the test
        final Object result = diyArrayListUnderTest.remove(index);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testIndexOf() {
        // Setup
        final Object valueToIndexSearch = "valueToIndexSearch";
        final int expectedResult = 0;
        diyArrayListUnderTest.add(valueToIndexSearch);
        // Run the test
        final int result = diyArrayListUnderTest.indexOf(valueToIndexSearch);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testLastIndexOf() {
        // Setup
        final Object valueToIndexSearch = "valueToIndexSearch";
        final int expectedResult = 1;
        diyArrayListUnderTest.add(valueToIndexSearch);
        diyArrayListUnderTest.add(valueToIndexSearch);
        // Run the test
        final int result = diyArrayListUnderTest.lastIndexOf(valueToIndexSearch);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testListIterator() {
        // Setup
        final ListIterator expectedResult = null;

        // Run the test
        final ListIterator result = diyArrayListUnderTest.listIterator();

        // Verify the results
        assertEquals(expectedResult, result);
    }

}