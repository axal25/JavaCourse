package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.trylock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTryLockDemo {

    private static final long SLEEP_DURATION_MILLIS = 1000L;

    private static class Timer {
        private final long start = System.currentTimeMillis();

        long getElapsed() {
            return System.currentTimeMillis() - start;
        }
    }

    private static class ReentrantLockTryLockIfElseAcquirerThread extends Thread {

        private static int instanceCounter = 0;
        private final ReentrantLock reentrantLock;

        ReentrantLockTryLockIfElseAcquirerThread(ReentrantLock reentrantLock) {
            super(ReentrantLockTryLockIfElseAcquirerThread.class.getSimpleName() + "-" + instanceCounter++);
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            tryLockIfElse();
        }

        private void tryLockIfElse() {
            if (reentrantLock.tryLock()) {
                System.out.println(getName() + " tryLock() == true - lock acquired - guarded action");

                try {
                    Timer sleepTimer = new Timer();
                    Thread.sleep(SLEEP_DURATION_MILLIS);
                    System.out.println(getName() + " slept (guarded) for " + sleepTimer.getElapsed() + " millis");
                } catch (InterruptedException e) {
                    System.out.println(getName() + " guarded sleep exception " + e);
                    throw new RuntimeException(e);
                } finally {
                    System.out.println(getName() + " unlock()");
                    reentrantLock.unlock();
                }
            } else {
                System.out.println(getName() + " tryLock() == false - lock NOT acquired - UNguarded action");

                try {
                    Timer sleepTimer = new Timer();
                    sleep(SLEEP_DURATION_MILLIS);
                    System.out.println(getName() + " slept (UNguarded) for " + sleepTimer.getElapsed() + " millis");
                } catch (InterruptedException e) {
                    System.out.println(getName() + " UNguarded sleep exception " + e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class ReentrantLockTryLockTimeoutAcquirerThread extends Thread {

        private static int instanceCounter = 0;
        private final ReentrantLock reentrantLock;
        private final Long timeout;
        private final TimeUnit timeUnit;

        ReentrantLockTryLockTimeoutAcquirerThread(ReentrantLock reentrantLock, Long timeout, TimeUnit timeUnit) {
            super(ReentrantLockTryLockIfElseAcquirerThread.class.getSimpleName() + "-" + instanceCounter++);
            this.reentrantLock = reentrantLock;
            validate(timeout, timeUnit);
            this.timeout = timeout;
            this.timeUnit = timeUnit;
        }

        private void validate(Long timeout, TimeUnit timeUnit) {
            if (timeout == null || timeUnit == null) {
                throw new UnsupportedOperationException(TimeUnit.class.getSimpleName() + " and Timeout " + Long.class.getSimpleName() + " must both be not null.");
            }
        }

        @Override
        public void run() {
            tryLock();
        }

        private void tryLock() {
            Timer timeoutTimer = new Timer();
            try {
                if (reentrantLock.tryLock(timeout, timeUnit)) {
                    System.out.println(getName() +
                            " tryLock(...) == true - lock acquired - guarded action - " +
                            "tryLock(...) timeout timer = " + timeoutTimer.getElapsed());

                    try {
                        Timer sleepTimer = new Timer();
                        Thread.sleep(SLEEP_DURATION_MILLIS);
                        System.out.println(getName() + " slept (guarded) for " + sleepTimer.getElapsed() + " millis");
                    } catch (InterruptedException e) {
                        System.out.println(getName() + " guarded sleep exception " + e);
                        throw new RuntimeException(e);
                    } finally {
                        System.out.println(getName() + " unlock()");
                        reentrantLock.unlock();
                    }
                } else {
                    System.out.println(getName() +
                            " tryLock(...) == false - lock NOT acquired - UNguarded action - " +
                            "tryLock(...) timeout timer = " + timeoutTimer.getElapsed());

                    try {
                        Timer sleepTimer = new Timer();
                        sleep(SLEEP_DURATION_MILLIS);
                        System.out.println(getName() + " slept (UNguarded) for " + sleepTimer.getElapsed() + " millis");
                    } catch (InterruptedException e) {
                        System.out.println(getName() + " UNguarded sleep exception " + e);
                        throw new RuntimeException(e);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(getName() +
                        "tryLock(long Timeout, " +
                        TimeUnit.class.getSimpleName() + " timeUnit)" +
                        " timed-out after " + timeoutTimer.getElapsed() +
                        " exception " + e);
                throw new RuntimeException(e);
            } finally {
                System.out.println(getName() + " unlock()");
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        demoNoArgsTryLock();
        demoTimeoutArgsTryLockTimeoutDurationLimitNotExceeded();
        demoTimeoutArgsTryLockTimeoutDurationLimitExceeded();
    }

    private static void demoNoArgsTryLock() throws InterruptedException {
        System.out.println("Demo - no args - tryLock() - " +
                Thread.class.getSimpleName() +
                " that has no access to " +
                ReentrantLock.class.getSimpleName() +
                " performs different, UNguarded action - based on tryLock() result");
        ReentrantLock reentrantLock = new ReentrantLock();
        ReentrantLockTryLockIfElseAcquirerThread t1 = new ReentrantLockTryLockIfElseAcquirerThread(reentrantLock);
        ReentrantLockTryLockIfElseAcquirerThread t2 = new ReentrantLockTryLockIfElseAcquirerThread(reentrantLock);

        t1.start();
        Thread.sleep(100L);
        t2.start();

        t1.join();
        t2.join();
    }

    private static void demoTimeoutArgsTryLockTimeoutDurationLimitNotExceeded() throws InterruptedException {
        System.out.println("Demo - timeout Args - tryLock(long timeout, " +
                TimeUnit.class.getSimpleName() +
                " timeUnit) - timeout length is longer than sleep duration - " +
                Thread.class.getSimpleName() +
                " that at first has no access to " +
                ReentrantLock.class.getSimpleName() +
                " performs same, guarded action, but after waiting for lock to be released");
        ReentrantLock reentrantLock = new ReentrantLock();
        long timeout = SLEEP_DURATION_MILLIS * 2;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        ReentrantLockTryLockTimeoutAcquirerThread t1 = new ReentrantLockTryLockTimeoutAcquirerThread(reentrantLock, timeout, timeUnit);
        ReentrantLockTryLockTimeoutAcquirerThread t2 = new ReentrantLockTryLockTimeoutAcquirerThread(reentrantLock, timeout, timeUnit);

        t1.start();
        Thread.sleep(100L);
        t2.start();

        t1.join();
        t2.join();
    }

    private static void demoTimeoutArgsTryLockTimeoutDurationLimitExceeded() throws InterruptedException {
        System.out.println("Demo - timeout Args - tryLock(long timeout, " +
                TimeUnit.class.getSimpleName() +
                " timeUnit) - timeout length is shorter than sleep duration - " +
                Thread.class.getSimpleName() +
                " that has no access to " +
                ReentrantLock.class.getSimpleName() +
                " performs different, UNguarded action, after timeout period elapses");
        ReentrantLock reentrantLock = new ReentrantLock();
        long timeout = SLEEP_DURATION_MILLIS / 2;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        ReentrantLockTryLockTimeoutAcquirerThread t1 = new ReentrantLockTryLockTimeoutAcquirerThread(reentrantLock, timeout, timeUnit);
        ReentrantLockTryLockTimeoutAcquirerThread t2 = new ReentrantLockTryLockTimeoutAcquirerThread(reentrantLock, timeout, timeUnit);

        t1.start();
        Thread.sleep(100L);
        t2.start();

        t1.join();
        t2.join();
    }
}
