package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

class SynchronizedContainer {
    private String stringVar;
    private static String staticStringVar;
    private String blockStringVar;
    private final Object lockObject = new Object();
    private Queue<Thread> lockWaitingTheads;
    private String lockStringVar;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private String reentrantLockStringVar;

    String getStringVar() {
        return stringVar;
    }

    synchronized void setStringVar(String stringVar) {
        this.stringVar = stringVar;
        sleep();
    }

    static String getStaticStringVar() {
        return staticStringVar;
    }

    synchronized static void setStaticStringVar(String staticStringVar) {
        SynchronizedContainer.staticStringVar = staticStringVar;
        sleep();
    }

    String getBlockStringVar() {
        return blockStringVar;
    }

    synchronized void setBlockStringVar(String blockStringVar) {
        this.blockStringVar = blockStringVar;
        sleep();
    }

    public String getLockStringVar() {
        return lockStringVar;
    }

    public void setLockStringVar(String lockStringVar) {
        synchronized (lockObject) {
            this.lockStringVar = lockStringVar;
            sleep();
        }
    }

    public String getReentrantLockStringVar() {
        return reentrantLockStringVar;
    }

    public void setReentrantLockStringVar(String reentrantLockStringVar) {
        reentrantLock.lock();
        try {
            this.reentrantLockStringVar = reentrantLockStringVar;
            sleep();
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
