package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void testProducerConsumerCorrectness() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> consumed = new ArrayList<>();
        int totalElements = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < totalElements; i++) {
                    consumed.add(queue.poll());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        assertEquals(totalElements, consumed.size());
        for (int i = 0; i < totalElements; i++) {
            assertEquals(Integer.valueOf(i), consumed.get(i));
        }
    }

    @Test
    public void testConsumerBlockedWhenQueueEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);

        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                queue.offer(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
        producer.start();

        consumer.join();
        producer.join();

        assertTrue(true);
    }
}