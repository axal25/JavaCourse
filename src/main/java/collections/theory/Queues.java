package collections.theory;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Queues {

    static Information queueInterface() {
        Information queueInterfaceInformation = new Information(Queue.class);

        // FIFO vs. Priority Queue
        Queue<?> queue;

        queue = blockingQueueInterface();
        queue = new PriorityQueue<>();

        return queueInterfaceInformation;
    }

    private static BlockingQueue<?> blockingQueueInterface() {
        return new LinkedBlockingQueue<>();
    }
}
