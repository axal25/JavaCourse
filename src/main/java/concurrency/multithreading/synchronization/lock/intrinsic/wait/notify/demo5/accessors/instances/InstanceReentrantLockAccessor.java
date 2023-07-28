package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.INSTANCE_REENTRANT_LOCK;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class InstanceReentrantLockAccessor extends AbstractInstanceAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = INSTANCE_REENTRANT_LOCK;

    public InstanceReentrantLockAccessor(
            SynchronizedWaitingContainer synchronizedWaitingContainer,
            LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        InstanceReentrantLockAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer,
                synchronizedWaitingContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        getSynchronizedWaitingContainer().instanceReentrantLock();
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
