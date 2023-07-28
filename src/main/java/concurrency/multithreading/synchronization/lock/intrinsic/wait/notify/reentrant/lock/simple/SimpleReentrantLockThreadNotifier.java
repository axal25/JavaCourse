package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish;
import lombok.AllArgsConstructor;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

@AllArgsConstructor
public class SimpleReentrantLockThreadNotifier extends Thread {
    private final SimpleReentrantLockSynchronizedLogs logs;
    private final SimpleReentrantLockThread thread;

    private static class WasLogIssued {

        private static class NotifyWaitingThread {
            private final BooleanWrapper start = new BooleanWrapper();
            private final BooleanWrapper finish = new BooleanWrapper();

        }

        private static class SynchronizedNotifyThread {
            private final BooleanWrapper start = new BooleanWrapper();
            private final BooleanWrapper finish = new BooleanWrapper();

        }

        private static class BooleanWrapper {
            private boolean value = false;
        }

        private final NotifyWaitingThread notifyWaitingThread = new NotifyWaitingThread();
        private final SynchronizedNotifyThread synchronizedNotifyThread = new SynchronizedNotifyThread();
    }

    @Override
    public void run() {
        WasLogIssued wasLogIssued = new WasLogIssued();

        log(getLog(START, null, "notify thread"));

        while (thread.getState() != Thread.State.WAITING
                && thread.getState() != State.TERMINATED) {
            try {
                sleep(5);
            } catch (InterruptedException e) {
                log(getLog(START, e, "notify thread"));
                throw new RuntimeException(e);
            }
        }

        while (thread.getState() == Thread.State.WAITING) {

            log(getLog(START, null, "notify waiting thread"), wasLogIssued.notifyWaitingThread.start);

            synchronized (thread) {
                log(getLog(START, null, "synchronized notify thread"), wasLogIssued.synchronizedNotifyThread.start);

                thread.notify();

                log(getLog(FINISH, null, "synchronized notify thread"), wasLogIssued.synchronizedNotifyThread.finish);
            }

            log(getLog(FINISH, null, "notify waiting thread"), wasLogIssued.notifyWaitingThread.finish);
        }

        log(getLog(FINISH, null, "notify thread"));
    }

    private void log(String log, WasLogIssued.BooleanWrapper booleanWrapper) {
        if (booleanWrapper == null) {
            log(log);
        }
        if (booleanWrapper != null && !booleanWrapper.value) {
            log(log);
            booleanWrapper.value = true;
        }
    }

    private void log(String log) {
        logs.add(log);
        System.out.println(log);
    }

    private static String getLog(StartOrFinish startOrFinish, Throwable throwable, String toAdd) {
        final String logPrefix =
                SimpleReentrantLockThreadNotifier.class.getSimpleName() +
                        " | " +
                        "Thread.currentThread().getName() = " +
                        Thread.currentThread().getName() +
                        ", " +
                        "Thread.currentThread().isInterrupted() = " +
                        Thread.currentThread().isInterrupted();

        return logPrefix + " | " + toString(startOrFinish, throwable) + " " + toAdd;
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
}
