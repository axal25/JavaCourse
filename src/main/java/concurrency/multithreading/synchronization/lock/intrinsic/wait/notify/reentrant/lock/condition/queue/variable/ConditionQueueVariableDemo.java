package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.condition.queue.variable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionQueueVariableDemo {

    private static class ConditionContainer {
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition readCondition = lock.newCondition();
        private final Condition writeCondition = lock.newCondition();
        private boolean isReadable = false;
        private boolean isWritable = true;
        private static int operationCounter = 0;

        public void read(Thread thread) {
            int operationId = ++operationCounter;
            String operationName = "read";

            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | lock()");
            lock.lock();
            try {
                while (!isReadable) {
                    System.out.println(
                            getLogPrefix(thread, operationId, operationName) +
                                    " | await() - isReadable == " + isReadable);
                    readCondition.await();
                }

                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | change state affecting isWriteable [" + isWritable + " -> " + !isWritable + "]" +
                                " (also affecting isReadable [" + isReadable + " -> " + !isReadable + "]) boolean");
                isWritable = true;
                isReadable = false;

                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | signal write Condition");
                writeCondition.signal();
            } catch (InterruptedException e) {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | await() exception: " + e);
                throw new RuntimeException(e);
            } finally {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | unlock()");
                lock.unlock();
            }
        }

        public void write(Thread thread) {
            int operationId = ++operationCounter;
            String operationName = "write";

            System.out.println(
                    getLogPrefix(thread, operationId, operationName) +
                            " | lock()");
            lock.lock();
            try {
                while (!isWritable) {
                    System.out.println(
                            getLogPrefix(thread, operationId, operationName) +
                                    " | await() - isWritable == " + isWritable);
                    writeCondition.await();
                }

                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | change state affecting isReadable [" + isReadable + " -> " + !isReadable + "]" +
                                " (also affecting isWritable [" + isWritable + " -> " + !isWritable + "]) boolean");
                isReadable = true;
                isWritable = false;

                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | signal read Condition");
                readCondition.signal();
            } catch (InterruptedException e) {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | await() exception: " + e);
                throw new RuntimeException(e);
            } finally {
                System.out.println(
                        getLogPrefix(thread, operationId, operationName) +
                                " | unlock()");
                lock.unlock();
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
        private static int instanceCounter;
        private final ConditionContainer container;

        ReaderThread(ConditionContainer container) {
            super("reader-" + instanceCounter++);
            this.container = container;
        }

        @Override
        public void run() {
            container.read(this);
        }
    }

    private static class WriterThread extends Thread {
        private static int instanceCounter;
        private final ConditionContainer container;

        WriterThread(ConditionContainer container) {
            super("writer-" + instanceCounter++);
            this.container = container;
        }

        @Override
        public void run() {
            container.write(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Condition.class.getName() + " is called a: " +
                Condition.class.getSimpleName() + ", " +
                Condition.class.getSimpleName() + "Queue, " +
                Condition.class.getSimpleName() + "Variable.");
        readerAwaitsWriterWriteThenReaderReads();
        writerWritesThenAwaitsReaderReadThenWrites();
    }

    private static void readerAwaitsWriterWriteThenReaderReads() throws InterruptedException {
        System.out.println("Reader awaits Writer's write then Reader reads");
        ConditionContainer container = new ConditionContainer();
        ReaderThread reader1 = new ReaderThread(container);
        WriterThread writer1 = new WriterThread(container);

        reader1.start();
        Thread.sleep(100);
        writer1.start();

        reader1.join();
        writer1.join();
    }

    private static void writerWritesThenAwaitsReaderReadThenWrites() throws InterruptedException {
        System.out.println("Writer writes, then Writer awaits Reader's read then Writer writes");
        ConditionContainer container = new ConditionContainer();
        WriterThread writer1 = new WriterThread(container);
        WriterThread writer2 = new WriterThread(container);
        ReaderThread reader1 = new ReaderThread(container);

        writer1.start();
        Thread.sleep(100);
        writer2.start();
        Thread.sleep(100);
        reader1.start();

        writer1.join();
        reader1.join();
        writer2.join();
    }
}
