package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {
    @Test
    public void testSingleThreadIncrement() {
        CASCount counter = new CASCount();
        counter.increment();
        assertEquals(1, counter.get());
    }

    @Test
    public void testTwoThreadIncrement() throws InterruptedException {
        final int incrementsPerThread = 10;
        final CASCount counter = new CASCount();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(20, counter.get());
    }
}
