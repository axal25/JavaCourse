package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
class SimpleReentrantLockContainer {
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final SimpleReentrantLockSynchronizedLogs logs;
    private final WaitSleepOrIteration waitSleepOrIteration;
    private final Long sleepDurationMillis;

    void lock(boolean isInterruptibly) throws InterruptedException {
        String currentThreadName = Thread.currentThread().getName();
        boolean currentThreadIsInterrupted = Thread.currentThread().isInterrupted();

        log(getLogLock(isInterruptibly, waitSleepOrIteration, StartOrFinish.START, null, currentThreadName, currentThreadIsInterrupted));
        if (isInterruptibly) {
            // isInterruptibly == true throws here
            try {
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                log(getLogLock(isInterruptibly, waitSleepOrIteration, null, e, currentThreadName, currentThreadIsInterrupted));
                throw e;
            }
        } else {
            reentrantLock.lock();
        }
        log(getLogLock(isInterruptibly, waitSleepOrIteration, StartOrFinish.FINISH, null, currentThreadName, currentThreadIsInterrupted));

        try {
            log(getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, StartOrFinish.START, null, currentThreadName, currentThreadIsInterrupted));
            // isInterruptibly == false throws here
            try {
                waitSleepOrIterate();
            } catch (InterruptedException e) {
                log(getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, null, e, currentThreadName, currentThreadIsInterrupted));
                throw e;
            }
            log(getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, StartOrFinish.FINISH, null, currentThreadName, currentThreadIsInterrupted));
        } finally {
            log(getLogUnLock(isInterruptibly, waitSleepOrIteration, StartOrFinish.START, null, currentThreadName, currentThreadIsInterrupted));
            reentrantLock.unlock();
            log(getLogUnLock(isInterruptibly, waitSleepOrIteration, StartOrFinish.FINISH, null, currentThreadName, currentThreadIsInterrupted));
        }
    }

    private void log(String log) {
        logs.add(log);
        System.out.println(log);
    }

    static String getLogLock(boolean isInterruptibly, WaitSleepOrIteration waitSleepOrIteration, StartOrFinish startOrFinish, Throwable throwable, String currentThreadName, boolean currentThreadIsInterrupted) {
        return getLog(isInterruptibly, waitSleepOrIteration, currentThreadName, currentThreadIsInterrupted) +
                " | " +
                "container lock " +
                toString(startOrFinish, throwable);
    }

    static String getLogWaitSleepOrIteration(boolean isInterruptibly, WaitSleepOrIteration waitSleepOrIteration, StartOrFinish startOrFinish, Throwable throwable, String currentThreadName, boolean currentThreadIsInterrupted) {
        return getLog(isInterruptibly, waitSleepOrIteration, currentThreadName, currentThreadIsInterrupted) +
                " | " +
                "container " +
                waitSleepOrIteration +
                " " +
                toString(startOrFinish, throwable);
    }

    private void waitSleepOrIterate() throws InterruptedException {
        if (waitSleepOrIteration == null) {
            throw new IllegalArgumentException(WaitSleepOrIteration.class.getSimpleName() + " cannot be null");
        }
        switch (waitSleepOrIteration) {
            case SLEEP:
                Thread.sleep(sleepDurationMillis);
                break;
            case WAIT:
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait();
                }
                break;
            case ITERATION:
                synchronized (Thread.currentThread()) {
                    IntStream.range(0, 100000)
                            .boxed()
                            .map(i -> i < 50000 ? "smaller" : "bigger")
                            .collect(Collectors.joining(", "));
                }
                break;
            default:
                throw new UnsupportedOperationException(WaitSleepOrIteration.class.getSimpleName() + " unsupported value = " + waitSleepOrIteration);
        }
    }

    static String getLogUnLock(boolean isInterruptibly, WaitSleepOrIteration waitSleepOrIteration, StartOrFinish startOrFinish, Throwable throwable, String currentThreadName, boolean currentThreadIsInterrupted) {
        return getLog(isInterruptibly, waitSleepOrIteration, currentThreadName, currentThreadIsInterrupted) +
                " | " +
                "container unlock " +
                toString(startOrFinish, throwable);
    }

    private static String toString(StartOrFinish startOrFinish, Throwable throwable) {
        if (startOrFinish == null && throwable == null) {
            throw new UnsupportedOperationException(
                    "both " +
                            StartOrFinish.class.getSimpleName() +
                            ", " +
                            Throwable.class.getSimpleName() +
                            " are null");
        }
        if (startOrFinish != null && throwable != null) {
            throw new UnsupportedOperationException(
                    "both " +
                            StartOrFinish.class.getSimpleName() +
                            ", " +
                            Throwable.class.getSimpleName() +
                            " are not null");
        }
        return startOrFinish != null
                ? startOrFinish.getValue()
                : throwable != null
                ? throwable.toString()
                : "INVALID";
    }

    public static String getLog(boolean isInterruptibly, WaitSleepOrIteration waitSleepOrIteration, String currentThreadName, boolean currentThreadIsInterrupted) {
        return "waitSleepOrIteration = " + waitSleepOrIteration +
                ", " +
                "isInterruptibly = " + isInterruptibly +
                " | " +
                SimpleReentrantLockContainer.class.getSimpleName() +
                " | " +
                "currentThreadName = " +
                currentThreadName +
                ", " +
                "currentThreadIsInterrupted = " +
                currentThreadIsInterrupted;
    }
}
