package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.SYNCHRONIZED_STATIC_LOCK;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class SynchronizedStaticLockAccessor extends AbstractStaticAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = SYNCHRONIZED_STATIC_LOCK;

    public SynchronizedStaticLockAccessor(LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        SynchronizedStaticLockAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        SynchronizedWaitingContainer.synchronizedStaticLock(logsContainer);
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
