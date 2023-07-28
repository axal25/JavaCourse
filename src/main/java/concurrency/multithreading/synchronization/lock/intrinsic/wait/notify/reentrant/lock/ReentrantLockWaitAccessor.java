package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class ReentrantLockWaitAccessor extends ReentrantLockAccessor {

    ReentrantLockWaitAccessor(
            LogsCopyOnAdd logs,
            ReentrantLockContainer reentrantLockContainer,
            boolean isInterruptibly) {
        super(
                logs,
                reentrantLockContainer,
                isInterruptibly,
                () -> {
                    Thread currentThread = Thread.currentThread();
                    synchronized (currentThread) {
                        try {
                            currentThread.wait();
                        } catch (InterruptedException e) {
                            logs.add(getLogExceptionWaitOrSleep(e));
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}
