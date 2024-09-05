package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private static final int TRANSITION = 10;
    private final T target;

    public ParallelSearch(T[] array, int from, int to, T target) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        if (to - from <= TRANSITION) {
            return linearSearch(array, target, from, to);
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> left = new ParallelSearch<>(array, from, middle, target);
        ParallelSearch<T> right = new ParallelSearch<>(array, middle + 1, to, target);

        left.fork();
        right.fork();

        int leftResult = left.join();
        int rightResult = right.join();

        return (leftResult != -1) ? leftResult : rightResult;
    }

    private int linearSearch(T[] array, T target, int from, int to) {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int parallelSearch(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, 0, array.length - 1, target));
    }
}
