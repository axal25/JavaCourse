package collections.theory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

class Deques {

    static Information dequeInterface() {
        Information dequeInterfaceInformation = new Information(Deque.class);

        // Deque is double-ended-queue
        // Deque implements Queue (FIFO) and Stack (FILO)

        Deque<?> deque;

        deque = new ArrayDeque<>();
        deque = new LinkedList<>();
        deque = new LinkedBlockingDeque<>();

        return dequeInterfaceInformation;
    }

    private static BlockingDeque<?> blockingDequeInterface() {
        return new LinkedBlockingDeque<>();
    }
}
