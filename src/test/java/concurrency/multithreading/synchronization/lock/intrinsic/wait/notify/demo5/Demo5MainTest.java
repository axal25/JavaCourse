package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer.Method;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances.SynchronizedInstanceAccessor;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances.SynchronizedInstanceBlockAccessor;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics.StaticReentrantLockAccessor;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics.SynchronizedClassBlockAccessor;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics.SynchronizedStaticAccessor;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics.SynchronizedStaticLockAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.FINISHING;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread.StartingOrFinishing.STARTING;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test for Demonstration #5")
public class Demo5MainTest {

    private static final long SLEEP_DURATION = 250;
    private static final int TEST_REPETITIONS = 100;
    private static final int ASSERTION_RETRIES_LIMIT = 3;

    private interface TestToBeRepeated {
        public void testMethodToBeRepeated() throws Exception;

        public default void runRepeated() {
            IntStream.range(0, TEST_REPETITIONS).forEach(i -> retryAssertions());
        }

        private void retryAssertions() {
            List<AssertionError> assertionErrors = new ArrayList<>();
            while (true) {
                try {
                    runOnce();
                    break;
                } catch (AssertionError e) {
                    assertionErrors.add(e);
                    if (assertionErrors.size() == ASSERTION_RETRIES_LIMIT) {
                        throw new CompoundAssertionError(assertionErrors);
                    }
                }
            }
        }

        private void runOnce() {
            resetLogsAndContainer();
            try {
                testMethodToBeRepeated();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        class CompoundAssertionError extends AssertionError {
            CompoundAssertionError(List<AssertionError> assertionErrors) {
                super(
                        CompoundAssertionError.class.getSimpleName() +
                                " assertion errors: " +
                                assertionErrors,
                        assertionErrors.isEmpty() ? null : assertionErrors.get(0));
            }
        }
    }

    private static LogsContainer logsContainer;
    private static SynchronizedWaitingContainer synchronizedWaitingContainer;

    @BeforeEach
    void setUp() {
        resetLogsAndContainer();
    }

    static void resetLogsAndContainer() {
        logsContainer = new LogsContainer();
        synchronizedWaitingContainer = new SynchronizedWaitingContainer(logsContainer);
    }

    @Test
    public void staticAndInstanceMethodSynchronization_doesNotAffectEachOther() throws InterruptedException {
        SynchronizedStaticAccessor synchronizedStaticAccessor = new SynchronizedStaticAccessor(logsContainer);
        SynchronizedInstanceAccessor synchronizedInstanceAccessor = new SynchronizedInstanceAccessor(synchronizedWaitingContainer, logsContainer);

        synchronizedStaticAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedInstanceAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedStaticNotifier = new AccessorNotifier(synchronizedStaticAccessor, logsContainer);
        synchronizedStaticNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedInstanceNotifier = new AccessorNotifier(synchronizedInstanceAccessor, logsContainer);
        synchronizedInstanceNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedStaticAccessor.join();
        synchronizedStaticNotifier.join();
        synchronizedInstanceAccessor.join();
        synchronizedInstanceNotifier.join();

        assertThat(logsContainer.getLogDequeGeneral())
                .isEqualTo(LogsCopyOnAdd.of(
                        synchronizedStaticAccessor.getLog(STARTING),
                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_STATIC,
                                SynchronizedWaitingContainer.CLASS_NAME,
                                STARTING,
                                synchronizedStaticAccessor),

                        synchronizedInstanceAccessor.getLog(STARTING),
                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_INSTANCE,
                                synchronizedWaitingContainer.getInstanceName(),
                                STARTING,
                                synchronizedInstanceAccessor),

                        synchronizedStaticNotifier.getLog(synchronizedStaticNotifier),

                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_STATIC,
                                SynchronizedWaitingContainer.CLASS_NAME,
                                FINISHING,
                                synchronizedStaticAccessor),
                        synchronizedStaticAccessor.getLog(FINISHING),

                        synchronizedInstanceNotifier.getLog(synchronizedInstanceNotifier),

                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_INSTANCE,
                                synchronizedWaitingContainer.getInstanceName(),
                                FINISHING,
                                synchronizedInstanceAccessor),
                        synchronizedInstanceAccessor.getLog(FINISHING)
                ));
        assertThat(logsContainer.getLogDequeStatic())
                .isEqualTo(LogsCopyOnAdd.of(
                        synchronizedStaticAccessor.getLog(STARTING),
                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_STATIC,
                                SynchronizedWaitingContainer.CLASS_NAME,
                                STARTING,
                                synchronizedStaticAccessor),

                        synchronizedStaticNotifier.getLog(synchronizedStaticNotifier),

                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_STATIC,
                                SynchronizedWaitingContainer.CLASS_NAME,
                                FINISHING,
                                synchronizedStaticAccessor),
                        synchronizedStaticAccessor.getLog(FINISHING)
                ));
        assertThat(logsContainer.getLogDequeInstance())
                .isEqualTo(LogsCopyOnAdd.of(
                        synchronizedInstanceAccessor.getLog(STARTING),
                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_INSTANCE,
                                synchronizedWaitingContainer.getInstanceName(),
                                STARTING,
                                synchronizedInstanceAccessor),

                        synchronizedInstanceNotifier.getLog(synchronizedInstanceNotifier),

                        SynchronizedWaitingContainer.getLog(
                                Method.SYNCHRONIZED_INSTANCE,
                                synchronizedWaitingContainer.getInstanceName(),
                                FINISHING,
                                synchronizedInstanceAccessor),
                        synchronizedInstanceAccessor.getLog(FINISHING)
                ));
    }

    @Test
    public void staticAndInstanceMethodSynchronization_doesNotAffectEachOther_repeated() {
        TestToBeRepeated testToBeRepeated = this::staticAndInstanceMethodSynchronization_doesNotAffectEachOther;
        testToBeRepeated.runRepeated();
    }

    @Test
    public void instanceMethodsSynchronization_doesBlockEachOther() throws InterruptedException {
        SynchronizedInstanceAccessor synchronizedInstanceAccessor = new SynchronizedInstanceAccessor(synchronizedWaitingContainer, logsContainer);
        SynchronizedInstanceBlockAccessor synchronizedInstanceBlockAccessor = new SynchronizedInstanceBlockAccessor(synchronizedWaitingContainer, logsContainer);

        synchronizedInstanceAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedInstanceBlockAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedInstanceNotifier = new AccessorNotifier(synchronizedInstanceAccessor, logsContainer);
        synchronizedInstanceNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedInstanceBlockNotifier = new AccessorNotifier(synchronizedInstanceBlockAccessor, logsContainer);
        synchronizedInstanceBlockNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedInstanceNotifier.join();
        synchronizedInstanceAccessor.join();
        synchronizedInstanceBlockAccessor.join();
        synchronizedInstanceBlockNotifier.join();

        LogsCopyOnAdd expectedLogsCopyOnAdd = LogsCopyOnAdd.of(
                synchronizedInstanceAccessor.getLog(STARTING),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_INSTANCE,
                        synchronizedWaitingContainer.getInstanceName(),
                        STARTING,
                        synchronizedInstanceAccessor),

                synchronizedInstanceBlockAccessor.getLog(STARTING),

                synchronizedInstanceNotifier.getLog(synchronizedInstanceNotifier),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_INSTANCE,
                        synchronizedWaitingContainer.getInstanceName(),
                        FINISHING,
                        synchronizedInstanceAccessor),
                synchronizedInstanceAccessor.getLog(FINISHING),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_INSTANCE_BLOCK,
                        synchronizedWaitingContainer.getInstanceName(),
                        STARTING,
                        synchronizedInstanceBlockAccessor),

                synchronizedInstanceBlockNotifier.getLog(synchronizedInstanceBlockNotifier),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_INSTANCE_BLOCK,
                        synchronizedWaitingContainer.getInstanceName(),
                        FINISHING,
                        synchronizedInstanceBlockAccessor),
                synchronizedInstanceBlockAccessor.getLog(FINISHING)
        );
        assertThat(logsContainer.getLogDequeGeneral()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeInstance()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeStatic()).isEqualTo(LogsCopyOnAdd.of());
    }

    @Test
    public void instanceMethodsSynchronization_doesBlockEachOther_repeated() {
        TestToBeRepeated testToBeRepeated = this::instanceMethodsSynchronization_doesBlockEachOther;
        testToBeRepeated.runRepeated();
    }

    @Test
    public void staticMethodsSynchronization_doesBlockEachOther() throws InterruptedException {
        SynchronizedStaticAccessor synchronizedStaticAccessor = new SynchronizedStaticAccessor(logsContainer);
        SynchronizedClassBlockAccessor synchronizedClassBlockAccessor = new SynchronizedClassBlockAccessor(logsContainer);

        synchronizedStaticAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedClassBlockAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedStaticNotifier = new AccessorNotifier(synchronizedStaticAccessor, logsContainer);
        synchronizedStaticNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedClassBlockNotifier = new AccessorNotifier(synchronizedClassBlockAccessor, logsContainer);
        synchronizedClassBlockNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedStaticNotifier.join();
        synchronizedStaticAccessor.join();
        synchronizedClassBlockAccessor.join();
        synchronizedClassBlockNotifier.join();

        LogsCopyOnAdd expectedLogsCopyOnAdd = LogsCopyOnAdd.of(
                synchronizedStaticAccessor.getLog(STARTING),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        synchronizedStaticAccessor),

                synchronizedClassBlockAccessor.getLog(STARTING),

                synchronizedStaticNotifier.getLog(synchronizedStaticNotifier),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        synchronizedStaticAccessor),
                synchronizedStaticAccessor.getLog(FINISHING),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_CLASS_BLOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        synchronizedClassBlockAccessor),

                synchronizedClassBlockNotifier.getLog(synchronizedClassBlockNotifier),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_CLASS_BLOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        synchronizedClassBlockAccessor),
                synchronizedClassBlockAccessor.getLog(FINISHING)
        );
        assertThat(logsContainer.getLogDequeGeneral()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeStatic()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeInstance()).isEqualTo(LogsCopyOnAdd.of());
    }

    @Test
    public void staticMethodsSynchronization_doesBlockEachOther_repeated() {
        TestToBeRepeated testToBeRepeated = this::staticMethodsSynchronization_doesBlockEachOther;
        testToBeRepeated.runRepeated();
    }

    @Test
    public void staticMethodsSynchronization_doesBlockEachOther_butLocksAreIndependent() throws InterruptedException {
        SynchronizedStaticAccessor synchronizedStaticAccessor = new SynchronizedStaticAccessor(logsContainer);
        SynchronizedClassBlockAccessor synchronizedClassBlockAccessor = new SynchronizedClassBlockAccessor(logsContainer);
        SynchronizedStaticLockAccessor synchronizedStaticLockAccessor = new SynchronizedStaticLockAccessor(logsContainer);
        StaticReentrantLockAccessor staticReentrantLockAccessor = new StaticReentrantLockAccessor(logsContainer);

        synchronizedStaticAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedClassBlockAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedStaticLockAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        staticReentrantLockAccessor.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedStaticNotifier = new AccessorNotifier(synchronizedStaticAccessor, logsContainer);
        synchronizedStaticNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedClassBlockNotifier = new AccessorNotifier(synchronizedClassBlockAccessor, logsContainer);
        synchronizedClassBlockNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier synchronizedStaticLockNotifier = new AccessorNotifier(synchronizedStaticLockAccessor, logsContainer);
        synchronizedStaticLockNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        AccessorNotifier staticReentrantLockNotifier = new AccessorNotifier(staticReentrantLockAccessor, logsContainer);
        staticReentrantLockNotifier.start();

        Thread.sleep(SLEEP_DURATION);

        synchronizedStaticNotifier.join();
        synchronizedStaticAccessor.join();
        synchronizedStaticLockAccessor.join();
        staticReentrantLockAccessor.join();
        synchronizedClassBlockAccessor.join();
        synchronizedClassBlockNotifier.join();
        synchronizedStaticLockNotifier.join();
        staticReentrantLockNotifier.join();

        LogsCopyOnAdd expectedLogsCopyOnAdd = LogsCopyOnAdd.of(
                synchronizedStaticAccessor.getLog(STARTING),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        synchronizedStaticAccessor),

                synchronizedClassBlockAccessor.getLog(STARTING),

                synchronizedStaticLockAccessor.getLog(STARTING),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC_LOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        synchronizedStaticLockAccessor),

                staticReentrantLockAccessor.getLog(STARTING),
                SynchronizedWaitingContainer.getLog(
                        Method.STATIC_REENTRANT_LOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        staticReentrantLockAccessor),

                synchronizedStaticNotifier.getLog(synchronizedStaticNotifier),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        synchronizedStaticAccessor),
                synchronizedStaticAccessor.getLog(FINISHING),

                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_CLASS_BLOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        STARTING,
                        synchronizedClassBlockAccessor),

                synchronizedClassBlockNotifier.getLog(synchronizedClassBlockNotifier),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_CLASS_BLOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        synchronizedClassBlockAccessor),
                synchronizedClassBlockAccessor.getLog(FINISHING),

                synchronizedStaticLockNotifier.getLog(synchronizedStaticLockNotifier),
                SynchronizedWaitingContainer.getLog(
                        Method.SYNCHRONIZED_STATIC_LOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        synchronizedStaticLockAccessor),
                synchronizedStaticLockAccessor.getLog(FINISHING),

                staticReentrantLockNotifier.getLog(staticReentrantLockNotifier),
                SynchronizedWaitingContainer.getLog(
                        Method.STATIC_REENTRANT_LOCK,
                        SynchronizedWaitingContainer.CLASS_NAME,
                        FINISHING,
                        staticReentrantLockAccessor),
                staticReentrantLockAccessor.getLog(FINISHING)
        );
        assertThat(logsContainer.getLogDequeGeneral()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeStatic()).isEqualTo(expectedLogsCopyOnAdd);
        assertThat(logsContainer.getLogDequeInstance()).isEqualTo(LogsCopyOnAdd.of());
    }

    @Test
    public void staticMethodsSynchronization_doesBlockEachOther_butLocksAreIndependent_repeated() {
        TestToBeRepeated testToBeRepeated = this::staticMethodsSynchronization_doesBlockEachOther_butLocksAreIndependent;
        testToBeRepeated.runRepeated();
    }
}
