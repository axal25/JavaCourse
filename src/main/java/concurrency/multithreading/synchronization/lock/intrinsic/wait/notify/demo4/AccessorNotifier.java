package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.AccessorMethodAbstractThread;

public class AccessorNotifier extends Thread {

    private final AccessorMethodAbstractThread accessor;

    AccessorNotifier(AccessorMethodAbstractThread accessor) {
        super(String.format("Notifier for %s", accessor.getName()));
        this.accessor = accessor;
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
            Logger.printAndAddToLogger("\t", String.format("%s notifying thread: %s", Thread.currentThread().getName(), accessor.getName()));
            synchronized (accessor) {
                accessor.notify();
            }
        }
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
