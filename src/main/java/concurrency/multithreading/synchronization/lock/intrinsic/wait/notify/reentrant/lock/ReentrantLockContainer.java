package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;
import lombok.AllArgsConstructor;

import java.util.concurrent.locks.ReentrantLock;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

@AllArgsConstructor
abstract class ReentrantLockContainer {
    private final LogsCopyOnAdd logs;
    private final FIWaitOrSleep fiWaitOrSleep;
    private final ReentrantLock reentrantLock = new ReentrantLock();


    void lockAndPerform(boolean isInterruptibly) {
        if (isInterruptibly) {
            lockInterruptibly();
        } else {
            lock();
        }

        try {
            performGuardedAction(isInterruptibly);
        } finally {
            unlock();
        }
    }

    private void performGuardedAction(boolean isInterruptibly) {
        logs.add(getLogPerfomingAction(isInterruptibly, START));
        waitOrSleep();
        logs.add(getLogPerfomingAction(isInterruptibly, FINISH));
    }

    static String getLogPerfomingAction(boolean isInterruptibly, StartOrFinish startOrFinish) {
        return startOrFinish.getValue() +
                " ) performing action requiring lock - while being locked" +
                (isInterruptibly ? " interruptibly" : "");
    }

    private void lock() {
        logs.add(getLogLocking(false, START));
        reentrantLock.lock();
        logs.add(getLogLocking(false, FINISH));
    }

    private void lockInterruptibly() {
        try {
            logs.add(getLogLocking(true, START));
            reentrantLock.lockInterruptibly();
            logs.add(getLogLocking(true, FINISH));
        } catch (InterruptedException e) {
            logs.add(getLogExceptionLockInterruptibly(e));
            throw new RuntimeException(e);
        }
    }

    private static String getLogExceptionLockInterruptibly(Exception e) {
        return e +
                " - " +
                ReentrantLockContainer.class.getSimpleName() +
                "::" +
                "lockInterruptibly";
    }

    static String getLogLocking(boolean isInterruptibly, StartOrFinish startOrFinish) {
        return startOrFinish.getValue() +
                " ) locking" +
                (isInterruptibly ? "" : " not") +
                " interruptibly";
    }

    private void waitOrSleep() {
        fiWaitOrSleep.waitOrSleep();
    }

    static String getLogExceptionWaitOrSleep(Exception e) {
        return e +
                " - " +
                ReentrantLockContainer.class.getSimpleName() +
                "::" +
                "waitOrSleep";
    }

    private void unlock() {
        logs.add(getLogUnlock());
        reentrantLock.unlock();
    }

    static String getLogUnlock() {
        return "unlocking";
    }
}
