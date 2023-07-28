package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo3;

import java.util.stream.IntStream;

public class ProducerThread extends Thread {
    private static int instance_counter = 1;
    private final Container container;
    private final int iterationLimit;
    private final long sleepingDurationMillis;
    private int counter = 0;
    private ConsumerThread consumerThread;
    private boolean isFinished = false;

    public ProducerThread(Container container, int iterationLimit, long sleepingDurationMillis) {
        super(String.format("%s-%d",
                ProducerThread.class.getSimpleName(),
                instance_counter++));
        this.container = container;
        this.iterationLimit = iterationLimit;
        this.sleepingDurationMillis = sleepingDurationMillis;
    }

    @Override
    public void run() {
        System.out.printf("%s - run %n", getName());
        IntStream.range(0, iterationLimit).forEach(i -> produce(i));
        isFinished = true;
        makeSureConsumerThreadIsNotLeftWaiting();
    }

    private void produce(int iteration) {
        // `while` instead of `if` because consumer and producer may be out of sync
        while (container.getValue() != null || consumerThread.getState() == State.WAITING) {
            notifyConsumer(iteration, true);
        }
        safeWait(iteration);
        synchronized (container) {
            System.out.printf("%d. %s - producing for container %n", iteration, getName());
            container.setValue(counter++);
        }
    }

    private void makeSureConsumerThreadIsNotLeftWaiting() {
        while (!consumerThread.isFinished()) {
            notifyConsumer(-1, false);
        }
    }

    private void notifyConsumer(int iteration, boolean doPrint) {
        synchronized (consumerThread) {
            if (doPrint) {
                System.out.printf("%d. %s - notifying %s %n", iteration, getName(), consumerThread.getName());
            }
            consumerThread.notify();
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

    public void setConsumerThread(ConsumerThread consumerThread) {
        this.consumerThread = consumerThread;
    }

    boolean isFinished() {
        return isFinished;
    }
}
