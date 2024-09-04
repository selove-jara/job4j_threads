package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable job = tasks.poll();
                        if (job != null) {
                            job.run();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ThreadPool thread = new ThreadPool();

            Runnable task1 = () -> {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Task 1 - Step " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            Runnable task2 = () -> {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Task 2 - Step " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            thread.work(task1);
            thread.work(task2);
            Thread.sleep(5000);
            thread.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}