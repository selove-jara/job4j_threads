package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchTest {
    @Test
    void testIntegerArray() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = ParallelSearch.parallelSearch(array, 7);
        assertEquals(6, index);
    }

    @Test
    void testStringArray() {
        String[] array = {"apple", "banana", "cherry", "date", "elderberry"};
        int index = ParallelSearch.parallelSearch(array, "cherry");
        assertEquals(2, index);
    }

    @Test
    void testLinearSearchSmallArray() {
        Integer[] array = {1, 2, 3, 4};
        int index = ParallelSearch.parallelSearch(array, 3);
        assertEquals(2, index);
    }

    @Test
    void testRecursiveSearchLargeArray() {
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        int index = ParallelSearch.parallelSearch(array, 15);
        assertEquals(14, index);
    }

    @Test
    void testElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5};
        int index = ParallelSearch.parallelSearch(array, 10);
        assertEquals(-1, index);
    }
}