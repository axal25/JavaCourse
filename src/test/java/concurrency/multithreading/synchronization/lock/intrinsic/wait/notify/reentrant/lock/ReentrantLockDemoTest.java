package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import com.google.common.truth.Correspondence;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;

public class ReentrantLockDemoTest {

    private static final Correspondence<Throwable, Throwable> CORRESPONDENCE_THROWABLE_IGNORE_STACK_TRACE = Correspondence.from(
            ReentrantLockDemoTest::equalsIgnoreStackTrace,
            String.format(
                    "actual %s equals expected %s",
                    Throwable.class.getSimpleName(),
                    Throwable.class.getSimpleName()));

    private static boolean equalsIgnoreStackTrace(Throwable throwable1, Throwable throwable2) {
        return throwable1 == throwable2
                ? true
                : throwable1 == null || throwable2 == null
                ? false
                : Objects.equals(throwable1.getClass(), throwable2.getClass())
                && Objects.equals(throwable1.toString(), throwable2.toString())
                && Objects.equals(throwable1.getMessage(), throwable2.getMessage())
                && Objects.equals(throwable1.getLocalizedMessage(), throwable2.getLocalizedMessage())
                && Arrays.equals(throwable1.getSuppressed(), throwable2.getSuppressed())
                && equalsIgnoreStackTrace(throwable1.getCause(), throwable2.getCause());
    }

    static final long SLEEP_DURATION_MILLIS = 250;

    @TestTemplate
    @ExtendWith(TestContextProviderWaitInterruptBeforeLocking.class)
    void interruptBeforeLockingWait(ReentrantLockDemoTestCaseData testCaseData) throws InterruptedException {
        testCaseData.getInputs().getReentrantLockAccessor().start();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockAccessor().interrupt();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockNotifier().notifyAccessor();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockNotifier().notifyAccessor();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockAccessor().join();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(testCaseData.getInputs().getLogs())
                .isEqualTo(testCaseData.getExpecteds().getLogs());

        ReentrantLockAccessor reentrantLockAccessor = testCaseData.getInputs().getReentrantLockAccessor();
        List<Throwable> actualUncaughtExceptions = reentrantLockAccessor.getReentrantLockThreadGroup().getUncaughtExceptions().get(reentrantLockAccessor);
        assertThat(reentrantLockAccessor.getUncaughtExceptions()).isEqualTo(actualUncaughtExceptions);

        List<Throwable> expectedUncaughtExceptions = testCaseData.getExpecteds().getUncaughtExceptions();

        assertThat(actualUncaughtExceptions).hasSize(expectedUncaughtExceptions.size());
        IntStream.range(0, actualUncaughtExceptions.size()).forEach(index -> {
            Throwable actualUncaughtException = actualUncaughtExceptions.get(index);
            Throwable expectedUncaughtException = expectedUncaughtExceptions.get(index);
            assertThat(actualUncaughtException.getClass()).isEqualTo(expectedUncaughtException.getClass());
            assertThat(actualUncaughtException).hasMessageThat().isEqualTo(expectedUncaughtException.getMessage());
            assertThat(actualUncaughtException.getCause().getClass()).isEqualTo(expectedUncaughtException.getCause().getClass());

            assertThat(actualUncaughtException).hasCauseThat().hasMessageThat().isNull();
            assertThat(expectedUncaughtException).hasCauseThat().hasMessageThat().isNull();

            assertThat(actualUncaughtException.getCause().getCause()).isNull();
            assertThat(expectedUncaughtException.getCause().getCause()).isNull();
        });
        assertThat(actualUncaughtExceptions)
                .comparingElementsUsing(CORRESPONDENCE_THROWABLE_IGNORE_STACK_TRACE)
                .containsExactlyElementsIn(expectedUncaughtExceptions);
    }

    @TestTemplate
    @ExtendWith(TestContextProviderWaitInterruptAfterLocking.class)
    void interruptAfterLockingWait(ReentrantLockDemoTestCaseData testCaseData) throws InterruptedException {
        testCaseData.getInputs().getReentrantLockAccessor().start();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockNotifier().notifyAccessor();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockAccessor().interrupt();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockNotifier().notifyAccessor();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockAccessor().join();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(testCaseData.getInputs().getLogs()).isEqualTo(testCaseData.getExpecteds().getLogs());


        ReentrantLockAccessor reentrantLockAccessor = testCaseData.getInputs().getReentrantLockAccessor();
        List<Throwable> actualUncaughtExceptions = reentrantLockAccessor.getReentrantLockThreadGroup().getUncaughtExceptions().get(reentrantLockAccessor);
        assertThat(reentrantLockAccessor.getUncaughtExceptions()).isEqualTo(actualUncaughtExceptions);

        List<Throwable> expectedUncaughtExceptions = testCaseData.getExpecteds().getUncaughtExceptions();

        assertThat(actualUncaughtExceptions).hasSize(expectedUncaughtExceptions.size());
        IntStream.range(0, actualUncaughtExceptions.size()).forEach(index -> {
            Throwable actualUncaughtException = actualUncaughtExceptions.get(index);
            Throwable expectedUncaughtException = expectedUncaughtExceptions.get(index);
            assertThat(actualUncaughtException.getClass()).isEqualTo(expectedUncaughtException.getClass());
            assertThat(actualUncaughtException).hasMessageThat().isEqualTo(expectedUncaughtException.getMessage());
            assertThat(actualUncaughtException.getCause().getClass()).isEqualTo(expectedUncaughtException.getCause().getClass());

            assertThat(actualUncaughtException).hasCauseThat().hasMessageThat().isNull();
            assertThat(expectedUncaughtException).hasCauseThat().hasMessageThat().isNull();

            assertThat(actualUncaughtException.getCause().getCause()).isNull();
            assertThat(expectedUncaughtException.getCause().getCause()).isNull();
        });
        assertThat(actualUncaughtExceptions)
                .comparingElementsUsing(CORRESPONDENCE_THROWABLE_IGNORE_STACK_TRACE)
                .containsExactlyElementsIn(expectedUncaughtExceptions);
    }

    @TestTemplate
    @ExtendWith(TestContextProviderSleepInterruptBeforeLocking.class)
    void interruptBeforeLockingSleep(ReentrantLockDemoTestCaseData testCaseData) throws InterruptedException {
        testCaseData.getInputs().getReentrantLockAccessor().start();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        testCaseData.getInputs().getReentrantLockAccessor().interrupt();
        Thread.sleep(SLEEP_DURATION_MILLIS * 3);

        testCaseData.getInputs().getReentrantLockAccessor().join();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(testCaseData.getInputs().getLogs())
                .isEqualTo(testCaseData.getExpecteds().getLogs());

        ReentrantLockAccessor reentrantLockAccessor = testCaseData.getInputs().getReentrantLockAccessor();
        List<Throwable> actualUncaughtExceptions = reentrantLockAccessor.getReentrantLockThreadGroup().getUncaughtExceptions().get(reentrantLockAccessor);
        assertThat(reentrantLockAccessor.getUncaughtExceptions()).isEqualTo(actualUncaughtExceptions);

        List<Throwable> expectedUncaughtExceptions = testCaseData.getExpecteds().getUncaughtExceptions();

        assertThat(actualUncaughtExceptions).hasSize(expectedUncaughtExceptions.size());
        IntStream.range(0, actualUncaughtExceptions.size()).forEach(index -> {
            Throwable actualUncaughtException = actualUncaughtExceptions.get(index);
            Throwable expectedUncaughtException = expectedUncaughtExceptions.get(index);
            assertThat(actualUncaughtException.getClass()).isEqualTo(expectedUncaughtException.getClass());
            assertThat(actualUncaughtException).hasMessageThat().isEqualTo(expectedUncaughtException.getMessage());
            assertThat(actualUncaughtException.getCause().getClass()).isEqualTo(expectedUncaughtException.getCause().getClass());

            assertThat(actualUncaughtException).hasCauseThat().hasMessageThat().isNull();
            assertThat(expectedUncaughtException).hasCauseThat().hasMessageThat().isNull();

            assertThat(actualUncaughtException.getCause().getCause()).isNull();
            assertThat(expectedUncaughtException.getCause().getCause()).isNull();
        });
        assertThat(actualUncaughtExceptions)
                .comparingElementsUsing(CORRESPONDENCE_THROWABLE_IGNORE_STACK_TRACE)
                .containsExactlyElementsIn(expectedUncaughtExceptions);
    }


    @TestTemplate
    @ExtendWith(TestContextProviderSleepInterruptAfterLocking.class)
    void interruptAfterLockingSleep(ReentrantLockDemoTestCaseData testCaseData) throws InterruptedException {
        testCaseData.getInputs().getReentrantLockAccessor().start();
        Thread.sleep(SLEEP_DURATION_MILLIS * 2);

        testCaseData.getInputs().getReentrantLockAccessor().interrupt();
        Thread.sleep(SLEEP_DURATION_MILLIS * 2);

        testCaseData.getInputs().getReentrantLockAccessor().join();
        Thread.sleep(SLEEP_DURATION_MILLIS * 2);

        assertThat(testCaseData.getInputs().getLogs()).isEqualTo(testCaseData.getExpecteds().getLogs());

        ReentrantLockAccessor reentrantLockAccessor = testCaseData.getInputs().getReentrantLockAccessor();
        List<Throwable> actualUncaughtExceptions = reentrantLockAccessor.getReentrantLockThreadGroup().getUncaughtExceptions().get(reentrantLockAccessor);
        assertThat(reentrantLockAccessor.getUncaughtExceptions()).isEqualTo(actualUncaughtExceptions);

        List<Throwable> expectedUncaughtExceptions = testCaseData.getExpecteds().getUncaughtExceptions();

        assertThat(actualUncaughtExceptions).hasSize(expectedUncaughtExceptions.size());
        IntStream.range(0, actualUncaughtExceptions.size()).forEach(index -> {
            Throwable actualUncaughtException = actualUncaughtExceptions.get(index);
            Throwable expectedUncaughtException = expectedUncaughtExceptions.get(index);
            assertThat(actualUncaughtException.getClass()).isEqualTo(expectedUncaughtException.getClass());
            assertThat(actualUncaughtException).hasMessageThat().isEqualTo(expectedUncaughtException.getMessage());
            assertThat(actualUncaughtException.getCause().getClass()).isEqualTo(expectedUncaughtException.getCause().getClass());

            assertThat(actualUncaughtException).hasCauseThat().hasMessageThat().isNull();
            assertThat(expectedUncaughtException).hasCauseThat().hasMessageThat().isNull();

            assertThat(actualUncaughtException.getCause().getCause()).isNull();
            assertThat(expectedUncaughtException.getCause().getCause()).isNull();
        });
        assertThat(actualUncaughtExceptions)
                .comparingElementsUsing(CORRESPONDENCE_THROWABLE_IGNORE_STACK_TRACE)
                .containsExactlyElementsIn(expectedUncaughtExceptions);
    }

    @Test
    void reentrantLockAccessor_propertiesOverLifeCycle() throws InterruptedException {
        boolean isInterruptibly = true;
        LogsCopyOnAdd logs = new LogsCopyOnAdd();
        ReentrantLockContainer reentrantLockContainer = new ReentrantLockWaitContainer(logs);
        ReentrantLockAccessor reentrantLockAccessor = new ReentrantLockWaitAccessor(logs, reentrantLockContainer, isInterruptibly);

        assertThat(reentrantLockAccessor.getThreadGroup()).isNotNull();
        assertThat(reentrantLockAccessor.getUncaughtExceptionHandler()).isNotNull();
        assertThat(reentrantLockAccessor.getReentrantLockThreadGroup()).isNotNull();

        assertThat(reentrantLockAccessor.isInterrupted()).isFalse();
        assertThat(reentrantLockAccessor.getState()).isEqualTo(Thread.State.NEW);

        reentrantLockAccessor.start();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(reentrantLockAccessor.getThreadGroup()).isNotNull();
        assertThat(reentrantLockAccessor.getUncaughtExceptionHandler()).isNotNull();
        assertThat(reentrantLockAccessor.getReentrantLockThreadGroup()).isNotNull();

        assertThat(reentrantLockAccessor.isInterrupted()).isFalse();
        assertThat(reentrantLockAccessor.getState()).isEqualTo(Thread.State.WAITING);

        reentrantLockAccessor.interrupt();

        assertThat(reentrantLockAccessor.isInterrupted()).isTrue();

        assertThat(reentrantLockAccessor.getState()).isEqualTo(Thread.State.WAITING);

        assertThat(reentrantLockAccessor.getThreadGroup()).isNotNull();
        assertThat(reentrantLockAccessor.getUncaughtExceptionHandler()).isNotNull();
        assertThat(reentrantLockAccessor.getReentrantLockThreadGroup()).isNotNull();

        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(reentrantLockAccessor.getThreadGroup()).isNull();
        assertThat(reentrantLockAccessor.getUncaughtExceptionHandler()).isNull();
        assertThat(reentrantLockAccessor.getReentrantLockThreadGroup()).isNotNull();

        assertThat(reentrantLockAccessor.isInterrupted()).isFalse();
        assertThat(reentrantLockAccessor.getState()).isEqualTo(Thread.State.TERMINATED);

        reentrantLockAccessor.join();
        Thread.sleep(SLEEP_DURATION_MILLIS);

        assertThat(reentrantLockAccessor.getThreadGroup()).isNull();
        assertThat(reentrantLockAccessor.getUncaughtExceptionHandler()).isNull();
        assertThat(reentrantLockAccessor.getReentrantLockThreadGroup()).isNotNull();

        assertThat(reentrantLockAccessor.isInterrupted()).isFalse();
        assertThat(reentrantLockAccessor.getState()).isEqualTo(Thread.State.TERMINATED);
    }
}
