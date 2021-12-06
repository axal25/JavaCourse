package collections;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

class Deques {

    static Deque<?> dequeInterface() {
        // Deque interface

        // Deque is double-ended-queue
        // Deque implements Queue (FIFO) and Stack (FILO)

        Deque<?> dequeLinkedBlockingDeque = new LinkedBlockingDeque<>();
        Deque<?> dequeLinkedList = Lists.linkedList();

        return dequeLinkedBlockingDeque;
    }
}
