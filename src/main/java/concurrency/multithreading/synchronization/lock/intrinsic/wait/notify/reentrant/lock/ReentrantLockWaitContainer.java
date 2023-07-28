package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class ReentrantLockWaitContainer extends ReentrantLockContainer {

    ReentrantLockWaitContainer(final LogsCopyOnAdd logs) {
        super(
                logs,
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
