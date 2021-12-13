package collections;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Queues {

    static Queue<?> queueInterface() {
        // Queue interface

        // FIFO vs. Priority Queue
        Queue<?> queue;

        queue = blockingQueueInterface();
        queue = new PriorityQueue<>();

        return queue;
    }

    private static BlockingQueue<?> blockingQueueInterface() {
        return new LinkedBlockingQueue<>();
    }
}
