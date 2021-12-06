package collections;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class Queues {

    static Queue<?> queueInterface() {
        // Queue interface

        // FIFO vs. Priority Queue

        Queue<?> queueLinkedBlockingQueue = new LinkedBlockingQueue<>();
        Queue<?> queuePriorityQueue = new PriorityQueue<>();

        return queueLinkedBlockingQueue;
    }
}
