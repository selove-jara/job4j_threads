package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        boolean success;
        do {
            int current = count.get();
            success = count.compareAndSet(current, current + 1);
        } while (!success);

    }

    public int get() {
        return count.get();
    }
}
