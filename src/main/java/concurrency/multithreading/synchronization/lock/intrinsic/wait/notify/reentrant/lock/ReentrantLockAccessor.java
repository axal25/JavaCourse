package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

import java.util.List;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;


public class ReentrantLockAccessor extends Thread {

    private static final ReentrantLockThreadGroup reentrantLockThreadGroup = new ReentrantLockThreadGroup();
    private static int instanceCounter = 1;

    private final LogsCopyOnAdd logs;
    private final ReentrantLockContainer reentrantLockContainer;
    private final boolean isInterruptibly;
    private final FIWaitOrSleep fiWaitOrSleep;

    ReentrantLockAccessor(
            LogsCopyOnAdd logs,
            ReentrantLockContainer reentrantLockContainer,
            boolean isInterruptibly,
            FIWaitOrSleep fiWaitOrSleep) {
        super(
                reentrantLockThreadGroup,
                String.format(
                        "%s-%d",
                        ReentrantLockAccessor.class.getSimpleName(),
                        instanceCounter++));
        this.logs = logs;
        this.reentrantLockContainer = reentrantLockContainer;
        this.isInterruptibly = isInterruptibly;
        setUncaughtExceptionHandler(reentrantLockThreadGroup);
        setDefaultUncaughtExceptionHandler(reentrantLockThreadGroup);
        this.fiWaitOrSleep = fiWaitOrSleep;
    }

    ReentrantLockThreadGroup getReentrantLockThreadGroup() {
        return reentrantLockThreadGroup;
    }

    List<Throwable> getUncaughtExceptions() {
        return reentrantLockThreadGroup.getUncaughtExceptions().get(this);
    }

    @Override
    public void run() {
        logs.add(getLog(this, isInterruptibly, START));
        waitOrSleep();
        reentrantLockContainer.lockAndPerform(isInterruptibly);
        logs.add(getLog(this, isInterruptibly, FINISH));
    }

    public static String getLog(ReentrantLockAccessor reentrantLockAccessor, boolean isInterruptibly, StartOrFinish startOrFinish) {
        return String.format(
                "%s - %s accessing %s::lockAndPerform(isInterruptibly = %s)",
                reentrantLockAccessor.getName(),
                startOrFinish,
                ReentrantLockAccessor.class.getSimpleName(),
                isInterruptibly);
    }

    private void waitOrSleep() {
        fiWaitOrSleep.waitOrSleep();
    }

    static String getLogExceptionWaitOrSleep(Exception e) {
        return e + " - " + ReentrantLockAccessor.class.getSimpleName() + "::" + "waitOrSleep";
    }
}
