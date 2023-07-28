package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import org.junit.jupiter.api.*;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple.WaitSleepOrIteration.SLEEP;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple.WaitSleepOrIteration.WAIT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for SimpleReentrantLockDemo")
public class SimpleReentrantLockDemoTest {

    private static final long SLEEP_DURATION_MILLIS = 250;

    private interface AssertionRetryer {
        int MAX_FAILED_ASSERTIONS = 5;

        void testToRun() throws Exception;

        default void runTestRetrying() throws Exception {
            AtomicReference<AssertionError> lastAssertionError = new AtomicReference<>(null);
            AtomicReference<Exception> lastUnexpectedException = new AtomicReference<>(null);

            IntStream.range(0, MAX_FAILED_ASSERTIONS).forEach(failedAssertionCounter -> {
                try {
                    testToRun();
                    lastAssertionError.set(null);
                } catch (AssertionError e) {
                    lastAssertionError.set(e);
                } catch (Exception e) {
                    lastUnexpectedException.set(e);
                }
            });

            if (lastUnexpectedException.get() != null) {
                throw lastUnexpectedException.get();
            }
            if (lastAssertionError.get() != null) {
                throw lastAssertionError.get();
            }
        }
    }

    private interface VariationRunner {
        void testToRun(WaitSleepOrIteration waitSleepOrIteration) throws Exception;

        default void runTestVariations() throws Exception {
            for (WaitSleepOrIteration waitSleepOrIteration : EnumSet.allOf(WaitSleepOrIteration.class)) {
                AssertionRetryer assertionRetryer = () -> testToRun(waitSleepOrIteration);
                assertionRetryer.runTestRetrying();
            }
        }
    }

    @Test
    @Order(1)
    void lockInterruptiblyThenInterrupt_exceptionThrownOnLocking() throws Exception {
        VariationRunner variationRunner = this::lockInterruptiblyThenInterruptExceptionThrownOnLocking;
        variationRunner.runTestVariations();
    }

    private void lockInterruptiblyThenInterruptExceptionThrownOnLocking(WaitSleepOrIteration waitSleepOrIteration) throws InterruptedException {
        boolean isInterruptibly = true;
        boolean willBeInterrupted = true;

        SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
        SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
        SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

        logs.add("start");
        thread.start();

        logs.add("interrupt");
        thread.interrupt();

        if (waitSleepOrIteration == WAIT) {
            SimpleReentrantLockThreadNotifier notifier = new SimpleReentrantLockThreadNotifier(new SimpleReentrantLockSynchronizedLogs(), thread);

            notifier.start();

            notifier.join();
        }

        Thread.sleep(SLEEP_DURATION_MILLIS * 2);

        logs.add("join - start");
        thread.join();
        logs.add("join - finish");
        logs.add("finish");

        InterruptedException expectedException = new InterruptedException();

        assertThat(logs).isEqualTo(SimpleReentrantLockSynchronizedLogs.flatMap(
                getLogsSleepInterruptBeginning(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread, expectedException),

                SimpleReentrantLockSynchronizedLogs.of(SimpleReentrantLockContainer.getLogLock(isInterruptibly, waitSleepOrIteration, null, expectedException, thread.getName(), true)),

                getLogsSleepInterruptEnding(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread, expectedException)));
    }

    @Test
    @Order(2)
    void lockNotInterruptiblyThenInterrupt_exceptionThrown_afterLocking_onWaitSleepOrIteration_thenUnlocks() throws Exception {
        AssertionRetryer assertionRetryer = () -> lockNotInterruptiblyThenInterrupt_exceptionThrown_afterLocking_onWaitSleepOrIteration_thenUnlocks(SLEEP);
        assertionRetryer.runTestRetrying();
    }

    private void lockNotInterruptiblyThenInterrupt_exceptionThrown_afterLocking_onWaitSleepOrIteration_thenUnlocks(WaitSleepOrIteration waitSleepOrIteration) throws InterruptedException {
        boolean isInterruptibly = false;
        boolean willBeInterrupted = true;

        SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
        SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
        SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

        logs.add("start");
        thread.start();

        logs.add("interrupt");
        thread.interrupt();

        Thread.sleep(SLEEP_DURATION_MILLIS * 2);

        logs.add("join - start");
        thread.join();
        logs.add("join - finish");
        logs.add("finish");

        InterruptedException expectedException = new InterruptedException("sleep interrupted");

        assertThat(logs).isEqualTo(SimpleReentrantLockSynchronizedLogs.flatMap(
                getLogsSleepInterruptBeginning(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread, expectedException),

                SimpleReentrantLockSynchronizedLogs.of(
                        SimpleReentrantLockContainer.getLogLock(isInterruptibly, waitSleepOrIteration, FINISH, null, thread.getName(), true),
                        SimpleReentrantLockContainer.getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), true),
                        SimpleReentrantLockContainer.getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, null, expectedException, thread.getName(), true),
                        SimpleReentrantLockContainer.getLogUnLock(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), true),
                        SimpleReentrantLockContainer.getLogUnLock(isInterruptibly, waitSleepOrIteration, FINISH, null, thread.getName(), true)),

                getLogsSleepInterruptEnding(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread, expectedException)));
    }

    private SimpleReentrantLockSynchronizedLogs getLogsSleepInterruptBeginning(
            boolean isInterruptibly,
            boolean willBeInterrupted,
            WaitSleepOrIteration waitSleepOrIteration,
            SimpleReentrantLockThread thread,
            InterruptedException expectedException) {
        return SimpleReentrantLockSynchronizedLogs.of(
                "start",
                "interrupt",
                SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, START, null, thread.getName(), true),
                SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, FINISH, null, thread.getName(), true),
                SimpleReentrantLockThread.getLogLock(isInterruptibly, willBeInterrupted, START, null, thread.getName(), true),
                SimpleReentrantLockContainer.getLogLock(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), true));
    }

    private SimpleReentrantLockSynchronizedLogs getLogsSleepInterruptEnding(
            boolean isInterruptibly,
            boolean willBeInterrupted,
            WaitSleepOrIteration waitSleepOrIteration,
            SimpleReentrantLockThread thread,
            InterruptedException expectedException) {
        return SimpleReentrantLockSynchronizedLogs.of(
                SimpleReentrantLockThread.getLogLock(isInterruptibly, willBeInterrupted, null, expectedException, thread.getName(), false),
                "join - start",
                "join - finish",
                "finish");
    }

    @Test
    @Order(3)
    void sleep_lockInterruptibly_interrupt_withSleepIntervals() throws Exception {
        AssertionRetryer assertionRetryer = () -> {
            boolean isInterruptibly = true;
            boolean willBeInterrupted = true;
            WaitSleepOrIteration waitSleepOrIteration = SLEEP;

            SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
            SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
            SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

            logs.add("start");
            thread.start();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("interrupt");
            thread.interrupt();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("join - start");
            thread.join();
            logs.add("join - finish");
            logs.add("finish");

            InterruptedException expectedException = new InterruptedException("sleep interrupted");
            assertThat(logs).isEqualTo(SimpleReentrantLockSynchronizedLogs.of(
                    "start",
                    SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, START, null, thread.getName(), false),
                    "interrupt",
                    SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, true, null, expectedException, thread.getName(), false),
                    "join - start",
                    "join - finish",
                    "finish"
            ));
            assertThat(logs).isEqualTo(getLogsSleepInterruptWithSleepIntervals(
                    isInterruptibly,
                    willBeInterrupted,
                    waitSleepOrIteration,
                    thread));
        };
        assertionRetryer.runTestRetrying();
    }

    @Test
    @Order(4)
    void sleep_lockNotInterruptibly_interrupt_withSleepIntervals() throws Exception {
        AssertionRetryer assertionRetryer = () -> {
            boolean isInterruptibly = false;
            boolean willBeInterrupted = true;
            WaitSleepOrIteration waitSleepOrIteration = SLEEP;

            SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
            SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
            SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

            logs.add("start");
            thread.start();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("interrupt");
            thread.interrupt();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("join - start");
            thread.join();
            logs.add("join - finish");
            logs.add("finish");

            InterruptedException expectedException = new InterruptedException("sleep interrupted");
            assertThat(logs).isEqualTo(SimpleReentrantLockSynchronizedLogs.of(
                    "start",
                    SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, START, null, thread.getName(), false),
                    "interrupt",
                    SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, true, null, expectedException, thread.getName(), false),
                    "join - start",
                    "join - finish",
                    "finish"
            ));
            assertThat(logs).isEqualTo(getLogsSleepInterruptWithSleepIntervals(
                    isInterruptibly,
                    willBeInterrupted,
                    waitSleepOrIteration,
                    thread));
        };
        assertionRetryer.runTestRetrying();
    }

    private SimpleReentrantLockSynchronizedLogs getLogsSleepInterruptWithSleepIntervals(
            boolean isInterruptibly,
            boolean willBeInterrupted,
            WaitSleepOrIteration waitSleepOrIteration,
            SimpleReentrantLockThread thread) {
        InterruptedException expectedException = new InterruptedException("sleep interrupted");
        return SimpleReentrantLockSynchronizedLogs.of(
                "start",
                SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, willBeInterrupted, START, null, thread.getName(), false),
                "interrupt",
                SimpleReentrantLockThread.getLogSleepUntilIsInterrupted(isInterruptibly, true, null, expectedException, thread.getName(), false),
                "join - start",
                "join - finish",
                "finish");
    }

    @Test
    @Order(5)
    void sleep_lockInterruptibly_doNotInterrupt() throws Exception {
        AssertionRetryer assertionRetryer = () -> {
            boolean isInterruptibly = true;
            boolean willBeInterrupted = false;
            WaitSleepOrIteration waitSleepOrIteration = SLEEP;

            SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
            SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
            SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

            logs.add("start");
            thread.start();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("join - start");
            thread.join();
            logs.add("join - finish");
            logs.add("finish");

            assertThat(logs).isEqualTo(getLogsDoNotInterrupt(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread));
        };
        assertionRetryer.runTestRetrying();
    }

    @Test
    @Order(6)
    void sleep_lockNotInterruptibly_doNotInterrupt() throws Exception {
        AssertionRetryer assertionRetryer = () -> {
            boolean isInterruptibly = false;
            boolean willBeInterrupted = false;
            WaitSleepOrIteration waitSleepOrIteration = SLEEP;

            SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
            SimpleReentrantLockContainer container = new SimpleReentrantLockContainer(logs, waitSleepOrIteration, SLEEP_DURATION_MILLIS);
            SimpleReentrantLockThread thread = new SimpleReentrantLockThread(logs, container, isInterruptibly, willBeInterrupted);

            logs.add("start");
            thread.start();

            Thread.sleep(SLEEP_DURATION_MILLIS * 2);

            logs.add("join - start");
            thread.join();
            logs.add("join - finish");
            logs.add("finish");

            assertThat(logs).isEqualTo(getLogsDoNotInterrupt(isInterruptibly, willBeInterrupted, waitSleepOrIteration, thread));
        };
        assertionRetryer.runTestRetrying();
    }

    private SimpleReentrantLockSynchronizedLogs getLogsDoNotInterrupt(
            boolean isInterruptibly,
            boolean willBeInterrupted,
            WaitSleepOrIteration waitSleepOrIteration,
            SimpleReentrantLockThread thread) {
        return SimpleReentrantLockSynchronizedLogs.of(
                "start",
                SimpleReentrantLockThread.getLogLock(isInterruptibly, willBeInterrupted, START, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogLock(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogLock(isInterruptibly, waitSleepOrIteration, FINISH, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogWaitSleepOrIteration(isInterruptibly, waitSleepOrIteration, FINISH, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogUnLock(isInterruptibly, waitSleepOrIteration, START, null, thread.getName(), false),
                SimpleReentrantLockContainer.getLogUnLock(isInterruptibly, waitSleepOrIteration, FINISH, null, thread.getName(), false),
                SimpleReentrantLockThread.getLogLock(isInterruptibly, willBeInterrupted, FINISH, null, thread.getName(), false),
                "join - start",
                "join - finish",
                "finish");
    }
}
