package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo1and2.WaitingThread;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo3.ConsumerThread;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo3.Container;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo3.ProducerThread;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.IntrinsicLocksWithWaitMain;

public class WaitNotifyMain {

    private static final long DEFAULT_SLEEP_DURATION_MILLIS = 100L;

    public static void main(String[] args) {
        waitingThreadDemo1();
        System.out.println();
        waitingThreadDemo2();
        System.out.println();
        consumerProducerContainerDemo3();
        System.out.println();
        IntrinsicLocksWithWaitMain.main(args);
    }

    private static void waitingThreadDemo1() {
        System.out.printf("%s Demo 1 %n", WaitingThread.class.getSimpleName());

        WaitingThread waitingThread = new WaitingThread();
        waitingThread.start();

        sleep();
        waitingThread.doNotify();

        sleep();
        waitingThread.doNotifyAll();

        safeJoin(waitingThread);
    }

    private static void waitingThreadDemo2() {
        System.out.printf("%s Demo 2 %n", WaitingThread.class.getSimpleName());

        WaitingThread waitingThread = new WaitingThread();

        sleep();
        synchronized (waitingThread) {
            System.out.printf("%s - notifying%n", WaitNotifyMain.class.getSimpleName());
            waitingThread.notify();
        }

        waitingThread.start();

        sleep();
        synchronized (waitingThread) {
            System.out.printf("%s - notifying all%n", WaitNotifyMain.class.getSimpleName());
            waitingThread.notifyAll();
        }

        safeJoin(waitingThread);
    }

    private static void consumerProducerContainerDemo3() {
        System.out.printf("%s %s %s Demo%n", ConsumerThread.class.getSimpleName(), ProducerThread.class.getSimpleName(), Container.class.getSimpleName());
        Container container = new Container();
        int iterationLimit = 10;
        long sleepingDurationMillis = DEFAULT_SLEEP_DURATION_MILLIS / 100;
        ProducerThread producerThread = new ProducerThread(container, iterationLimit, sleepingDurationMillis);
        ConsumerThread consumerThread = new ConsumerThread(container, iterationLimit, sleepingDurationMillis);
        producerThread.setConsumerThread(consumerThread);
        consumerThread.setProducerThread(producerThread);

        System.out.printf("%s - starting %s %n", WaitNotifyMain.class.getSimpleName(), producerThread.getName());
        producerThread.start();
        System.out.printf("%s - starting %s %n", WaitNotifyMain.class.getSimpleName(), consumerThread.getName());
        consumerThread.start();

        sleep(sleepingDurationMillis * 2 * iterationLimit + sleepingDurationMillis);

        safeJoin(producerThread);
        safeJoin(consumerThread);

        System.out.printf("consumed: %s", consumerThread.getConsumed());
    }

    private static void sleep() {
        sleep(DEFAULT_SLEEP_DURATION_MILLIS);
    }

    private static void sleep(long sleepDurationMillis) {
        try {
            Thread.sleep(sleepDurationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void safeJoin(Thread thread) {
        try {
            System.out.printf("%s - joining %s %n", WaitNotifyMain.class.getSimpleName(), thread.getName());
            thread.join();
            System.out.printf("%s - joined %s %n", WaitNotifyMain.class.getSimpleName(), thread.getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
