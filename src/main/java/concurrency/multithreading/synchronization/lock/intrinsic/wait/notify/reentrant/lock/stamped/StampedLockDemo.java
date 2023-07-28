package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.stamped;

import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    private static final long SLEEP_DURATION_MILLIS = 1000;

    private static class StampedLockContainer {
        private static int operationCounter = 1;
        private final StampedLock stampedLock = new StampedLock();

        public void read(Thread thread, boolean isOptimistic) {
            int operationId = operationCounter++;
            String operationName = isOptimistic
                    ? "optimistic read"
                    : "read";

            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | readLock() - requesting");
            long stamp = isOptimistic
                    ? stampedLock.tryOptimisticRead()
                    : stampedLock.readLock();
            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | readLock() - received");

            try {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | sleeping");
                Thread.sleep(SLEEP_DURATION_MILLIS);
            } catch (InterruptedException e) {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | sleep exception:" + e);
                throw new RuntimeException(e);
            } finally {
                if (isOptimistic) {
                    System.out.println(
                            getLogPrefix(thread, operationId, operationName) +
                                    " | validate(stamp=" + stamp + ") = " +
                                    stampedLock.validate(stamp) +
                                    " - checking if stamp is valid");
                } else {
                    System.out.println(
                            getLogPrefix(thread, operationId, operationName) +
                                    " | unlockRead(stamp)");
                    stampedLock.unlockRead(stamp);
                }
            }
        }

        public void write(Thread thread) {
            int operationId = operationCounter++;
            String operationName = "write";

            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | writeLock() - requesting");
            long stamp = stampedLock.writeLock();
            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | writeLock() - received");
            try {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | sleeping");
                Thread.sleep(SLEEP_DURATION_MILLIS);
            } catch (InterruptedException e) {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | sleep exception:" + e);
                throw new RuntimeException(e);
            } finally {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | unlockRead(stamp)");
                stampedLock.unlockWrite(stamp);
            }
        }

        private String getLogPrefix(
                Thread thread,
                int operationId,
                String operationName) {
            return "operation: " + operationId + ", " + operationName +
                    ", thread: " + thread.getName();
        }
    }

    private static class ReaderThread extends Thread {
        private static int instanceCounter = 0;
        private final StampedLockContainer container;
        private final boolean isOptimistic;

        ReaderThread(StampedLockContainer container, boolean isOptimistic) {
            super(
                    (isOptimistic
                            ? "optimisticReader-"
                            : "reader-")
                            + instanceCounter++);
            this.container = container;
            this.isOptimistic = isOptimistic;
        }

        @Override
        public void run() {
            container.read(this, isOptimistic);
        }
    }

    private static class WriterThread extends Thread {
        private static int instanceCounter = 0;
        private final StampedLockContainer container;

        WriterThread(StampedLockContainer container) {
            super("writer-" + instanceCounter++);
            this.container = container;
        }

        @Override
        public void run() {
            container.write(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        readersAndOptimisticReadersCanAccessStampedLockAtTheSameTime();
        readersAndWriterCanNotAccessStampedLockAtTheSameTime();
        optimisticReadersAndWriterCanAccessStampedLockAtTheSameTimeButOptimisticReaderStampBecomesInvalid();
        writersCanNotAccessStampedLockAtTheSameTime();
    }

    private static void readersAndOptimisticReadersCanAccessStampedLockAtTheSameTime() throws InterruptedException {
        System.out.println(ReaderThread.class.getSimpleName() + "s and Optimistic" +
                ReaderThread.class.getSimpleName() + "s can access " + StampedLock.class.getSimpleName() +
                " at the same time.");
        StampedLockContainer container = new StampedLockContainer();
        ReaderThread reader1 = new ReaderThread(container, false);
        ReaderThread reader2 = new ReaderThread(container, true);
        ReaderThread reader3 = new ReaderThread(container, false);
        ReaderThread reader4 = new ReaderThread(container, true);

        reader1.start();
        Thread.sleep(100);
        reader2.start();
        Thread.sleep(100);
        reader3.start();
        Thread.sleep(100);
        reader4.start();

        reader1.join();
        reader2.join();
        reader3.join();
        reader4.join();
    }

    private static void readersAndWriterCanNotAccessStampedLockAtTheSameTime() throws InterruptedException {
        System.out.println(ReaderThread.class.getSimpleName() + "s and " +
                WriterThread.class.getSimpleName() + "s can NOT access " +
                StampedLock.class.getSimpleName() + " at the same time.");
        StampedLockContainer container = new StampedLockContainer();
        ReaderThread reader1 = new ReaderThread(container, false);
        WriterThread writer1 = new WriterThread(container);
        ReaderThread reader2 = new ReaderThread(container, false);

        reader1.start();
        Thread.sleep(100);
        writer1.start();
        Thread.sleep(100);
        reader2.start();

        reader1.join();
        writer1.join();
        reader2.join();
    }

    private static void optimisticReadersAndWriterCanAccessStampedLockAtTheSameTimeButOptimisticReaderStampBecomesInvalid() throws InterruptedException {
        System.out.println("Optimistic" + ReaderThread.class.getSimpleName() + " and " +
                WriterThread.class.getSimpleName() + " can access " +
                StampedLock.class.getSimpleName() + " at the same time, but Optimistic" +
                ReaderThread.class.getSimpleName() + " stamp becomes invalid after " +
                WriterThread.class.getSimpleName() + " accesses " +
                StampedLock.class.getSimpleName() + ".");
        StampedLockContainer container = new StampedLockContainer();
        ReaderThread reader1 = new ReaderThread(container, true);
        ReaderThread reader2 = new ReaderThread(container, false);
        WriterThread writer1 = new WriterThread(container);
        ReaderThread reader3 = new ReaderThread(container, true);

        reader1.start();
        Thread.sleep(100);
        reader2.start();
        Thread.sleep(100);
        writer1.start();
        Thread.sleep(100);
        reader3.start();

        reader1.join();
        reader2.join();
        writer1.join();
        reader2.join();
    }

    private static void writersCanNotAccessStampedLockAtTheSameTime() throws InterruptedException {
        System.out.println("Multiple " + WriterThread.class.getSimpleName() + "s can NOT access " +
                StampedLock.class.getSimpleName() + " at the same time.");
        StampedLockContainer container = new StampedLockContainer();
        WriterThread writer1 = new WriterThread(container);
        WriterThread writer2 = new WriterThread(container);
        WriterThread writer3 = new WriterThread(container);

        writer1.start();
        Thread.sleep(100);
        writer2.start();
        Thread.sleep(100);
        writer3.start();

        writer1.join();
        writer2.join();
        writer3.join();
    }
}
