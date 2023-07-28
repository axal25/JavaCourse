package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.SYNCHRONIZED_INSTANCE;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class SynchronizedInstanceAccessor extends AbstractInstanceAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = SYNCHRONIZED_INSTANCE;

    public SynchronizedInstanceAccessor(
            SynchronizedWaitingContainer synchronizedWaitingContainer,
            LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        SynchronizedInstanceAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer,
                synchronizedWaitingContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        getSynchronizedWaitingContainer().synchronizedInstance();
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
