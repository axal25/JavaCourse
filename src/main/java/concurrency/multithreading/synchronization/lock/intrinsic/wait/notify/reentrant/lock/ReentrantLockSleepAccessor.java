package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class ReentrantLockSleepAccessor extends ReentrantLockAccessor {

    public ReentrantLockSleepAccessor(
            LogsCopyOnAdd logs,
            ReentrantLockContainer reentrantLockContainer,
            boolean isInterruptibly,
            long sleepDurationMillis) {
        super(
                logs,
                reentrantLockContainer,
                isInterruptibly, () -> {
                    try {
                        Thread.sleep(sleepDurationMillis);
                    } catch (InterruptedException e) {
                        logs.add(getLogExceptionWaitOrSleep(e));
                        throw new RuntimeException(e);
                    }
                });
    }
}
