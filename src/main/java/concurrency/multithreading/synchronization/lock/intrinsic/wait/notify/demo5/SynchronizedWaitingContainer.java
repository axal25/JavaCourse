package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.locks.ReentrantLock;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

@AllArgsConstructor
public class SynchronizedWaitingContainer {
    static final String CLASS_NAME = SynchronizedWaitingContainer.class.getSimpleName();

    @Getter
    @AllArgsConstructor
    public enum Method {
        SYNCHRONIZED_STATIC(1, "synchronizedStatic"),
        SYNCHRONIZED_CLASS_BLOCK(2, "synchronizedClassBlock"),
        SYNCHRONIZED_STATIC_LOCK(3, "staticMethodSynchronizedClassLock"),
        STATIC_REENTRANT_LOCK(4, "staticMethodClassReentrantLock"),
        SYNCHRONIZED_INSTANCE(5, "synchronizedObjectInstanceMethod"),
        SYNCHRONIZED_INSTANCE_BLOCK(6, "instanceMethodSynchronizedObjectBlock"),
        SYNCHRONIZED_INSTANCE_LOCK(7, "instanceMethodSynchronizedObjectLock"),
        INSTANCE_REENTRANT_LOCK(8, "instanceMethodObjectReentrantLock");


        private final int index;
        private final String name;
    }

    private static int instance_counter = 1;

    @Getter
    private final String instanceName = String.format(
            "%s-%d",
            SynchronizedWaitingContainer.class.getSimpleName(),
            instance_counter++);
    private static final Object staticLock = new Object();
    private static final ReentrantLock staticReentrantLock = new ReentrantLock();
    private final Object instanceLock = new Object();
    private final ReentrantLock instanceReentrantLock = new ReentrantLock();

    private final LogsContainer logsContainer;

    public synchronized static void synchronizedStatic(LogsContainer logsContainer) {
        Method method = Method.SYNCHRONIZED_STATIC;

        logStatic(method, STARTING, logsContainer);
        safeWaitCurrentThread();
        logStatic(method, FINISHING, logsContainer);
    }

    public static void synchronizedClassBlock(LogsContainer logsContainer) {
        Method method = Method.SYNCHRONIZED_CLASS_BLOCK;

        synchronized (SynchronizedWaitingContainer.class) {
            logStatic(method, STARTING, logsContainer);
            safeWaitCurrentThread();
            logStatic(method, FINISHING, logsContainer);
        }
    }

    public static void synchronizedStaticLock(LogsContainer logsContainer) {
        Method method = Method.SYNCHRONIZED_STATIC_LOCK;

        synchronized (staticLock) {
            logStatic(method, STARTING, logsContainer);
            safeWaitCurrentThread();
            logStatic(method, FINISHING, logsContainer);
        }
    }

    public static void staticReentrantLock(LogsContainer logsContainer) {
        Method method = Method.STATIC_REENTRANT_LOCK;

        staticReentrantLock.lock();
        try {
            logStatic(method, STARTING, logsContainer);
            safeWaitCurrentThread();
            logStatic(method, FINISHING, logsContainer);
        } finally {
            staticReentrantLock.unlock();
        }
    }

    public synchronized void synchronizedInstance() {
        Method method = Method.SYNCHRONIZED_INSTANCE;

        logInstance(method, STARTING);
        safeWaitCurrentThread();
        logInstance(method, FINISHING);
    }

    public void synchronizedInstanceBlock() {
        Method method = Method.SYNCHRONIZED_INSTANCE_BLOCK;

        synchronized (this) {
            logInstance(method, STARTING);
            safeWaitCurrentThread();
            logInstance(method, FINISHING);
        }
    }

    public void synchronizedInstanceLock() {
        Method method = Method.SYNCHRONIZED_INSTANCE_LOCK;

        synchronized (instanceLock) {
            logInstance(method, STARTING);
            safeWaitCurrentThread();
            logInstance(method, FINISHING);
        }
    }

    public void instanceReentrantLock() {
        Method method = Method.INSTANCE_REENTRANT_LOCK;

        instanceReentrantLock.lock();
        try {
            logInstance(method, STARTING);
            safeWaitCurrentThread();
            logInstance(method, FINISHING);
        } finally {
            instanceReentrantLock.unlock();
        }
    }

    private void logInstance(Method method, StartingOrFinishing startingOrFinishing) {
        logsContainer.addInstance(
                getLog(
                        method,
                        instanceName,
                        startingOrFinishing,
                        Thread.currentThread()));
    }

    private static void logStatic(Method method, StartingOrFinishing startingOrFinishing, LogsContainer logsContainer) {
        logsContainer.addStatic(
                getLog(
                        method,
                        CLASS_NAME,
                        startingOrFinishing,
                        Thread.currentThread()));
    }

    public static String getLog(Method method, String instanceOrClassName, StartingOrFinishing startingOrFinishing, Thread thread) {
        return String.format("%d. %s::%s - %s wait - thread: %s",
                method.index,
                instanceOrClassName,
                method.name,
                startingOrFinishing.getValue(),
                thread.getName());
    }

    private static void safeWaitCurrentThread() {
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
