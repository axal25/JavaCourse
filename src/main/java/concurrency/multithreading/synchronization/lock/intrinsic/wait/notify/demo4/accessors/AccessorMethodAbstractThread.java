package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.Logger;

public abstract class AccessorMethodAbstractThread extends Thread {

    protected AccessorMethodAbstractThread(String name) {
        super(name);
    }

    @Override
    public abstract void run();

    protected abstract String getLog(String finishingOrStarting);

    protected void addLog(String finishingOrStarting) {
        String log = getLog(finishingOrStarting);
        Logger.printAndAddToLogger("\t", log);
    }
}
