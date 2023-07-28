package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.AccessorMethodAbstractThread;

public class AccessorMethodStaticSynchronizedClassLock extends AccessorMethodAbstractThread {
    private static int instanceCounter = 1;
    private static final int THREAD_INDEX = 3;

    public AccessorMethodStaticSynchronizedClassLock() {
        super(
                String.format(
                        "%d. %s-%d",
                        THREAD_INDEX,
                        AccessorMethodStaticSynchronizedClassLock.class.getSimpleName(),
                        instanceCounter++));
    }

    @Override
    public void run() {
        addLog("starting");
        SynchronizedWaitingContainer.staticMethodSynchronizedClassLock();
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
                "staticMethodSynchronizedClassLock");
    }
}
