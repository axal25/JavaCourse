package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method.SYNCHRONIZED_CLASS_BLOCK;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

public class SynchronizedClassBlockAccessor extends AbstractStaticAccessorMethodThread {
    private static int instanceCounter = 1;
    private static final Method method = SYNCHRONIZED_CLASS_BLOCK;

    public SynchronizedClassBlockAccessor(LogsContainer logsContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        method.getIndex(),
                        SynchronizedClassBlockAccessor.class.getSimpleName(),
                        instanceCounter++),
                logsContainer);
    }

    @Override
    public void run() {
        log(STARTING);
        SynchronizedWaitingContainer.synchronizedClassBlock(logsContainer);
        log(FINISHING);
    }

    @Override
    public String getAccessedMethodName() {
        return method.getName();
    }
}
