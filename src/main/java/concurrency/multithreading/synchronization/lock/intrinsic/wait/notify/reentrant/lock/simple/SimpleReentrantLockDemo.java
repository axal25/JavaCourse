package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple.WaitSleepOrIteration.WAIT;

public class SimpleReentrantLockDemo {

    private static final long CONTAINER_SLEEP_DURATION_MILLIS = 250;

    public static void main(String[] args) throws InterruptedException {
        demo(WaitSleepOrIteration.SLEEP);
        demo(WaitSleepOrIteration.ITERATION);
        demo(WAIT);
    }

    private static void demo(WaitSleepOrIteration waitSleepOrIteration) throws InterruptedException {
        System.out.println();
        demo(true, true, waitSleepOrIteration);
        System.out.println();
        demo(false, true, waitSleepOrIteration);
        System.out.println();
        demo(false, false, waitSleepOrIteration);
        System.out.println();
        demo(true, false, waitSleepOrIteration);
    }

    private static void demo(boolean isInterruptibly, boolean doInterrupt, WaitSleepOrIteration waitSleepOrIteration) throws InterruptedException {
        SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
        SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, CONTAINER_SLEEP_DURATION_MILLIS);
        SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, doInterrupt);

        log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "demo", START);

        thread.start();

        if (doInterrupt) {
            log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "interrupt", START);

            thread.interrupt();

            log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "interrupt", FINISH);
        }

        if (waitSleepOrIteration == WAIT) {
            SimpleReentrantLockThreadNotifier notifier = new SimpleReentrantLockThreadNotifier(logs, thread);

            notifier.start();

            notifier.join();
        }

        log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "join thread", START);
        thread.join();
        log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "join thread", FINISH);

        log(waitSleepOrIteration, doInterrupt, isInterruptibly, logs, "demo", FINISH);
    }

    private static void log(
            WaitSleepOrIteration waitSleepOrIteration,
            boolean doInterrupt,
            boolean isInterruptibly,
            SimpleReentrantLockSynchronizedLogs logs,
            String toAdd,
            StartOrFinish startOrFinish) {
        final String logPrefix =
                "waitSleepOrIteration = " + waitSleepOrIteration +
                        ", " +
                        "doInterrupt = " + doInterrupt +
                        ", " +
                        "isInterruptibly = " + isInterruptibly +
                        " | " +
                        SimpleReentrantLockDemo.class.getSimpleName() +
                        " | " +
                        "Thread.currentThread().getName() = " +
                        Thread.currentThread().getName() +
                        ", " +
                        "Thread.currentThread().isInterrupted() = " +
                        Thread.currentThread().isInterrupted();
        final String log = logPrefix + " | " + startOrFinish.getValue() + " " + toAdd;

        logs.add(log);
        System.out.println(log);
    }
}
