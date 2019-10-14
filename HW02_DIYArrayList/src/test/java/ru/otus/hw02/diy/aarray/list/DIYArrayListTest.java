package ru.otus.hw02.diy.aarray.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Проба пера в тестировании
 */
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
//
//    @Test
//    void testContains() {
//        // Setup
//        final Object o = null;
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.contains(o);
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testIterator() {
//        // Setup
//        final Iterator expectedResult = null;
//
//        // Run the test
//        final Iterator result = diyArrayListUnderTest.iterator();
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }

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
        final Object o = null;

        // Run the test
        final boolean result = diyArrayListUnderTest.add(o);

        // Verify the results
        assertTrue(result);
    }

//    @Test
//    void testRemove() {
//        // Setup
//        final Object o = null;
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.remove(o);
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddAll() {
//        // Setup
//        final Collection c = Arrays.asList();
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.addAll(c);
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testAddAll1() {
//        // Setup
//        final int index = 0;
//        final Collection c = Arrays.asList();
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.addAll(index, c);
//
//        // Verify the results
//        assertTrue(result);
//    }

    @Test
    void testClear() {
        // Setup

        // Run the test
        diyArrayListUnderTest.clear();

        // Verify the results
    }

//    @Test
//    void testGet() {
//        // Setup
//        final int index = 0;
//        final Object expectedResult = null;
//
//        // Run the test
//        final Object result = diyArrayListUnderTest.get(index);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testSet() {
//        // Setup
//        final int index = 0;
//        final Object element = null;
//        final Object expectedResult = null;
//
//        // Run the test
//        final Object result = diyArrayListUnderTest.set(index, element);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }

//    @Test
//    void testAdd1() {
//        // Setup
//        final int index = 0;
//        final Object element = null;
//
//        // Run the test
//        diyArrayListUnderTest.add(index, element);
//
//        // Verify the results
//    }
//
//    @Test
//    void testRemove1() {
//        // Setup
//        final int index = 0;
//        final Object expectedResult = null;
//
//        // Run the test
//        final Object result = diyArrayListUnderTest.remove(index);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testIndexOf() {
//        // Setup
//        final Object o = null;
//        final int expectedResult = 0;
//
//        // Run the test
//        final int result = diyArrayListUnderTest.indexOf(o);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testLastIndexOf() {
//        // Setup
//        final Object o = null;
//        final int expectedResult = 0;
//
//        // Run the test
//        final int result = diyArrayListUnderTest.lastIndexOf(o);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testListIterator() {
//        // Setup
//        final ListIterator expectedResult = null;
//
//        // Run the test
//        final ListIterator result = diyArrayListUnderTest.listIterator();
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testListIterator1() {
//        // Setup
//        final int index = 0;
//        final ListIterator expectedResult = null;
//
//        // Run the test
//        final ListIterator result = diyArrayListUnderTest.listIterator(index);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testSubList() {
//        // Setup
//        final int fromIndex = 0;
//        final int toIndex = 0;
//        final List expectedResult = Arrays.asList();
//
//        // Run the test
//        final List result = diyArrayListUnderTest.subList(fromIndex, toIndex);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testRetainAll() {
//        // Setup
//        final Collection c = Arrays.asList();
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.retainAll(c);
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testRemoveAll() {
//        // Setup
//        final Collection c = Arrays.asList();
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.removeAll(c);
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testContainsAll() {
//        // Setup
//        final Collection c = Arrays.asList();
//
//        // Run the test
//        final boolean result = diyArrayListUnderTest.containsAll(c);
//
//        // Verify the results
//        assertTrue(result);
//    }

//    @Test
//    void testToArray1() {
//        // Setup
//        final Object[] a = new Object[]{};
//        final Object[] expectedResult = new Object[]{};
//
//        // Run the test
//        final Object[] result = diyArrayListUnderTest.toArray(a);
//
//        // Verify the results
//        assertArrayEquals(expectedResult, result);
//    }
}
