package concurrency.multithreading.executor.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorServiceNewFixedThreadPoolTest {

    @Test
    void submit() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return inputOutput;
            };
            Future<String> future = executorService.submit(callable);
            TimeoutException getWithTimeoutException =
                    assertThrows(TimeoutException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
            assertThat(getWithTimeoutException).hasCauseThat().isNull();
            assertThat(getWithTimeoutException).hasMessageThat().isNull();
            assertDoesNotThrow(() -> assertThat(future.get()).isEqualTo(inputOutput));
        }
    }

    @Test
    void execute_runnable_noWayToSynchronize() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            AtomicReference<String> refOutput = new AtomicReference<>();
            Runnable runnable = () -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                refOutput.set(inputOutput);
            };

            executorService.execute(runnable);

            // No way to synchronize on .execute() method
            // Option 1: assertDoesNotThrow(() -> Thread.sleep(100));
            // Option 2:
            Future<String> future = executorService.submit(() -> "");
            assertDoesNotThrow(() -> future.get());

            assertThat(refOutput.get()).isEqualTo(inputOutput);

            assertThat(executorService.isTerminated()).isFalse();
            assertThat(executorService.isShutdown()).isFalse();
        }
    }

    @Test
    void execute_forceShutDown_interruptedSleep() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            AtomicReference<String> refOutput = new AtomicReference<>();
            Callable<String> callable = () -> {
                Thread.sleep(100);
                refOutput.set("output");
                return "callableOutput";
            };
            FutureTask<String> futureTask = new FutureTask<>(callable);
            Runnable runnable = futureTask;
            executorService.execute(runnable);

            executorService.shutdown();
            assertThat(executorService.isShutdown()).isTrue();
            AtomicReference<Boolean> wasTerminated = new AtomicReference<>();
            wasTerminated.set(null);
            assertDoesNotThrow(() -> wasTerminated.set(executorService.awaitTermination(10, TimeUnit.MILLISECONDS)));
            assertThat(wasTerminated.get()).isFalse();
            assertThat(executorService.isTerminated()).isFalse();
            assertThat(executorService.shutdownNow()).isEmpty();

            assertThat(refOutput.get()).isNull();

            ExecutionException futureTaskGetExecution = assertThrows(ExecutionException.class, futureTask::get);
            assertThat(futureTask.exceptionNow()).isInstanceOf(InterruptedException.class);
            assertThat(futureTask.exceptionNow()).hasMessageThat().isEqualTo("sleep interrupted");
            assertThat(futureTaskGetExecution).hasCauseThat().isEqualTo(futureTask.exceptionNow());

            while (!executorService.isTerminated()) ;
            assertThat(executorService.isTerminated()).isTrue();
        }
    }

    @Test
    void execute_forceShutDown_notInterruptable() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            String inputCallableOutput = "callableOutput";
            AtomicReference<String> refOutput = new AtomicReference<>();
            Callable<String> callable = () -> {
                for (long i = 0L; i < 1_000_000_000L; i++) ;
                refOutput.set(inputOutput);
                return inputCallableOutput;
            };
            FutureTask<String> futureTask = new FutureTask<>(callable);
            Runnable runnable = futureTask;
            executorService.execute(runnable);

            executorService.shutdown();
            assertThat(executorService.isShutdown()).isTrue();
            AtomicReference<Boolean> wasTerminated = new AtomicReference<>();
            wasTerminated.set(null);
            assertDoesNotThrow(() -> wasTerminated.set(executorService.awaitTermination(10, TimeUnit.MILLISECONDS)));
            assertThat(wasTerminated.get()).isFalse();
            assertThat(executorService.shutdownNow()).isEmpty();

            // sometimes it finishes, sometimes it does not
            if (executorService.isTerminated()) {
                assertThat(refOutput.get()).isEqualTo(inputOutput);
            } else {
                assertThat(refOutput.get()).isNull();
            }

            assertDoesNotThrow(() -> assertThat(futureTask.get()).isEqualTo(inputCallableOutput));
            assertThat(refOutput.get()).isEqualTo(inputOutput);

            while (!executorService.isTerminated()) ;
            assertThat(executorService.isTerminated()).isTrue();
        }
    }

    @Test
    void invokeAll() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return inputOutput;
            };
            AtomicReference<List<Future<String>>> refFutures = new AtomicReference<>();

            assertDoesNotThrow(() -> refFutures.set(executorService.invokeAll(List.of(callable))));

            assertThat(refFutures.get()).isNotNull();
            assertThat(refFutures.get()).hasSize(1);
            refFutures.get().forEach(future -> {
                assertDoesNotThrow(() -> future.get(10, TimeUnit.MILLISECONDS));
                assertDoesNotThrow(() -> assertThat(future.get()).isEqualTo(inputOutput));
            });
        }
    }

    @Test
    void invokeAllWithTimeout() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return inputOutput;
            };
            AtomicReference<List<Future<String>>> refFutures = new AtomicReference<>();

            assertDoesNotThrow(() ->
                    refFutures.set(executorService.invokeAll(List.of(callable), 10, TimeUnit.MILLISECONDS)));

            assertThat(refFutures.get()).isNotNull();
            assertThat(refFutures.get()).hasSize(1);
            refFutures.get().forEach(future -> {
                CancellationException futureGetException =
                        assertThrows(CancellationException.class, future::get);
                assertThat(futureGetException).hasCauseThat().isNull();
                assertThat(futureGetException).hasMessageThat().isNull();
                CancellationException futureGetWithTimeoutException =
                        assertThrows(CancellationException.class, () -> future.get(10, TimeUnit.MILLISECONDS));
                assertThat(futureGetWithTimeoutException).hasCauseThat().isNull();
                assertThat(futureGetWithTimeoutException).hasMessageThat().isNull();
            });
        }
    }

    @Test
    void invokeAny() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return inputOutput;
            };
            AtomicReference<String> refFuture = new AtomicReference<>();

            assertDoesNotThrow(() -> refFuture.set(executorService.invokeAny(List.of(callable))));

            assertThat(refFuture.get()).isNotNull();
            assertThat(refFuture.get()).isEqualTo(inputOutput);
        }
    }

    @Test
    void callableThrowsException_invokeAnyThrowsExecutionException() {
        Exception stubException = new Exception("failed");
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            Callable<String> callable = () -> {
                Thread.sleep(100);
                throw stubException;
            };
            AtomicReference<String> refFuture = new AtomicReference<>();

            ExecutionException invokeAnyException = assertThrows(ExecutionException.class, () ->
                    refFuture.set(executorService.invokeAny(List.of(callable))));

            assertThat(invokeAnyException).hasCauseThat().isEqualTo(stubException);
            assertThat(invokeAnyException).hasMessageThat().isEqualTo(stubException.toString());
            assertThat(refFuture.get()).isNull();
        }
    }

    @Test
    void invokeAnyWithTimeout() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            String inputOutput = "output";
            Callable<String> callable = () -> {
                Thread.sleep(100);
                return inputOutput;
            };
            AtomicReference<String> refFuture = new AtomicReference<>();

            TimeoutException invokeAnyWithTimeoutException = assertThrows(TimeoutException.class, () -> refFuture.set(
                    executorService.invokeAny(
                            List.of(callable),
                            10, TimeUnit.MILLISECONDS)));

            assertThat(invokeAnyWithTimeoutException).hasCauseThat().isNull();
            assertThat(invokeAnyWithTimeoutException).hasMessageThat().isNull();
            assertThat(refFuture.get()).isNull();
        }
    }
}
