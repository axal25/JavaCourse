package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4;

import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedWaitingContainer {

    private static final Object classLock = new Object();
    private static final ReentrantLock classReentrantLock = new ReentrantLock();
    private static int instance_counter = 1;
    private final String name;
    private final Object objectLock = new Object();
    private final ReentrantLock objectReentrantLock = new ReentrantLock();

    SynchronizedWaitingContainer() {
        name = String.format(
                "%s-%d",
                SynchronizedWaitingContainer.class.getSimpleName(),
                instance_counter++);
    }

    public synchronized static String synchronizedStaticMethod() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "synchronizedStaticMethod";
        int methodIndex = 1;
        log(methodIndex, className, methodName, "starting");
        safeWait();
        log(methodIndex, className, methodName, "finishing");
        return methodName;
    }

    public static String staticMethodSynchronizedClassBlock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "staticMethodSynchronizedClassBlock";
        int methodIndex = 2;

        synchronized (SynchronizedWaitingContainer.class) {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        }

        return methodName;
    }

    public static String staticMethodSynchronizedClassLock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "staticMethodSynchronizedStaticLock";
        int methodIndex = 3;

        synchronized (classLock) {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        }

        return methodName;
    }

    public static String staticMethodClassReentrantLock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "staticMethodClassReentrantLock";
        int methodIndex = 4;

        classReentrantLock.lock();
        try {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        } finally {
            classReentrantLock.unlock();
        }

        return methodName;
    }

    public synchronized String synchronizedObjectInstanceMethod() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "synchronizedObjectInstanceMethod";
        int methodIndex = 5;
        log(methodIndex, className, methodName, "starting");
        safeWait();
        log(methodIndex, className, methodName, "finishing");
        return methodName;
    }

    public String instanceMethodSynchronizedObjectBlock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "instanceMethodSynchronizedObjectBlock";
        int methodIndex = 6;

        synchronized (this) {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        }

        return methodName;
    }

    public String instanceMethodSynchronizedObjectLock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "instanceMethodSynchronizedInstanceLock";
        int methodIndex = 7;

        synchronized (objectLock) {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        }

        return methodName;
    }

    public String instanceMethodObjectReentrantLock() {
        String className = SynchronizedWaitingContainer.class.getSimpleName();
        String methodName = "instanceMethodObjectReentrantLock";
        int methodIndex = 8;

        objectReentrantLock.lock();
        try {
            log(methodIndex, className, methodName, "starting");
            safeWait();
            log(methodIndex, className, methodName, "finishing");
        } finally {
            objectReentrantLock.unlock();
        }

        return methodName;
    }

    private static void log(int methodIndex, String instanceOrClassName, String methodName, String finishingOrStarting) {
        String log = String.format("%d. %s::%s - %s wait - thread: %s",
                methodIndex,
                instanceOrClassName,
                methodName,
                finishingOrStarting,
                Thread.currentThread().getName());
        Logger.printAndAddToLogger("\t\t", log);
    }

    private synchronized static void safeWait() {
        Thread currentThread = Thread.currentThread();
        synchronized (currentThread) {
            try {
                currentThread.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
