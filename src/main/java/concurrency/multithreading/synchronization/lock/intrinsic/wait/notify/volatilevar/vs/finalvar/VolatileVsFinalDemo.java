package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.volatilevar.vs.finalvar;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VolatileVsFinalDemo {

    private static class ArrayContainer {
        private final int[] finalArrayCounter = new int[]{0};
    }

    private static class AtomicContainer {
        private final AtomicInteger atomicInteger = new AtomicInteger(0);
    }

    public static void main(String[] args) throws InterruptedException {
        int counter = 0;
        final Thread tCounter = new Thread(() -> {
            // counter++; // must be final or effectively final
        });

        // volatile int volatileCounter = 0; // volatile not allowed here

        testVolatileCounter(true);
        System.out.println();
        testVolatileCounter(false);
        System.out.println();
        testCounter(true);
        System.out.println();
        testCounter(false);
    }

    private volatile static boolean isVolatileCounterIncrementationFinished = false;
    private volatile static int volatileCounter = 0;

    private static class VolatileCounterWriter extends Thread {
        private static int instanceCounter = 0;
        private final int incrementLimit;

        VolatileCounterWriter(int incrementLimit) {
            super(VolatileCounterWriter.class.getSimpleName() + "-" + instanceCounter++);
            this.incrementLimit = incrementLimit;
        }

        @Override
        public void run() {
            IntStream.range(1, incrementLimit + 1)
                    .forEach(newCounterValue -> volatileCounter = newCounterValue);
            isVolatileCounterIncrementationFinished = true;
        }
    }

    public static abstract class AbstractReader extends Thread {
        AbstractReader(String name) {
            super(name);
        }

        public abstract List<Integer> getValuesDetected();

        boolean hadDetectedCounterChangesBeforeWriterWasFinished() {
            return Boolean.TRUE == getHadDetectedCounterChangesBeforeWriterWasFinished();
        }

        public abstract Boolean getHadDetectedCounterChangesBeforeWriterWasFinished();

        boolean hadToBeInterrupted() {
            return Boolean.TRUE == getHadToBeInterrupted();
        }

        public abstract Boolean getHadToBeInterrupted();
    }

    @Getter
    static class VolatileCounterReader extends AbstractReader {
        private static int instanceCounter = 0;
        private final List<Integer> valuesDetected = new LinkedList<>();
        private Boolean hadDetectedCounterChangesBeforeWriterWasFinished = false;
        private final Boolean hadToBeInterrupted = null;
        private final boolean doAssignValueAfterFlagValueChange;

        VolatileCounterReader(boolean doAssignValueAfterFlagValueChange) {
            super(VolatileCounterReader.class.getSimpleName() + "-" + instanceCounter++);
            valuesDetected.add(volatileCounter);
            this.doAssignValueAfterFlagValueChange = doAssignValueAfterFlagValueChange;
        }

        @Override
        public void run() {
            waitOnFinishedLoop();
            finish();
        }

        private void waitOnFinishedLoop() {
            while (!isVolatileCounterIncrementationFinished) {
                assignValue();
            }
            assignValueAfterFlagValueChange();
        }

        private void assignValueAfterFlagValueChange() {
            if (!doAssignValueAfterFlagValueChange) {
                return;
            }
            assignValue();
        }

        private void assignValue() {
            int localVolatileCounter = volatileCounter;
            if (valuesDetected.get(valuesDetected.size() - 1) != localVolatileCounter) {
                valuesDetected.add(localVolatileCounter);
            }
        }

        private void finish() {
            if (!hadDetectedCounterChangesBeforeWriterWasFinished) {
                valuesDetected.remove(0);
            }
        }
    }

    private static void testVolatileCounter(boolean doAssignValueAfterFlagValueChange) throws InterruptedException {
        System.out.println("test volatile counter - doAssignValueAfterFlagValueChange: " +
                doAssignValueAfterFlagValueChange);
        Thread wrappingThread = new Thread(() -> {
            final int readersAmount = 100;
            final VolatileCounterWriter writer = new VolatileCounterWriter(100);
            final List<VolatileCounterReader> readers = IntStream.range(0, readersAmount)
                    .mapToObj(i -> new VolatileCounterReader(doAssignValueAfterFlagValueChange))
                    .collect(Collectors.toList());

            readers.forEach(Thread::start);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            writer.start();

            try {
                writer.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            readers.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            printCounter(volatileCounter, readers);
        }, "wrappingThread");
        wrappingThread.start();
        wrappingThread.join();
    }

    private static int counter = 0;
    private static boolean isCounterIncrementationFinished = false;

    private static class CounterWriter extends Thread {
        private static int instanceCounter = 0;
        private final int incrementLimit;

        CounterWriter(int incrementLimit) {
            super(CounterWriter.class.getSimpleName() + "-" + instanceCounter++);
            this.incrementLimit = incrementLimit;
        }

        @Override
        public void run() {
            IntStream.range(1, incrementLimit + 1)
                    .forEach(newCounterValue -> counter = newCounterValue);
            isCounterIncrementationFinished = true;
        }
    }

    @Getter
    private static class CounterReader extends AbstractReader {
        private static int instanceCounter = 0;

        private final List<Integer> valuesDetected = new LinkedList<>();
        private Boolean hadDetectedCounterChangesBeforeWriterWasFinished = false;
        private Boolean hadToBeInterrupted = false;
        private final boolean doAssignValueAfterFlagValueChange;

        CounterReader(boolean doAssignValueAfterFlagValueChange) {
            super(CounterReader.class.getSimpleName() + "-" + instanceCounter++);
            valuesDetected.add(counter);
            this.doAssignValueAfterFlagValueChange = doAssignValueAfterFlagValueChange;
        }

        @Override
        public void run() {
            waitOnFinishedLoop();
            finish();
        }

        private void waitOnFinishedLoop() {
            while (!isCounterIncrementationFinished) {
                if (hadToBeInterrupted || isInterrupted()) {
                    hadToBeInterrupted = true;
                    finish();
                    return;
                }

                assignValue();
            }
            assignValueAfterFlagValueChange();
        }

        private void assignValueAfterFlagValueChange() {
            if (!doAssignValueAfterFlagValueChange) {
                return;
            }
            assignValue();
        }

        private void assignValue() {
            int localCounter = counter;
            if (valuesDetected.get(valuesDetected.size() - 1) != localCounter) {
                valuesDetected.add(localCounter);
            }
        }

        private void finish() {
            if (!hadDetectedCounterChangesBeforeWriterWasFinished) {
                valuesDetected.remove(0);
            }
        }

        private synchronized void myInterrupt() {
            hadToBeInterrupted = true;
            interrupt();
        }
    }

    private static void testCounter(boolean doAssignValueAfterFlagValueChange) throws InterruptedException {
        System.out.println("test normal counter - doAssignValueAfterFlagValueChange: " +
                doAssignValueAfterFlagValueChange);
        Thread wrappingThread = new Thread(() -> {
            final int readersAmount = 100;
            final CounterWriter writer = new CounterWriter(100);
            final List<CounterReader> readers = IntStream.range(0, readersAmount)
                    .mapToObj(i -> new CounterReader(doAssignValueAfterFlagValueChange))
                    .collect(Collectors.toList());

            readers.forEach(Thread::start);

            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            writer.start();

            try {
                writer.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int[] interruptedCounter = new int[]{0};
            List<Thread> interrupters = readers.stream()
                    .map(reader -> new Thread(() -> {
                        try {
                            Thread.sleep(1L);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (Thread.State.TERMINATED != reader.getState()) {
                            reader.myInterrupt();
                            try {
                                reader.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, "interrupter-" + interruptedCounter[0]++))
                    .collect(Collectors.toList());
            interrupters.forEach(Thread::start);
            interrupters.forEach(interrupter -> {
                try {
                    interrupter.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println(writer.getName() + " - " + writer.getState());
            System.out.println("unterminated interrupters: [" + interrupters.stream()
                    .filter(interrupter -> Thread.State.TERMINATED != interrupter.getState())
                    .map(interrupter -> interrupter.getName() + ": " + interrupter.getState())
                    .collect(Collectors.joining(",")) +
                    "]");
            System.out.println("unterminated readers: [" + readers.stream()
                    .filter(reader -> Thread.State.TERMINATED != reader.getState())
                    .map(reader -> "<" + reader.getName() + "> state: " + reader.getState() + ", isInterrupted: " + reader.isInterrupted())
                    .collect(Collectors.joining(",")) +
                    "]");

            printCounter(counter, readers);
        }, "wrappingThread");
        wrappingThread.start();
        wrappingThread.join();
        System.out.println(wrappingThread.getName() + " - " + wrappingThread.getState());
    }

    private static <E extends AbstractReader> void
    printCounter(int counter, List<E> readers) {
        System.out.println(
                "counter: " +
                        counter +
                        ", \nreaders: [" +
                        readers.stream()
                                .map(reader -> "{" +
                                        "[" +
                                        reader.getValuesDetected().stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(", ")) +
                                        "], " +
                                        reader.hadDetectedCounterChangesBeforeWriterWasFinished() +
                                        ", " +
                                        reader.hadToBeInterrupted() +
                                        "}"
                                ).collect(Collectors.joining(", ")) +
                        "] \n(" +
                        (readers.stream().anyMatch(reader -> !reader.getValuesDetected().isEmpty())
                                ? readers.stream().filter(reader -> !reader.getValuesDetected().isEmpty()).count() +
                                " out of " +
                                readers.size() +
                                " had values detected: [" + readers.stream()
                                .map(AbstractReader::getValuesDetected)
                                .filter(valuesDetected -> !valuesDetected.isEmpty())
                                .map(valuesDetected -> "[" +
                                        valuesDetected.stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(", ")) +
                                        "]")
                                .collect(Collectors.joining(", ")) + "]"
                                : "none had values detected") +
                        ") \n(" +
                        (readers.stream()
                                .anyMatch(AbstractReader::hadDetectedCounterChangesBeforeWriterWasFinished)
                                ? readers.stream().filter(AbstractReader::hadDetectedCounterChangesBeforeWriterWasFinished).count() +
                                " out of " +
                                readers.size() +
                                " were notified before volatile flag changed value: [" +
                                readers.stream()
                                        .filter(AbstractReader::hadDetectedCounterChangesBeforeWriterWasFinished)
                                        .map(reader -> "[" +
                                                reader.getValuesDetected().stream()
                                                        .map(String::valueOf)
                                                        .collect(Collectors.joining(", ")) +
                                                "]")
                                        .collect(Collectors.joining(", ")) + "]"
                                : "none was notified before flag changed value") +
                        ")\n" +
                        "(" +
                        (readers.stream().anyMatch(reader -> reader.hadToBeInterrupted() || reader.isInterrupted())
                                ? readers.stream().filter(AbstractReader::hadToBeInterrupted).count() +
                                " out of " +
                                readers.size() + " had to be interrupted: [" +
                                readers.stream()
                                        .filter(AbstractReader::hadToBeInterrupted)
                                        .map(reader -> reader.getValuesDetected().stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(", ")))
                                        .collect(Collectors.joining(", ")) +
                                "]"
                                : "none had to be interrupted") +
                        ").");
    }
}
