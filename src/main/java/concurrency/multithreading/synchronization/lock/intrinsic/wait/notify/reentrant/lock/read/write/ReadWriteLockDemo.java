package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.read.write;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    private static final long SLEEP_DURATION_MILLIS = 1000L;

    private static class ReadWriteLockContainer {
        private final AtomicInteger operationCounter = new AtomicInteger(0);
        private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void read(Thread reader) {
            int operationId = getOperationId();
            System.out.println(operationId + ". read - start - " + reader.getName());

            if (readWriteLock.readLock().tryLock()) {
                System.out.println(operationId + ". read - start reading - " + reader.getName());

                try {
                    Thread.sleep(SLEEP_DURATION_MILLIS);
                } catch (InterruptedException e) {
                    System.out.println(operationId + ". read - " + reader.getName() + " - sleep exception " + e);
                    throw new RuntimeException(e);
                } finally {
                    readWriteLock.readLock().unlock();
                    System.out.println(operationId + ". read - finish reading - " + reader.getName());
                }
            } else {
                System.out.println(operationId + ". read - could not read - " + reader.getName());
            }

            System.out.println(operationId + ". read - finish - " + reader.getName());
        }

        void write(Thread writer) {
            int operationId = getOperationId();
            System.out.println(operationId + ". write - start - " + writer.getName());

            if (readWriteLock.writeLock().tryLock()) {
                System.out.println(operationId + ". write - start writing - " + writer.getName());

                try {
                    Thread.sleep(SLEEP_DURATION_MILLIS);
                } catch (InterruptedException e) {
                    System.out.println(operationId + ". write - " + writer.getName() + " - sleep exception " + e);
                    throw new RuntimeException(e);
                } finally {
                    readWriteLock.writeLock().unlock();
                    System.out.println(operationId + ". write - finish writing - " + writer.getName());
                }
            } else {
                System.out.println(operationId + ". write - could not write - " + writer.getName());
            }

            System.out.println(operationId + ". write - finish - " + writer.getName());
        }

        private int getOperationId() {
            Integer operationId = null;
            synchronized (operationCounter) {
                operationId = operationCounter.get();
                operationCounter.set(operationCounter.intValue() + 1);
            }
            return operationId;
        }
    }

    private static class ReaderThread extends Thread {
        private static int instanceCounter = 0;
        private final ReadWriteLockContainer container;

        ReaderThread(ReadWriteLockContainer container) {
            super("reader " + instanceCounter++);
            this.container = container;
        }

        @Override
        public void run() {
            container.read(this);
        }
    }

    private static class WriterThread extends Thread {
        private static int instanceCounter = 0;
        private final ReadWriteLockContainer container;

        WriterThread(ReadWriteLockContainer container) {
            super("writer " + instanceCounter++);
            this.container = container;
        }

        @Override
        public void run() {
            container.write(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        multipleReadersReadingThenWriterCannotWrite();
        multipleWritersCannotAccessReadWriteLockAtTheSameTime();
        writerPreventsReadersFromAccessingReadWriteLock();
    }

    private static void multipleReadersReadingThenWriterCannotWrite() throws InterruptedException {
        System.out.println("Demo: multiple " + ReaderThread.class.getSimpleName() +
                "s can access " + ReadWriteLock.class.getSimpleName() + " at the same time, but " +
                WriterThread.class.getSimpleName() + " cannot access if any " +
                ReaderThread.class.getSimpleName() + " is reading.");
        ReadWriteLockContainer container = new ReadWriteLockContainer();
        ReaderThread reader1 = new ReaderThread(container);
        ReaderThread reader2 = new ReaderThread(container);
        ReaderThread reader3 = new ReaderThread(container);
        WriterThread writer1 = new WriterThread(container);

        reader1.start();
        Thread.sleep(100);
        reader2.start();
        Thread.sleep(100);
        reader3.start();
        Thread.sleep(100);
        writer1.start();

        reader1.join();
        reader2.join();
        reader3.join();
        writer1.join();

        WriterThread writer2 = new WriterThread(container);
        writer2.start();
        writer2.join();
    }

    private static void multipleWritersCannotAccessReadWriteLockAtTheSameTime() throws InterruptedException {
        System.out.println("Multiple writers cannot access " + ReadWriteLock.class.getSimpleName() + " at the same time.");
        ReadWriteLockContainer container = new ReadWriteLockContainer();
        WriterThread writer1 = new WriterThread(container);
        WriterThread writer2 = new WriterThread(container);

        writer1.start();
        Thread.sleep(100);
        writer2.start();

        writer1.join();
        writer2.join();

        WriterThread writer3 = new WriterThread(container);
        writer3.start();
        writer3.join();
    }

    private static void writerPreventsReadersFromAccessingReadWriteLock() throws InterruptedException {
        System.out.println(WriterThread.class.getSimpleName() + " prevents " + ReaderThread.class.getSimpleName() + " from accessing " + ReadWriteLock.class.getSimpleName() + ".");
        ReadWriteLockContainer container = new ReadWriteLockContainer();
        WriterThread writer1 = new WriterThread(container);
        ReaderThread reader1 = new ReaderThread(container);

        writer1.start();
        Thread.sleep(100);
        reader1.start();

        writer1.join();
        reader1.join();

        ReaderThread reader2 = new ReaderThread(container);
        reader2.start();
        reader2.join();
    }
}

