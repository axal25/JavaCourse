package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class ReentrantLockSleepContainer extends ReentrantLockContainer {

    ReentrantLockSleepContainer(final LogsCopyOnAdd logs, long sleepDurationMillis) {
        super(
                logs,
                () -> {
                    try {
                        Thread.sleep(sleepDurationMillis);
                    } catch (InterruptedException e) {
                        logs.add(getLogExceptionWaitOrSleep(e));
                        throw new RuntimeException(e);
                    }
                });
    }
}
