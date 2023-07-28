package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.AccessorMethodAbstractThread;

public class AccessorMethodStaticSynchronizedClassBlock extends AccessorMethodAbstractThread {
    private static int instanceCounter = 1;
    private static final int THREAD_INDEX = 2;

    public AccessorMethodStaticSynchronizedClassBlock() {
        super(
                String.format(
                        "%d. %s-%d",
                        THREAD_INDEX,
                        AccessorMethodStaticSynchronizedClassBlock.class.getSimpleName(),
                        instanceCounter++));
    }

    @Override
    public void run() {
        addLog("starting");
        SynchronizedWaitingContainer.staticMethodSynchronizedClassBlock();
        addLog("finished");
    }

    @Override
    protected String getLog(String finishingOrStarting) {
        return String.format(
                "%s - %s accessing %s %s::%s",
                getName(),
                finishingOrStarting,
                "(Class/Static)",
                SynchronizedWaitingContainer.class.getSimpleName(),
                "staticMethodSynchronizedClassBlock");
    }
}
