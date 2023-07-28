package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance.AccessorMethodInstanceObjectReentrantLock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance.AccessorMethodInstanceSynchronizedObjectBlock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance.AccessorMethodInstanceSynchronizedObjectLock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.instance.AccessorMethodSynchronizedObjectInstance;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics.AccessorMethodStaticClassReentrantLock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics.AccessorMethodStaticSynchronizedClassBlock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics.AccessorMethodStaticSynchronizedClassLock;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.accessors.statics.AccessorMethodSynchronizedStatic;

import java.util.stream.Collectors;

public class IntrinsicLocksWithWaitMain {

    private static final long DEFAULT_SLEEP_DURATION_MILLIS = 1000L;

    private IntrinsicLocksWithWaitMain() {
    }

    public static void main(String[] args) {
        IntrinsicLocksWithWaitMain.allAccessors();

        System.out.println("\nLogger.getLogsCopy():");
        System.out.println(Logger.getLogsCopy().stream().map(log -> String.format("\"%s\"", log)).collect(Collectors.joining(", ")));

        System.out.println("\nLogger.toPrettyString():");
        System.out.println(Logger.toPrettyString());

        Logger.resetLogs();
    }

    private static void allAccessors() {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main \\/\\/\\/");

        SynchronizedWaitingContainer synchronizedWaitingContainer = new SynchronizedWaitingContainer();

        staticClassAccessors();
        staticClassLockAccessors();
        staticClassReentrantLockAccessors();

        objectInstanceAccessors(synchronizedWaitingContainer);
        objectInstanceLockAccessors(synchronizedWaitingContainer);
        objectInstanceReentrantLockAccessors(synchronizedWaitingContainer);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main /\\/\\/\\");
    }

    private static void staticClassAccessors() {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Static/Class \\/\\/\\/");

        AccessorMethodSynchronizedStatic syncStat =
                new AccessorMethodSynchronizedStatic();
        AccessorMethodStaticSynchronizedClassBlock statSyncClassBlock =
                new AccessorMethodStaticSynchronizedClassBlock();

        startAndLog(syncStat);
        startAndLog(statSyncClassBlock);

        safeSleep();

        AccessorNotifier syncStatNotifier = new AccessorNotifier(syncStat);
        syncStatNotifier.start();

        safeSleep();

        AccessorNotifier statSyncClassBlockNotifier = new AccessorNotifier(statSyncClassBlock);
        statSyncClassBlockNotifier.start();

        safeSleep();

        safeJoin(syncStatNotifier);
        safeJoinAndLog(syncStat);

        safeJoin(statSyncClassBlockNotifier);
        safeJoinAndLog(statSyncClassBlock);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Static/Class /\\/\\/\\");
    }

    private static void staticClassLockAccessors() {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Static/Class Lock \\/\\/\\/");

        AccessorMethodStaticSynchronizedClassLock statSyncClassLock1 =
                new AccessorMethodStaticSynchronizedClassLock();
        AccessorMethodStaticSynchronizedClassLock statSyncClassLock2 =
                new AccessorMethodStaticSynchronizedClassLock();

        startAndLog(statSyncClassLock1);
        startAndLog(statSyncClassLock2);

        safeSleep();

        AccessorNotifier statSyncClassLockNotifier1 = new AccessorNotifier(statSyncClassLock1);
        statSyncClassLockNotifier1.start();

        safeSleep();

        AccessorNotifier statSyncClassLockNotifier2 = new AccessorNotifier(statSyncClassLock2);
        statSyncClassLockNotifier2.start();

        safeSleep();

        safeJoin(statSyncClassLockNotifier2);
        safeJoinAndLog(statSyncClassLock2);

        safeJoin(statSyncClassLockNotifier1);
        safeJoinAndLog(statSyncClassLock1);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Static/Class Lock /\\/\\/\\");
    }

    private static void staticClassReentrantLockAccessors() {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Static/Class Reentrant Lock \\/\\/\\/");

        AccessorMethodStaticClassReentrantLock statClassReentrantLock1 =
                new AccessorMethodStaticClassReentrantLock();
        AccessorMethodStaticClassReentrantLock statClassReentrantLock2 =
                new AccessorMethodStaticClassReentrantLock();

        startAndLog(statClassReentrantLock1);
        startAndLog(statClassReentrantLock2);

        safeSleep();

        AccessorNotifier statClassReentrantLockNotifier1 = new AccessorNotifier(statClassReentrantLock1);
        statClassReentrantLockNotifier1.start();

        safeSleep();

        AccessorNotifier statClassReentrantLockNotifier2 = new AccessorNotifier(statClassReentrantLock2);
        statClassReentrantLockNotifier2.start();

        safeSleep();

        safeJoin(statClassReentrantLockNotifier1);
        safeJoinAndLog(statClassReentrantLock1);

        safeJoin(statClassReentrantLockNotifier2);
        safeJoinAndLog(statClassReentrantLock2);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Static/Class Reentrant Lock /\\/\\/\\");
    }

    private static void objectInstanceLockAccessors(SynchronizedWaitingContainer synchronizedWaitingContainer) {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Object/Instance Lock \\/\\/\\/");

        AccessorMethodInstanceSynchronizedObjectLock instSyncObjLock1 =
                new AccessorMethodInstanceSynchronizedObjectLock(synchronizedWaitingContainer);
        AccessorMethodInstanceSynchronizedObjectLock instSyncObjLock2 =
                new AccessorMethodInstanceSynchronizedObjectLock(synchronizedWaitingContainer);

        startAndLog(instSyncObjLock1);
        startAndLog(instSyncObjLock2);

        safeSleep();

        AccessorNotifier instSyncObjLockNotifier1 = new AccessorNotifier(instSyncObjLock1);
        instSyncObjLockNotifier1.start();

        safeSleep();

        AccessorNotifier instSyncObjLockNotifier2 = new AccessorNotifier(instSyncObjLock2);
        instSyncObjLockNotifier2.start();

        safeSleep();

        safeJoin(instSyncObjLockNotifier1);
        safeJoinAndLog(instSyncObjLock1);

        safeJoin(instSyncObjLockNotifier2);
        safeJoinAndLog(instSyncObjLock2);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Object/Instance Lock /\\/\\/\\");
    }

    private static void objectInstanceReentrantLockAccessors(SynchronizedWaitingContainer synchronizedWaitingContainer) {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Object/Instance Reentrant Lock \\/\\/\\/");

        AccessorMethodInstanceObjectReentrantLock instObjReentrantLock1 =
                new AccessorMethodInstanceObjectReentrantLock(synchronizedWaitingContainer);
        AccessorMethodInstanceObjectReentrantLock instObjReentrantLock2 =
                new AccessorMethodInstanceObjectReentrantLock(synchronizedWaitingContainer);

        startAndLog(instObjReentrantLock1);
        startAndLog(instObjReentrantLock2);

        safeSleep();

        AccessorNotifier instObjReentrantLockNotifier1 = new AccessorNotifier(instObjReentrantLock1);
        instObjReentrantLockNotifier1.start();

        safeSleep();

        AccessorNotifier instObjReentrantLockNotifier2 = new AccessorNotifier(instObjReentrantLock2);
        instObjReentrantLockNotifier2.start();

        safeSleep();

        safeJoin(instObjReentrantLockNotifier1);
        safeJoinAndLog(instObjReentrantLock1);

        safeJoin(instObjReentrantLockNotifier2);
        safeJoinAndLog(instObjReentrantLock2);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Object/Instance Reentrant Lock /\\/\\/\\");
    }

    private static void objectInstanceAccessors(SynchronizedWaitingContainer synchronizedWaitingContainer) {
        System.out.println("\\/\\/\\/ Intrinsic Locks Main - Object/Instance \\/\\/\\/");

        AccessorMethodSynchronizedObjectInstance syncObjInst =
                new AccessorMethodSynchronizedObjectInstance(synchronizedWaitingContainer);
        AccessorMethodInstanceSynchronizedObjectBlock instSyncObjBlock =
                new AccessorMethodInstanceSynchronizedObjectBlock(synchronizedWaitingContainer);

        startAndLog(syncObjInst);

        startAndLog(instSyncObjBlock);

        safeSleep();

        AccessorNotifier syncObjInstNotifier = new AccessorNotifier(syncObjInst);
        syncObjInstNotifier.start();

        safeSleep();

        AccessorNotifier instSyncObjBlockNotifier = new AccessorNotifier(instSyncObjBlock);
        instSyncObjBlockNotifier.start();

        safeSleep();

        safeJoin(syncObjInstNotifier);
        safeJoinAndLog(syncObjInst);

        safeJoin(instSyncObjBlockNotifier);
        safeJoinAndLog(instSyncObjBlock);

        System.out.println("/\\/\\/\\ Intrinsic Locks Main - Object/Instance /\\/\\/\\");
    }

    private static void safeSleep() {
        safeSleep(DEFAULT_SLEEP_DURATION_MILLIS);
    }

    private static void safeSleep(long sleepDurationMillis) {
        try {
            Thread.sleep(sleepDurationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startAndLog(Thread thread) {
        Logger.printAndAddToLogger("", String.format("%s starting thread: %s", Thread.currentThread().getName(), thread.getName()));
        thread.start();
    }

    private static void safeJoinAndLog(Thread thread) {
        Logger.printAndAddToLogger("", String.format("%s joining thread: %s", Thread.currentThread().getName(), thread.getName()));
        safeJoin(thread);
        Logger.printAndAddToLogger("", String.format("%s joined thread: %s", Thread.currentThread().getName(), thread.getName()));
    }

    private static void safeJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
