package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.AccessorMethodAbstractThread;

public class AccessorMethodInstanceSynchronizedObjectLock extends AccessorMethodAbstractThread {
    private static int instanceCounter = 1;

    private static final int threadIndex = 7;
    private final SynchronizedWaitingContainer synchronizedWaitingContainer;

    public AccessorMethodInstanceSynchronizedObjectLock(SynchronizedWaitingContainer synchronizedWaitingContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        threadIndex,
                        AccessorMethodInstanceSynchronizedObjectLock.class.getSimpleName(),
                        instanceCounter++));
        this.synchronizedWaitingContainer = synchronizedWaitingContainer;
    }

    @Override
    public void run() {
        addLog("starting");
        synchronizedWaitingContainer.instanceMethodSynchronizedObjectLock();
        addLog("finished");
    }

    @Override
    protected String getLog(String finishingOrStarting) {
        return String.format(
                "%s - %s accessing %s %s::%s",
                getName(),
                finishingOrStarting,
                "(Object/Instance)",
                SynchronizedWaitingContainer.class.getSimpleName(),
                "instanceMethodSynchronizedObjectLock");
    }
}
