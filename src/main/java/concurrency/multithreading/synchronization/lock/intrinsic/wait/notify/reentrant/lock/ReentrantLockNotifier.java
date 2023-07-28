package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class ReentrantLockNotifier {
    private final ReentrantLockAccessor accessor;
    private final LogsCopyOnAdd logs;

    ReentrantLockNotifier(LogsCopyOnAdd logs, ReentrantLockAccessor accessor) {
        this.accessor = accessor;
        this.logs = logs;
    }

    void notifyAccessor() {
        log();
        synchronized (accessor) {
            accessor.notify();
        }
    }

    private void log() {
        logs.add(getLog(Thread.currentThread(), accessor));
    }

    public static String getLog(Thread thread, ReentrantLockAccessor accessor) {
        return String.format(
                "%s notifying thread: %s",
                thread.getName(),
                accessor.getName());
    }
}
