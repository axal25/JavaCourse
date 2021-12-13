package collections;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

class Deques {

    static Deque<?> dequeInterface() {
        // Deque interface

        // Deque is double-ended-queue
        // Deque implements Queue (FIFO) and Stack (FILO)

        Deque<?> deque;

        deque = new ArrayDeque<>();
        deque = new LinkedList<>();
        deque = new LinkedBlockingDeque<>();

        return deque;
    }

    private static BlockingDeque<?> blockingDequeInterface() {
        return new LinkedBlockingDeque<>();
    }
}
