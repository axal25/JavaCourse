package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish;
import lombok.AllArgsConstructor;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

@AllArgsConstructor
class SimpleReentrantLockThread extends Thread {
    private final SimpleReentrantLockSynchronizedLogs logs;
    private final SimpleReentrantLockContainer container;
    private final boolean isInterruptibly;
    private final boolean willBeInterrupted;

    @Override
    public void run() {
        if (willBeInterrupted) {
            log(getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, START, null, getName(), isInterrupted()));
            while (!isInterrupted()) {
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    log(getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, null, e, getName(), isInterrupted()));
                    throw new RuntimeException(e);
                }
            }
            log(getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, FINISH, null, getName(), isInterrupted()));
        }

        log(getLogLock(isInterruptibly, willBeInterrupted, START, null, getName(), isInterrupted()));
        try {
            container.lock(isInterruptibly);
        } catch (InterruptedException e) {
            log(getLogLock(isInterruptibly, willBeInterrupted, null, e, getName(), isInterrupted()));
            throw new RuntimeException(e);
        }
        log(getLogLock(isInterruptibly, willBeInterrupted, FINISH, null, getName(), isInterrupted()));
    }

    private void log(String log) {
        logs.add(log);
        System.out.println(log);
    }

    static String getLogSleepUntilIsInterrupted(boolean isInterruptibly, boolean willBeInterrupted, StartOrFinish startOrFinish, Throwable throwable, String currentThreadName, boolean currentThreadIsInterrupted) {
        return getLog(isInterruptibly, willBeInterrupted, currentThreadName, currentThreadIsInterrupted) +
                " | " +
                "thread sleep until is interrupted " +
                toString(startOrFinish, throwable);
    }

    static String getLogLock(boolean isInterruptibly, boolean willBeInterrupted, StartOrFinish startOrFinish, Throwable throwable, String currentThreadName, boolean currentThreadIsInterrupted) {
        return getLog(isInterruptibly, willBeInterrupted, currentThreadName, currentThreadIsInterrupted) +
                " | " +
                "thread lock container " +
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

    public static String getLog(boolean isInterruptibly, boolean willBeInterrupted, String currentThreadName, boolean currentThreadIsInterrupted) {
        return "willBeInterrupted = " + willBeInterrupted +
                ", " +
                "isInterruptibly = " + isInterruptibly +
                " | " +
                SimpleReentrantLockThread.class.getSimpleName() +
                " | " +
                "currentThreadName = " +
                currentThreadName +
                ", " +
                "currentThreadIsInterrupted = " +
                currentThreadIsInterrupted;
    }
}
