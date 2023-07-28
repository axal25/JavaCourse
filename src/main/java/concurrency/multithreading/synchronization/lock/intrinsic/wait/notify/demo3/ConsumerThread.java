package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo3;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ConsumerThread extends Thread {
    private static int instance_counter = 1;
    private final Container container;
    private final int iterationLimit;
    private final long sleepingDurationMillis;
    private ProducerThread producerThread;
    private final List<Integer> consumed = new LinkedList<>();
    private boolean isFinished = false;

    public ConsumerThread(Container container, int iterationLimit, long sleepingDurationMillis) {
        super(String.format("%s-%d",
                ConsumerThread.class.getSimpleName(),
                instance_counter++));
        this.container = container;
        this.iterationLimit = iterationLimit;
        this.sleepingDurationMillis = sleepingDurationMillis;
    }

    @Override
    public void run() {
        System.out.printf("%s - run %n", getName());
        IntStream.range(0, iterationLimit).forEach(i -> consume(i));
        isFinished = true;
        makeSureProducerThreadIsNotLeftWaiting();
    }

    private void consume(int iteration) {
        // `while` instead of `if` because consumer and producer may be out of sync
        while (container.getValue() == null || producerThread.getState() == State.WAITING) {
            notifyProducer(iteration, false);
        }
        safeWait(iteration);
        synchronized (container) {
            System.out.printf("%d. %s - consuming from container %n", iteration, getName());
            consumed.add(container.getValue());
            container.setValue(null);
        }
    }

    private void makeSureProducerThreadIsNotLeftWaiting() {
        while (!producerThread.isFinished()) {
            notifyProducer(-1, false);
        }
    }

    private void notifyProducer(int iteration, boolean doPrint) {
        synchronized (producerThread) {
            if (doPrint) {
                System.out.printf("%d. %s - notifying %s %n", iteration, getName(), producerThread.getName());
            }
            producerThread.notify();
        }
        sleep();
    }

    private void sleep() {
        try {
            sleep(sleepingDurationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeWait(int iteration) {
        synchronized (this) {
            System.out.printf("%d. %s - waiting %n", iteration, getName());
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setProducerThread(ProducerThread producerThread) {
        this.producerThread = producerThread;
    }

    public List<Integer> getConsumed() {
        return consumed;
    }

    boolean isFinished() {
        return isFinished;
    }
}
