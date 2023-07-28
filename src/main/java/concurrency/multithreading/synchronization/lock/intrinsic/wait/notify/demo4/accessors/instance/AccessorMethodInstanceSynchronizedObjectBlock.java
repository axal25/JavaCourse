package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.AccessorMethodAbstractThread;

public class AccessorMethodInstanceSynchronizedObjectBlock extends AccessorMethodAbstractThread {
    private static int instanceCounter = 1;

    private static final int threadIndex = 6;
    private final SynchronizedWaitingContainer synchronizedWaitingContainer;

    public AccessorMethodInstanceSynchronizedObjectBlock(SynchronizedWaitingContainer synchronizedWaitingContainer) {
        super(
                String.format(
                        "%d. %s-%d",
                        threadIndex,
                        AccessorMethodInstanceSynchronizedObjectBlock.class.getSimpleName(),
                        instanceCounter++));
        this.synchronizedWaitingContainer = synchronizedWaitingContainer;
    }

    @Override
    public void run() {
        addLog("starting");
        synchronizedWaitingContainer.instanceMethodSynchronizedObjectBlock();
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
                "instanceMethodSynchronizedObjectBlock");
    }
}
