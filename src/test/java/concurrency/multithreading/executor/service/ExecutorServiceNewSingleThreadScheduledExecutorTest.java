package concurrency.multithreading.executor.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.truth.Truth.assertThat;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorServiceNewSingleThreadScheduledExecutorTest {

    @Test
    void schedule_executesThreadsOnceAfterDelay() {
        try (ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()) {
            long sleepDuration = 150L;
            AtomicReference<String> runnableOutputRef = new AtomicReference<>();
            String runnableOutputInput = "runnableOutput";
            Runnable runnable = () -> {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runnableOutputRef.set(runnableOutputInput);
            };
            AtomicReference<String> callableOutputRef = new AtomicReference<>();
            String callableOutputInput = "callableOutput";
            Callable<String> callable = () -> {
                Thread.sleep(sleepDuration);
                callableOutputRef.set(callableOutputInput);
                return callableOutputInput;
            };
            AtomicReference<String> futureTaskOutputRef = new AtomicReference<>();
            String futureTaskOutputInput = "callableOutput";
            FutureTask<String> futureTask = new FutureTask<>(() -> {
                Thread.sleep(sleepDuration);
                futureTaskOutputRef.set(futureTaskOutputInput);
                return futureTaskOutputInput;
            });
            long executionDelayDuration = 350L;
            TimeUnit executionDelayTimeUnit = MILLISECONDS;
            scheduledExecutorService.schedule(runnable, executionDelayDuration, executionDelayTimeUnit);
            scheduledExecutorService.schedule(callable, executionDelayDuration, executionDelayTimeUnit);
            scheduledExecutorService.schedule(futureTask, executionDelayDuration, executionDelayTimeUnit);

            assertThat(runnableOutputRef.get()).isNull();
            assertThat(callableOutputRef.get()).isNull();
            assertThat(futureTaskOutputRef.get()).isNull();
            assertThat(futureTask.isDone()).isFalse();

            assertDoesNotThrow(() -> Thread.sleep(executionDelayDuration + 3 * sleepDuration));
            assertDoesNotThrow(() -> assertThat(futureTask.get()).isEqualTo(futureTaskOutputInput));
            assertThat(futureTask.isDone()).isTrue();

            assertThat(runnableOutputRef.get()).isEqualTo(runnableOutputInput);
            assertThat(callableOutputRef.get()).isEqualTo(callableOutputInput);
            assertThat(futureTaskOutputRef.get()).isEqualTo(futureTaskOutputInput);
            assertThat(futureTask.resultNow()).isEqualTo(futureTaskOutputInput);
        }
    }

    @Test
    void scheduleAtFixedRate_runnable_executesThreadMultipleTimeWithDelay() {
        try (ScheduledExecutorService mainRunnableSes = Executors.newSingleThreadScheduledExecutor();
             ScheduledExecutorService cancellationSes = Executors.newSingleThreadScheduledExecutor()) {
            List<String> callableStartLogs = Collections.synchronizedList(new ArrayList<>());
            List<String> callableFinishLogs = Collections.synchronizedList(new ArrayList<>());
            AtomicInteger counterRef = new AtomicInteger(0);
            long sleepDuration = 250L;
            Runnable runnable = () -> {
                int counter = counterRef.get();
                counterRef.set(counter + 1);

                callableStartLogs.add("callableStartLog_" + counter);

                assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

                callableFinishLogs.add("callableFinishLog_" + counter);
            };
            long delayDuration = 450L;
            long periodDuration = 350L;
            int executionAmount = 2;
            long timingAdjustment = -150L;
            long cancelExecutionAfter = delayDuration + executionAmount * periodDuration + timingAdjustment;

            Future<?> future =
                    mainRunnableSes.scheduleAtFixedRate(runnable, delayDuration, periodDuration, MILLISECONDS);
            cancellationSes.schedule(() -> future.cancel(false), cancelExecutionAfter, MILLISECONDS);

            assertThat(callableStartLogs).isEmpty();
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(delayDuration + timingAdjustment));

            while (callableStartLogs.size() < 1) ;
            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            while (callableFinishLogs.size() < 1) ;
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(periodDuration - sleepDuration));

            while (callableStartLogs.size() < 2) ;
            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            while (callableFinishLogs.size() < 2) ;
            assertThat(callableFinishLogs).isEqualTo(List.of("callableFinishLog_0", "callableFinishLog_1"));

            assertDoesNotThrow(() -> Thread.sleep(3 * periodDuration));

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            assertThat(callableFinishLogs).isEqualTo(List.of("callableFinishLog_0", "callableFinishLog_1"));
        }
    }

    @Test
    void scheduleAtFixedRate_futureTask_cannotBeRunMultipleTimes_runReturnsCachedValue() {
        try (ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()) {
            List<String> callableStartLogs = Collections.synchronizedList(new ArrayList<>());
            List<String> callableFinishLogs = Collections.synchronizedList(new ArrayList<>());
            AtomicInteger counterRef = new AtomicInteger(0);
            long sleepDuration = 100L;
            Callable<String> callable = () -> {
                int counter = counterRef.get();
                counterRef.set(counter + 1);

                callableStartLogs.add("callableStartLog_" + counter);

                Thread.sleep(sleepDuration);

                callableFinishLogs.add("callableFinishLog_" + counter);

                return "callableFinishLog_" + counter;
            };
            // FutureTask's callable can called only once!
            // Future's run returns caches value of already executed callable.
            FutureTask<String> futureTask = new FutureTask<>(callable);
            long delayDuration = 450L;
            long periodDuration = 350L;
            long timingAdjustment = -150L;

            assertDoesNotThrow(() -> Thread.sleep(2 * periodDuration));
            assertThat(futureTask.isDone()).isFalse();

            Future<?> future =
                    scheduledExecutorService.scheduleAtFixedRate(futureTask, delayDuration, periodDuration, MILLISECONDS);
            assertThat(futureTask).isNotEqualTo(future);

            assertThat(futureTask.isDone()).isFalse();
            assertThat(callableStartLogs).isEmpty();
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(delayDuration + timingAdjustment));

            assertThat(futureTask.isDone()).isFalse();
            assertThat(future.isDone()).isFalse();
            while (callableStartLogs.size() < 1) ;
            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

            assertDoesNotThrow(() -> assertThat(futureTask.get()).isEqualTo("callableFinishLog_0"));
            assertDoesNotThrow(() -> assertThat(futureTask.resultNow()).isEqualTo("callableFinishLog_0"));
            assertThat(futureTask.isDone()).isTrue();
            assertThat(future.isDone()).isFalse();

            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(3 * periodDuration));

            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> assertThat(futureTask.resultNow()).isEqualTo("callableFinishLog_0"));
            assertThat(futureTask.isDone()).isTrue();
            assertThat(future.isDone()).isFalse();
        }
    }

    @Test
    void scheduleAtFixedRate_futureTask_executesThreadMultipleTimeWithDelay() {
        try (ScheduledExecutorService mainRunnableSes = Executors.newSingleThreadScheduledExecutor();
             ScheduledExecutorService cancellationSes = Executors.newSingleThreadScheduledExecutor()) {
            List<String> callableStartLogs = Collections.synchronizedList(new ArrayList<>());
            List<String> callableFinishLogs = Collections.synchronizedList(new ArrayList<>());
            AtomicInteger counterRef = new AtomicInteger(0);
            long sleepDuration = 250L;
            Callable<String> callable = () -> {
                int counter = counterRef.get();
                counterRef.set(counter + 1);

                callableStartLogs.add("callableStartLog_" + counter);

                Thread.sleep(sleepDuration);

                callableFinishLogs.add("callableFinishLog_" + counter);

                return "callableFinishLog_" + counter;
            };
            // FutureTask's callable can called only once!
            // Future's run returns caches value of already executed callable.
            AtomicReference<FutureTask<String>> futureTaskRef = new AtomicReference<>();
            Runnable runnable = () -> {
                FutureTask<String> futureTask = new FutureTask<>(callable);
                futureTaskRef.set(futureTask);
                futureTask.run();
                assertDoesNotThrow(() -> assertThat(futureTask.get()).isNotNull());
            };
            long delayDuration = 450L;
            long periodDuration = 350L;
            int executionAmount = 2;
            long timingAdjustment = -150L;
            long cancelExecutionAfter = delayDuration + executionAmount * periodDuration + timingAdjustment;

            Future<?> future =
                    mainRunnableSes.scheduleAtFixedRate(runnable, delayDuration, periodDuration, MILLISECONDS);
            cancellationSes.schedule(() -> future.cancel(false), cancelExecutionAfter, MILLISECONDS);

            assertThat(futureTaskRef.get()).isNull();
            assertThat(callableStartLogs).isEmpty();
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(delayDuration + timingAdjustment));
            while (callableStartLogs.size() < 1) ;

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0"));
            assertThat(callableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));
            while (callableFinishLogs.size() < 1) ;

            assertThat(callableStartLogs).containsExactly("callableStartLog_0");
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(periodDuration - sleepDuration));
            while (callableStartLogs.size() < 2) ;

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            assertThat(callableFinishLogs).containsExactly("callableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));
            while (callableFinishLogs.size() < 2) ;

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            assertThat(callableFinishLogs).isEqualTo(List.of("callableFinishLog_0", "callableFinishLog_1"));

            assertDoesNotThrow(() -> Thread.sleep(3 * periodDuration));

            assertThat(callableStartLogs).isEqualTo(List.of("callableStartLog_0", "callableStartLog_1"));
            assertThat(callableFinishLogs).isEqualTo(List.of("callableFinishLog_0", "callableFinishLog_1"));
        }
    }

    @Test
    void scheduleAtFixedRate_runnable_executionTimeIsLongerThenPeriod_launchesNextThreadImmediatelyAfterFinishingPrevious() {
        try (ScheduledExecutorService mainRunnableSes = Executors.newSingleThreadScheduledExecutor();
             ScheduledExecutorService cancellationSes = Executors.newSingleThreadScheduledExecutor()) {
            List<String> runnableStartLogs = Collections.synchronizedList(new ArrayList<>());
            List<String> runnableFinishLogs = Collections.synchronizedList(new ArrayList<>());
            long sleepDuration = 450L;
            AtomicInteger counterAtomic = new AtomicInteger(0);
            Runnable runnable = () -> {
                int counter = counterAtomic.getAndIncrement();
                runnableStartLogs.add("runnableStartLog_" + counter);
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runnableFinishLogs.add("runnableFinishLog_" + counter);
            };
            long delayDuration = 350L;
            long periodDuration = 250L;
            TimeUnit timeUnit = MILLISECONDS;
            int executionAmount = 2;
            long timingAdjustment = -150L;
            long cancelExecutionAfter = executionAmount * sleepDuration + delayDuration + timingAdjustment;
            long nanosExponent = Math.floorDiv((long) Math.pow(10, 6), 1L);
            long timeStart = System.nanoTime();
            Future<?> future = mainRunnableSes.scheduleAtFixedRate(runnable, delayDuration, periodDuration, timeUnit);
            cancellationSes.schedule(() -> future.cancel(false), cancelExecutionAfter, timeUnit);

            assertThat(runnableStartLogs).isEmpty();
            assertThat(runnableFinishLogs).isEmpty();

            final AtomicLong millisSinceExecution = new AtomicLong();
            millisSinceExecution.set((System.nanoTime() - timeStart) / nanosExponent);
            assertDoesNotThrow(() -> Thread.sleep(delayDuration - millisSinceExecution.get()));
            while (runnableStartLogs.size() < 1) ;

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0"));
            assertThat(runnableFinishLogs).isEmpty();

            millisSinceExecution.set((System.nanoTime() - timeStart) / nanosExponent);
            assertDoesNotThrow(() -> Thread.sleep(delayDuration + sleepDuration - millisSinceExecution.get()));
            while (runnableFinishLogs.size() < 1) ;

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).isEqualTo(List.of("runnableFinishLog_0"));

            IllegalArgumentException sleepException = assertThrows(IllegalArgumentException.class, () ->
                    Thread.sleep(delayDuration + periodDuration - sleepDuration - millisSinceExecution.get()));
            assertThat(sleepException).hasCauseThat().isNull();
            assertThat(sleepException).hasMessageThat().isEqualTo("timeout value is negative");

            millisSinceExecution.set((System.nanoTime() - timeStart) / nanosExponent);
            assertDoesNotThrow(() -> Thread.sleep(delayDuration + sleepDuration + sleepDuration - millisSinceExecution.get()));
            while (runnableFinishLogs.size() < 2) ;

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).isEqualTo(List.of("runnableFinishLog_0", "runnableFinishLog_1"));

            assertDoesNotThrow(() -> Thread.sleep(3 * (periodDuration + sleepDuration)));

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).isEqualTo(List.of("runnableFinishLog_0", "runnableFinishLog_1"));
        }
    }

    @Test
    void scheduleWithFixedDelay_runnable_executionTimeIsLongerThenPeriod_launchesNextThreadWithFixedPeriod() {
        try (ScheduledExecutorService runnableSes = Executors.newSingleThreadScheduledExecutor();
             ScheduledExecutorService cancelSes = Executors.newSingleThreadScheduledExecutor()) {
            List<String> runnableStartLogs = Collections.synchronizedList(new ArrayList<>());
            List<String> runnableFinishLogs = Collections.synchronizedList(new ArrayList<>());

            long sleepDuration = 450L;
            AtomicInteger counterAtomic = new AtomicInteger(0);
            Runnable runnable = () -> {
                int counter = counterAtomic.getAndIncrement();
                runnableStartLogs.add("runnableStartLog_" + counter);
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runnableFinishLogs.add("runnableFinishLog_" + counter);
            };
            long delayDuration = 350L;
            long periodAfterFinishDuration = 250L;
            TimeUnit timeUnit = MILLISECONDS;
            int executionAmount = 2;
            long cancelExecutionAfter = executionAmount * sleepDuration + delayDuration;

            Future<?> future =
                    runnableSes.scheduleWithFixedDelay(runnable, delayDuration, periodAfterFinishDuration, timeUnit);
            cancelSes.schedule(() -> future.cancel(false), cancelExecutionAfter, timeUnit);

            assertThat(runnableStartLogs).isEmpty();
            assertThat(runnableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(delayDuration));

            assertThat(runnableStartLogs).containsExactly("runnableStartLog_0");
            assertThat(runnableFinishLogs).isEmpty();

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

            assertThat(runnableStartLogs).containsExactly("runnableStartLog_0");
            assertThat(runnableFinishLogs).containsExactly("runnableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(periodAfterFinishDuration));

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).containsExactly("runnableFinishLog_0");

            assertDoesNotThrow(() -> Thread.sleep(sleepDuration));

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).isEqualTo(List.of("runnableFinishLog_0", "runnableFinishLog_1"));

            assertDoesNotThrow(() -> Thread.sleep(2 * (periodAfterFinishDuration + sleepDuration)));

            assertThat(runnableStartLogs).isEqualTo(List.of("runnableStartLog_0", "runnableStartLog_1"));
            assertThat(runnableFinishLogs).isEqualTo(List.of("runnableFinishLog_0", "runnableFinishLog_1"));
        }
    }
}
