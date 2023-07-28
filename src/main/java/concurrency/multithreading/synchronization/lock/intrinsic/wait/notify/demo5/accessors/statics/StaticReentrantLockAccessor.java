package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.STATIC_REENTRANT_LOCK;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.staticReentrantLock;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class StaticReentrantLockAccessor extends AbstractStaticAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = STATIC_REENTRANT_LOCK;

    public StaticReentrantLockAccessor(LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        StaticReentrantLockAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        staticReentrantLock(logsContainer);
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
