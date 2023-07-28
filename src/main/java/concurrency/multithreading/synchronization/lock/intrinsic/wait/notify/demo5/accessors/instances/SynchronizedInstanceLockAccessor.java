package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.SYNCHRONIZED_INSTANCE_LOCK;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class SynchronizedInstanceLockAccessor extends AbstractInstanceAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = SYNCHRONIZED_INSTANCE_LOCK;

    public SynchronizedInstanceLockAccessor(
            SynchronizedWaitingContainer synchronizedWaitingContainer,
            LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        SynchronizedInstanceLockAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer,
                synchronizedWaitingContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        getSynchronizedWaitingContainer().synchronizedInstanceLock();
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
