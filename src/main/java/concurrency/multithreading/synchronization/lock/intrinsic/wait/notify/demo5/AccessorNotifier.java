package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread;

public class AccessorNotifier extends Thread {

    private final AbstractAccessorMethodThread accessor;
    private final LogsContainer logsContainer;

    AccessorNotifier(
            AbstractAccessorMethodThread accessor,
            LogsContainer logsContainer) {
        super(String.format("Notifier for %s", accessor.getName()));
        this.accessor = accessor;
        this.logsContainer = logsContainer;
    }

    @Override
    public void run() {
        while (accessor.getState() != State.TERMINATED) {
            waitUntilAccessorIsWaiting();
            notifyAccessorIfIsWaiting();
        }
    }

    private void notifyAccessorIfIsWaiting() {
        if (accessor.getState() == State.WAITING) {
            log();
            synchronized (accessor) {
                accessor.notify();
            }
        }
    }

    private void log() {
        if (accessor.isStatic()) {
            logsContainer.addStatic(getLog(Thread.currentThread()));
        } else {
            logsContainer.addInstance(getLog(Thread.currentThread()));
        }
    }

    String getLog(Thread thread) {
        return String.format(
                "%s notifying thread: %s",
                thread.getName(),
                accessor.getName());
    }

    private void waitUntilAccessorIsWaiting() {
        while (accessor.getState() != State.WAITING && accessor.getState() != State.TERMINATED) {
            safeSleep();
        }
    }

    private void safeSleep() {
        try {
            sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
