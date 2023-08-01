package concurrency.multithreading.completable.future;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompletableFutureTest {

    @Test
    void extendsFuture() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Future<String> future = completableFuture;
    }

    @Test
    void withComplete_get_returnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";
        AtomicReference<String> actualOutputRef = new AtomicReference<>();

        completableFuture.complete(inputOutput);
        assertDoesNotThrow(() -> actualOutputRef.set(completableFuture.get()));

        assertThat(actualOutputRef.get()).isEqualTo(inputOutput);
        assertThat(completableFuture.getNow("valueIfAbsent")).isEqualTo(inputOutput);
    }

    @Test
    void withCompleteAsync_get_returnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";
        AtomicReference<String> actualOutputRef = new AtomicReference<>();

        completableFuture.completeAsync(() -> inputOutput);
        assertDoesNotThrow(() -> actualOutputRef.set(completableFuture.get()));

        assertThat(actualOutputRef.get()).isEqualTo(inputOutput);
        assertThat(completableFuture.getNow("valueIfAbsent")).isEqualTo(inputOutput);
    }

    @Test
    void cancel_get_throwsCancellationException_notYetCompleted_wasCancelledTrue_mayInterruptIfRunningTrue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        // has no effect
        boolean mayInterruptIfRunning = true;
        AtomicReference<CancellationException> getExceptionRef = new AtomicReference<>();

        completableFuture.completeAsync(() -> {
            boolean alwaysTrue = true;
            while (alwaysTrue) ;
            return "completableFuture_completed";
        });
        boolean wasCancelled = completableFuture.cancel(mayInterruptIfRunning);
        assertDoesNotThrow(() -> getExceptionRef.set(
                assertThrows(CancellationException.class, completableFuture::get)));

        assertThat(wasCancelled).isTrue();
        assertThat(getExceptionRef.get()).hasCauseThat().isNull();
        assertThat(getExceptionRef.get()).hasMessageThat().isNull();
    }

    @Test
    void cancel_get_throwsCancellationException_notYetCompleted_wasCancelledTrue_mayInterruptIfRunningFalse() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        // has no effect
        boolean mayInterruptIfRunning = false;
        AtomicReference<CancellationException> getExceptionRef = new AtomicReference<>();

        completableFuture.completeAsync(() -> {
            boolean alwaysTrue = true;
            while (alwaysTrue) ;
            return "completableFuture_completed";
        });
        boolean wasCancelled = completableFuture.cancel(mayInterruptIfRunning);
        assertDoesNotThrow(() -> getExceptionRef.set(
                assertThrows(CancellationException.class, completableFuture::get)));

        assertThat(wasCancelled).isTrue();
        assertThat(getExceptionRef.get()).hasCauseThat().isNull();
        assertThat(getExceptionRef.get()).hasMessageThat().isNull();
    }

    @Test
    void cancel_get_returnsOutputBeforeCancel_alreadyCompleted_wasCancelledFalse() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        // has no effect
        boolean mayInterruptIfRunning = true;
        String inputOutput = "completableFuture_output";
        AtomicReference<String> getOutputRef = new AtomicReference<>();

        completableFuture.completeAsync(() -> inputOutput);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> getOutputRef.set(completableFuture.get()));
        boolean wasCancelled = completableFuture.cancel(mayInterruptIfRunning);

        assertThat(getOutputRef.get()).isEqualTo(inputOutput);
        assertThat(wasCancelled).isFalse();
    }

    @Test
    void withoutComplete_getWithTimeout_throwsTimeoutException() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        TimeoutException timeoutException =
                assertThrows(TimeoutException.class, () -> completableFuture.get(100L, MILLISECONDS));
        assertThat(timeoutException).hasMessageThat().isNull();
        assertThat(timeoutException).hasCauseThat().isNull();
    }

    @Test
    void withoutComplete_get_gettingThreadHangs() {
        long timeoutLimit = 100L;
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        AtomicReference<String> completableFutureOutput = new AtomicReference<>();
        Callable<String> callable = () -> {
            completableFutureOutput.set(completableFuture.get());
            return "callableCompleted";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);

        thread.start();
        assertDoesNotThrow(() -> Thread.sleep(timeoutLimit));
        assertThat(futureTask.isDone()).isFalse();
        thread.interrupt();
        assertDoesNotThrow(() -> thread.join());

        assertThat(completableFutureOutput.get()).isNull();
        assertThat(futureTask.isDone()).isTrue();
        assertThat(futureTask.exceptionNow()).isInstanceOf(InterruptedException.class);
        assertThat(futureTask.exceptionNow()).hasCauseThat().isNull();
        assertThat(futureTask.exceptionNow()).hasMessageThat().isNull();
        ExecutionException futureTaskGetException = assertThrows(ExecutionException.class, futureTask::get);
        assertThat(futureTaskGetException).hasCauseThat().isEqualTo(futureTask.exceptionNow());
        assertThrows(TimeoutException.class, () -> completableFuture.get(timeoutLimit, MILLISECONDS));
    }

    @Test
    void completeExceptionally_get_throwsExecutionException() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Throwable stubThrowable = new Throwable("stub exception msg");

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            executorService.submit(() -> {
                completableFuture.completeExceptionally(stubThrowable);
            });
        }

        ExecutionException actualException = assertThrows(ExecutionException.class, completableFuture::get);
        assertThat(actualException).hasCauseThat().isEqualTo(stubThrowable);
        assertThat(actualException).hasMessageThat().isEqualTo(stubThrowable.toString());
    }

    @Test
    void interruptedThread_getCompletableFuture_throwsInterruptedException() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Exception notReachable = new Exception("not reachable");
        Callable<String> callableNeverNotified = () -> {
            Thread.currentThread().wait();
            completableFuture.complete(notReachable.getMessage());
            throw notReachable;
        };
        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            executorService.submit(callableNeverNotified);
        }

        Thread outerInterruptedThread = new Thread(() -> {
            InterruptedException interruptedThreadCfGetException =
                    assertThrows(InterruptedException.class, completableFuture::get);
            assertThat(interruptedThreadCfGetException).hasMessageThat().isNull();
            assertThat(interruptedThreadCfGetException).hasCauseThat().isNull();
        });
        outerInterruptedThread.start();
        outerInterruptedThread.interrupt();

        TimeoutException mainThreadCfGetWithTimeoutException =
                assertThrows(TimeoutException.class, () -> completableFuture.get(100L, MILLISECONDS));
        assertThat(mainThreadCfGetWithTimeoutException).hasMessageThat().isNull();
        assertThat(mainThreadCfGetWithTimeoutException).hasCauseThat().isNull();

        assertThat(completableFuture.getNow("valueIfAbsent")).isEqualTo("valueIfAbsent");

        IllegalStateException mainCfGetExceptionNowException =
                assertThrows(IllegalStateException.class, completableFuture::exceptionNow);
        assertThat(mainCfGetExceptionNowException).hasCauseThat().isNull();
        assertThat(mainCfGetExceptionNowException).hasMessageThat().isNull();
    }

    @Test
    void completeAsync_sleeps_getReturnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";

        Thread thread = new Thread(() -> {
            completableFuture.completeAsync(() -> {
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return inputOutput;
            });
        });
        thread.start();

        TimeoutException getWithTimeoutException =
                assertThrows(TimeoutException.class, () ->
                        completableFuture.get(10L, MILLISECONDS));
        assertThat(getWithTimeoutException).hasMessageThat().isNull();
        assertThat(getWithTimeoutException).hasCauseThat().isNull();
        assertDoesNotThrow(() -> assertThat(completableFuture.get()).isEqualTo(inputOutput));
    }

    @Test
    void complete_threadSleep_getWithTimeoutThrowsTimeoutException_getReturnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(250L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            completableFuture.complete(inputOutput);
        });
        thread.start();

        TimeoutException getWithTimeoutException =
                assertThrows(TimeoutException.class, () ->
                        completableFuture.get(10L, MILLISECONDS));
        assertThat(getWithTimeoutException).hasMessageThat().isNull();
        assertThat(getWithTimeoutException).hasCauseThat().isNull();
        assertDoesNotThrow(() -> assertThat(completableFuture.get()).isEqualTo(inputOutput));
    }

    @Test
    void completeAsync_threadSleeps_getWithTimeoutThrowsTimeoutException_getReturnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            completableFuture.completeAsync(() -> inputOutput);
        });
        thread.start();

        TimeoutException getWithTimeoutException =
                assertThrows(TimeoutException.class, () ->
                        completableFuture.get(10L, MILLISECONDS));
        assertThat(getWithTimeoutException).hasMessageThat().isNull();
        assertThat(getWithTimeoutException).hasCauseThat().isNull();
        assertDoesNotThrow(() -> assertThat(completableFuture.get()).isEqualTo(inputOutput));
    }

    @Test
    void completeAsync_executionServiceSleeps_getReturnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";

        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            Future<String> executorFuture = executorService.submit(() -> {
                Thread.sleep(500L);
                completableFuture.completeAsync(() -> inputOutput);
                return "callableOutput";
            });
            assertThat(executorFuture.isDone()).isFalse();
            TimeoutException executorFutureException =
                    assertThrows(TimeoutException.class, () -> executorFuture.get(10L, MILLISECONDS));
            assertThat(executorFutureException).hasCauseThat().isNull();
            assertThat(executorFutureException).hasMessageThat().isNull();


            assertThat(completableFuture.isDone()).isFalse();
            TimeoutException completableFutureException =
                    assertThrows(TimeoutException.class, () -> completableFuture.get(10L, MILLISECONDS));
            assertThat(completableFutureException).hasCauseThat().isNull();
            assertThat(completableFutureException).hasMessageThat().isNull();
        }
        assertDoesNotThrow(() -> Thread.sleep(100L));
        // This does not throw exception because:
        // 1) sleep is part of Executor's submit's (method) callable (lambda)
        // 2) we are outside of scope of ExecutorService, so it has to be completed
        // See test below
        while (!completableFuture.isDone()) ;
        assertThat(completableFuture.isDone()).isTrue();
        assertDoesNotThrow(() ->
                assertThat(completableFuture.get(10L, MILLISECONDS)).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(completableFuture.get()).isEqualTo(inputOutput));
    }

    @Test
    void completeAsync_cfSleeps_getWithTimeoutThrowsTimeoutException_getReturnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";

        try (ExecutorService executorService = Executors.newFixedThreadPool(1)) {
            executorService.submit(() -> {
                completableFuture.completeAsync(() -> {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return inputOutput;
                });
                return "callableOutput";
            });
        }

        TimeoutException getWithTimeoutException =
                assertThrows(TimeoutException.class, () -> completableFuture.get(10L, MILLISECONDS));
        assertThat(getWithTimeoutException).hasCauseThat().isNull();
        assertThat(getWithTimeoutException).hasMessageThat().isNull();
        assertDoesNotThrow(() -> assertThat(completableFuture.get()).isEqualTo(inputOutput));
    }

    @Test
    void asyncComplete_resultNow_returnsExpectedValue_sleep_immediatelyStartsExecutor() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";
        long sleepMillis = 150L;
        long startNano = System.nanoTime();
        // uses default Executor
        completableFuture.completeAsync(() -> {
            assertDoesNotThrow(() -> Thread.sleep(sleepMillis));
            return inputOutput;
        });

        while (!completableFuture.isDone()) ;
        assertThat(completableFuture.isDone()).isTrue();
        long finishNano = System.nanoTime();
        assertThat(finishNano - startNano).isGreaterThan((long) (sleepMillis * Math.pow(10, 6)));
        assertThat(completableFuture.resultNow()).isEqualTo(inputOutput);
    }

    @Test
    void join_completeAsync_afterWaitThreadNotified_returnsExpectedValue() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";
        WaitThread waitThread = new WaitThread("join_returnsExpectedValue");
        waitThread.start();
        completableFuture.completeAsync(() -> {
            assertDoesNotThrow(() -> waitThread.join());
            return inputOutput;
        });

        assertThat(completableFuture.isDone()).isFalse();

        assertDoesNotThrow(() -> Thread.sleep(500L));

        assertThat(completableFuture.isDone()).isFalse();

        waitThread.keepNotifyingUntilNotified();

        assertThat(completableFuture.join()).isEqualTo(inputOutput);
    }

    @Test
    void join_completeAsync_afterWaitThreadNotified_joiningThreadHangs() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String inputOutput = "output";
        Thread waitThread = new WaitThread("hangingThread test: "
                + "join_completeAsync_afterWaitThreadNotified_joiningThreadHangs");
        waitThread.start();
        completableFuture.completeAsync(() -> {
            assertDoesNotThrow(() -> waitThread.join());
            return inputOutput;
        });

        AtomicReference<String> cfOutput = new AtomicReference<>(null);
        Thread joinThread = new Thread(() -> cfOutput.set(completableFuture.join()));
        joinThread.start();

        assertDoesNotThrow(() -> Thread.sleep(500L));

        assertThat(waitThread.getState()).isEqualTo(Thread.State.WAITING);
        assertThat(joinThread.getState()).isEqualTo(Thread.State.WAITING);
        assertThat(cfOutput.get()).isNull();
        assertThat(completableFuture.isDone()).isFalse();
        while (waitThread.getState() != Thread.State.TERMINATED) {
            synchronized (waitThread) {
                waitThread.notify();
            }
        }
        try {
            waitThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void thenApplyAsync_completeAsync_thenApplyAsync_thenApplyAsyncBeforeCompleteAsyncIgnored() {
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture<String> cfInput = new CompletableFuture<>();

        WaitThread thenApplyAsync1WaitThread = new WaitThread("completeAsync");
        thenApplyAsync1WaitThread.start();

        CompletableFuture<String> cfThenApplyAsync1 = cfInput.thenApplyAsync(input -> {
            try {
                thenApplyAsync1WaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("thenApplyAsync #1: " + input + " 1st");
            return input + " 1st";
        });

        WaitThread completeAsyncWaitThread = new WaitThread("completeAsync");
        completeAsyncWaitThread.start();

        String inputOutput = "output";
        CompletableFuture<String> cfCompleteAsync = cfThenApplyAsync1.completeAsync(() -> {
            try {
                completeAsyncWaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("completeAsync: " + inputOutput);
            return inputOutput;
        });

        WaitThread thenApplyAsync2WaitThread = new WaitThread("completeAsync");
        thenApplyAsync2WaitThread.start();

        CompletableFuture<String> cfThenApplyAsync2 = cfCompleteAsync.thenApplyAsync(input -> {
            try {
                thenApplyAsync2WaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("thenApplyAsync #2: " + input + " 2nd");
            return input + " 2nd";
        });

        assertThat(cfInput).isNotEqualTo(cfThenApplyAsync1);
        assertThat(cfInput).isNotEqualTo(cfCompleteAsync);
        assertThat(cfInput).isNotEqualTo(cfThenApplyAsync2);
        assertThat(cfThenApplyAsync1).isNotEqualTo(cfInput);
        assertThat(cfThenApplyAsync1).isEqualTo(cfCompleteAsync);
        assertThat(cfThenApplyAsync1).isNotEqualTo(cfThenApplyAsync2);
        assertThat(cfCompleteAsync).isNotEqualTo(cfInput);
        assertThat(cfCompleteAsync).isEqualTo(cfThenApplyAsync1);
        assertThat(cfCompleteAsync).isNotEqualTo(cfThenApplyAsync2);
        assertThat(cfThenApplyAsync2).isNotEqualTo(cfInput);
        assertThat(cfThenApplyAsync2).isNotEqualTo(cfThenApplyAsync1);
        assertThat(cfThenApplyAsync2).isNotEqualTo(cfCompleteAsync);

        assertThat(cfInput.isDone()).isFalse();
        assertThat(cfThenApplyAsync1.isDone()).isFalse();
        assertThat(cfCompleteAsync.isDone()).isFalse();
        assertThat(cfThenApplyAsync2.isDone()).isFalse();

        assertThrows(TimeoutException.class, () ->
                assertThat(cfInput.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertThrows(TimeoutException.class, () ->
                assertThat(cfCompleteAsync.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertThrows(TimeoutException.class, () ->
                assertThat(cfThenApplyAsync2.get(100L, MILLISECONDS)).isEqualTo(inputOutput));

        thenApplyAsync1WaitThread.keepNotifyingUntilNotified();

        assertThrows(TimeoutException.class, () ->
                assertThat(cfThenApplyAsync1.get(100L, MILLISECONDS)).isEqualTo(inputOutput));

        completeAsyncWaitThread.keepNotifyingUntilNotified();

        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync1.get()).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfCompleteAsync.get()).isEqualTo(inputOutput));

        thenApplyAsync2WaitThread.keepNotifyingUntilNotified();

        assertThrows(TimeoutException.class, () ->
                assertThat(cfInput.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync1.get()).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfCompleteAsync.get()).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync2.get()).isEqualTo(inputOutput + " 2nd"));

        assertThat(logs).isEqualTo(List.of(
                "completeAsync: " + inputOutput,
                "thenApplyAsync #2: " + inputOutput + " 2nd"));
    }

    @Test
    void thenApplyAsync_complete_thenApplyAsync_thenApplyAsyncBeforeCompleteIgnored() {
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture<String> cfInput = new CompletableFuture<>();

        WaitThread thenApplyAsync1WaitThread = new WaitThread("completeAsync");
        thenApplyAsync1WaitThread.start();

        CompletableFuture<String> cfThenApplyAsync1 = cfInput.thenApplyAsync(input -> {
            try {
                thenApplyAsync1WaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("thenApplyAsync #1: " + input + " 1st");
            return input + " 1st";
        });

        WaitThread completeWaitThread = new WaitThread("completeAsync");
        completeWaitThread.start();
        String inputOutput = "output";
        Supplier<String> completeSupplier = () -> {
            try {
                completeWaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("complete: " + inputOutput);
            return inputOutput;
        };
        FutureTask<Boolean> cfIsCompleteFt = new FutureTask<>(() -> cfThenApplyAsync1.complete(completeSupplier.get()));
        Thread cfIsCompleteThread = new Thread(cfIsCompleteFt);
        cfIsCompleteThread.start();

        WaitThread thenApplyAsync2WaitThread = new WaitThread("completeAsync");
        thenApplyAsync2WaitThread.start();

        CompletableFuture<String> cfThenApplyAsync2 = cfThenApplyAsync1.thenApplyAsync(input -> {
            try {
                thenApplyAsync2WaitThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logs.add("thenApplyAsync #2: " + input + " 2nd");
            return input + " 2nd";
        });

        assertThat(cfInput).isNotEqualTo(cfThenApplyAsync1);
        assertThat(cfInput).isNotEqualTo(cfThenApplyAsync2);
        assertThat(cfThenApplyAsync1).isNotEqualTo(cfInput);
        assertThat(cfThenApplyAsync1).isNotEqualTo(cfThenApplyAsync2);
        assertThat(cfThenApplyAsync2).isNotEqualTo(cfInput);
        assertThat(cfThenApplyAsync2).isNotEqualTo(cfThenApplyAsync1);

        assertThat(cfInput.isDone()).isFalse();
        assertThat(cfThenApplyAsync1.isDone()).isFalse();
        assertThat(cfIsCompleteFt.isDone()).isFalse();
        assertThat(cfThenApplyAsync2.isDone()).isFalse();

        assertThrows(TimeoutException.class, () ->
                assertThat(cfInput.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertThrows(TimeoutException.class, () ->
                assertThat(cfThenApplyAsync1.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertThrows(TimeoutException.class, () ->
                assertThat(cfThenApplyAsync2.get(100L, MILLISECONDS)).isEqualTo(inputOutput));

        // not needed
        // thenApplyAsync1WaitThread.keepNotifyingUntilNotified();
        completeWaitThread.keepNotifyingUntilNotified();
        while (logs.size() < 1) ;
        assertThat(logs).isEqualTo(List.of("complete: " + inputOutput));
        assertThat(cfIsCompleteFt.isDone()).isTrue();
        assertDoesNotThrow(() -> assertThat(cfIsCompleteFt.get()).isTrue());
        assertThat(cfThenApplyAsync1.isDone()).isTrue();
        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync1.get()).isEqualTo(inputOutput));

        thenApplyAsync2WaitThread.keepNotifyingUntilNotified();

        assertThrows(TimeoutException.class, () ->
                assertThat(cfInput.get(100L, MILLISECONDS)).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync1.get()).isEqualTo(inputOutput));
        assertDoesNotThrow(() -> assertThat(cfThenApplyAsync2.get()).isEqualTo(inputOutput + " 2nd"));

        assertThat(logs).isEqualTo(List.of(
                "complete: " + inputOutput,
                "thenApplyAsync #2: " + inputOutput + " 2nd"));
    }

    @Test
    void async_vs_nonAsyncMethods() {
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        WaitThread thenApplyAsync1WaitThread = new WaitThread("thenApplyAsync1");
        WaitThread thenApply1WaitThread = new WaitThread("thenApply1");
        WaitThread completeAsyncWaitThread = new WaitThread("completeAsync");
        WaitThread thenApplyAsync2WaitThread = new WaitThread("thenApplyAsync2");
        WaitThread thenApply2WaitThread = new WaitThread("thenApply2");
        Stream.of(thenApplyAsync1WaitThread,
                        completeAsyncWaitThread,
                        thenApplyAsync2WaitThread,
                        thenApply2WaitThread)
                .forEach(Thread::start);
        String inputOutput = "output";
        ThreadPerTaskExecutor thenApplyAsync1Executor = new ThreadPerTaskExecutor();
        ThreadPerTaskExecutor thenApplyAsync2Executor = new ThreadPerTaskExecutor();
        ThreadPerTaskExecutor completeAsyncExecutor = new ThreadPerTaskExecutor();

        CompletableFuture<String> cf = new CompletableFuture<>()
                .thenApplyAsync(input -> {
                    try {
                        thenApplyAsync1WaitThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("[ NOT EXECUTED 1 ] thenApplyAsync #1:"
                            + input
                            + " (thread=" + Thread.currentThread().getName() + ")");
                    return input + " NOT EXECUTED 1) thenApplyAsync #1";
                }, thenApplyAsync1Executor)
                .thenApply(input -> {
                    try {
                        thenApply1WaitThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("[ NOT EXECUTED 2 ] thenApply #1:"
                            + input
                            + " (thread=" + Thread.currentThread().getName() + ")");
                    return input + " NOT EXECUTED 2) thenApply #1";
                })
                .completeAsync(() -> {
                    try {
                        completeAsyncWaitThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("[ 1st ] completeAsync: "
                            + inputOutput
                            + " (thread=" + Thread.currentThread().getName() + ")");
                    return inputOutput + " 1) completeAsync";
                }, completeAsyncExecutor)
                .thenApplyAsync(input -> {
                    try {
                        thenApplyAsync2WaitThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("[ 2nd ] thenApplyAsync #2: "
                            + input
                            + " (thread=" + Thread.currentThread().getName() + ")");
                    return input + " 2) thenApplyAsync #2";
                }, thenApplyAsync2Executor)
                .thenApply(input -> {
                    try {
                        thenApply2WaitThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("[ 3rd ] thenApply #2: "
                            + input +
                            " (thread=" + Thread.currentThread().getName() + ")");
                    return input + " 3) thenApply #2";
                });

        assertThat(logs).isEmpty();

        completeAsyncWaitThread.keepNotifyingUntilNotified();
        while (logs.size() < 1) ;
        assertThat(cf.isDone()).isFalse();
        assertThat(logs).isEqualTo(List.of(
                "[ 1st ] completeAsync: "
                        + inputOutput
                        + " (thread=" + completeAsyncExecutor.thread.getName() + ")"));

        thenApplyAsync2WaitThread.keepNotifyingUntilNotified();
        while (logs.size() < 2) ;
        assertThat(cf.isDone()).isFalse();
        assertThat(logs).isEqualTo(List.of(
                "[ 1st ] completeAsync: "
                        + inputOutput
                        + " (thread=" + completeAsyncExecutor.thread.getName() + ")",
                "[ 2nd ] thenApplyAsync #2: "
                        + inputOutput + " 1) completeAsync"
                        + " (thread=" + thenApplyAsync2Executor.thread.getName() + ")"));

        thenApply2WaitThread.keepNotifyingUntilNotified();
        while (logs.size() < 3) ;
        while (!cf.isDone()) ;
        assertThat(cf.isDone()).isTrue();
        assertThat(cf.resultNow()).isEqualTo(inputOutput + " 1) completeAsync 2) thenApplyAsync #2 3) thenApply #2");
        assertThat(logs).isEqualTo(List.of(
                "[ 1st ] completeAsync: "
                        + inputOutput
                        + " (thread=" + completeAsyncExecutor.thread.getName() + ")",
                "[ 2nd ] thenApplyAsync #2: "
                        + inputOutput + " 1) completeAsync"
                        + " (thread=" + thenApplyAsync2Executor.thread.getName() + ")",
                "[ 3rd ] thenApply #2: "
                        + inputOutput + " 1) completeAsync 2) thenApplyAsync #2"
                        + " (thread=" + thenApplyAsync2Executor.thread.getName() + ")"));
    }

    @Test
    void simpleCfCompounding() {
        CompletableFuture<String> startCf = new CompletableFuture<>()
                .thenApplyAsync(input -> input + "NOT EXECUTED");

        String inputOutput = "output";
        CompletableFuture<CompletableFuture<String>> cfOfCf = new CompletableFuture<CompletableFuture<String>>()
                .completeAsync(() -> {
                    try {
                        Thread.sleep(150L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return startCf
                            .completeAsync(() -> inputOutput + " 1) completeAsync #1")
                            .thenApplyAsync(input -> input + " 2) thenApply 3) completeAsync #2");
                });

        assertThat(startCf.isDone()).isFalse();
        while (!cfOfCf.isDone()) ;
        assertThat(cfOfCf.isDone()).isTrue();
        AtomicReference<CompletableFuture<String>> finalCfRef = new AtomicReference<>();
        assertDoesNotThrow(() -> finalCfRef.set(cfOfCf.get()));
        while (!finalCfRef.get().isDone()) ;
        assertThat(finalCfRef.get().isDone()).isTrue();
        assertDoesNotThrow(() -> assertThat(finalCfRef.get().get()).isEqualTo(
                inputOutput +
                        " 1) completeAsync #1" +
                        " 2) thenApply" +
                        " 3) completeAsync #2"));
    }

    @Test
    void thenCombine() {
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        WaitThread waitThreadA = new WaitThread("A");
        waitThreadA.start();
        CompletableFuture<String> cfA = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThreadA.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("cfA.completeAsync(...)");
                    return "cfA.completeAsync_output";
                });
        WaitThread waitThreadB = new WaitThread("B");
        waitThreadB.start();
        CompletableFuture<String> cfB = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThreadB.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logs.add("cfB.completeAsync(...)");
                    return "cfB.completeAsync_output";
                });

        CompletableFuture<String> cfCombined = cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> {
            logs.add("cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...) cfAOutput: " + cfAOutput);
            logs.add("cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...) cfBOutput: " + cfBOutput);
            return "cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...) cfAOutput: " + cfAOutput
                    + ", cfBOutput: "
                    + cfBOutput;
        });

        assertThat(cfA.isDone()).isFalse();
        assertThat(cfB.isDone()).isFalse();
        assertThat(cfCombined.isDone()).isFalse();

        while (waitThreadA.getState() != Thread.State.TERMINATED) {
            synchronized (waitThreadA) {
                waitThreadA.notify();
            }
        }
        while (!cfA.isDone()) ;

        assertThat(cfA.isDone()).isTrue();
        assertThat(cfB.isDone()).isFalse();
        assertThat(cfCombined.isDone()).isFalse();

        while (waitThreadB.getState() != Thread.State.TERMINATED) {
            synchronized (waitThreadB) {
                waitThreadB.notify();
            }
        }
        while (!cfB.isDone()) ;
        while (!cfCombined.isDone()) ;

        assertThat(cfA.isDone()).isTrue();
        assertThat(cfB.isDone()).isTrue();
        assertThat(cfCombined.isDone()).isTrue();

        assertDoesNotThrow(() -> assertThat(cfA.get()).isEqualTo("cfA.completeAsync_output"));
        assertDoesNotThrow(() -> assertThat(cfB.get()).isEqualTo("cfB.completeAsync_output"));
        assertDoesNotThrow(() -> assertThat(cfCombined.get()).isEqualTo(
                "cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...)" +
                        " cfAOutput: " + "cfA.completeAsync_output" +
                        ", cfBOutput: " + "cfB.completeAsync_output"));
        assertThat(logs).isEqualTo(List.of(
                "cfA.completeAsync(...)",
                "cfB.completeAsync(...)",
                "cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...) cfAOutput: " + "cfA.completeAsync_output",
                "cfA.thenCombine(cfB, (cfAOutput, cfBOutput) -> ...) cfBOutput: " + "cfB.completeAsync_output"));
    }

    @Test
    void thenCombineAsync_successAndFailure_getThrowsExecutionException_doesNotExecuteCombineBlock() {
        CompletableFuture<String> cfSuccess = CompletableFuture.completedFuture("cfSuccess_output");
        Throwable stubThrowable = new Throwable("stub throwable msg");
        CompletableFuture<String> cfFailure = CompletableFuture.failedFuture(stubThrowable);
        CompletableFuture<String> cfUnusedCombineResult =
                CompletableFuture.completedFuture("cfUnusedCombineResult_output");

        CompletableFuture<String> cfCombined = cfSuccess.thenCombineAsync(cfFailure, (success, failure) -> {
            return cfUnusedCombineResult.join();
        });

        ExecutionException actualException = assertThrows(ExecutionException.class, cfCombined::get);

        assertThat(actualException).hasCauseThat().isEqualTo(stubThrowable);
    }

    @Test
    void thenCombineAsync_successAndSuccess_executesCombineBlock() {
        CompletableFuture<String> cfSuccessA = CompletableFuture.completedFuture("cfSuccessA_output");
        CompletableFuture<String> cfSuccessB = CompletableFuture.completedFuture("cfSuccessB_output");
        Throwable stubThrowable = new Throwable("stub throwable msg");
        CompletableFuture<String> cfFailureCombineResult = CompletableFuture.failedFuture(stubThrowable);

        CompletableFuture<String> cfCombined = cfSuccessA.thenCombineAsync(cfSuccessB, (aOut, bOut) -> {
            return cfFailureCombineResult.join();
        });

        ExecutionException cfCombinedException = assertThrows(ExecutionException.class, cfCombined::get);
        ExecutionException cfFailureCombineResultException = assertThrows(ExecutionException.class, cfFailureCombineResult::get);

        assertThat(cfCombinedException).hasCauseThat().isEqualTo(stubThrowable);
        assertThat(cfFailureCombineResultException).hasCauseThat().isEqualTo(stubThrowable);
    }

    @Test
    void applyToEitherAsync() {
        List<String> logsA = Collections.synchronizedList(new ArrayList<>());
        List<String> logsB = Collections.synchronizedList(new ArrayList<>());
        WaitThread waitThreadA = new WaitThread("A");
        waitThreadA.start();
        CompletableFuture<String> cfA = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThreadA.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logsA.add("cfA.completeAsync(...)");
                    return "cfA.completeAsync_output";
                });
        WaitThread waitThreadB = new WaitThread("B");
        waitThreadB.start();
        CompletableFuture<String> cfB = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThreadB.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    logsA.add("cfB.completeAsync(...)");
                    logsB.add("cfB.completeAsync(...)");
                    return "cfB.completeAsync_output";
                });

        CompletableFuture<String> cfEitherA = cfA.applyToEitherAsync(cfB, cfOutput -> {
            logsA.add("cfA.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + cfOutput);
            return "cfA.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + cfOutput;
        });

        CompletableFuture<String> cfC = new CompletableFuture<>();
        CompletableFuture<String> cfEitherB = cfC.applyToEitherAsync(cfB, cfOutput -> {
            logsA.add("cfC.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + cfOutput);
            logsB.add("cfC.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + cfOutput);
            return "cfC.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + cfOutput;
        });

        assertThat(cfA.isDone()).isFalse();
        assertThat(cfB.isDone()).isFalse();
        assertThat(cfC.isDone()).isFalse();
        assertThat(cfEitherA.isDone()).isFalse();
        assertThat(cfEitherB.isDone()).isFalse();

        while (waitThreadA.getState() != Thread.State.TERMINATED) {
            synchronized (waitThreadA) {
                waitThreadA.notify();
            }
        }
        while (!cfA.isDone()) ;
        while (!cfEitherA.isDone()) ;

        assertThat(cfA.isDone()).isTrue();
        assertThat(cfB.isDone()).isFalse();
        assertThat(cfC.isDone()).isFalse();
        assertThat(cfEitherA.isDone()).isTrue();
        assertThat(cfEitherB.isDone()).isFalse();

        assertDoesNotThrow(() -> assertThat(cfA.get()).isEqualTo("cfA.completeAsync_output"));
        assertThrows(TimeoutException.class, () -> assertThat(cfB.get(100L, MILLISECONDS)));
        assertThrows(TimeoutException.class, () -> assertThat(cfC.get(100L, MILLISECONDS)));
        assertDoesNotThrow(() -> assertThat(cfEitherA.get())
                .isEqualTo("cfA.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: "
                        + "cfA.completeAsync_output"));
        assertThrows(TimeoutException.class, () -> assertThat(cfEitherB.get(100L, MILLISECONDS)));
        assertThat(logsA).isEqualTo(List.of(
                "cfA.completeAsync(...)",
                "cfA.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + "cfA.completeAsync_output"));

        while (waitThreadB.getState() != Thread.State.TERMINATED) {
            synchronized (waitThreadB) {
                waitThreadB.notify();
            }
        }
        while (!cfB.isDone()) ;
        while (!cfEitherB.isDone()) ;

        assertThat(cfA.isDone()).isTrue();
        assertThat(cfB.isDone()).isTrue();
        assertThat(cfC.isDone()).isFalse();
        assertThat(cfEitherA.isDone()).isTrue();
        assertThat(cfEitherB.isDone()).isTrue();

        assertDoesNotThrow(() -> assertThat(cfB.get()).isEqualTo("cfB.completeAsync_output"));
        assertDoesNotThrow(() -> assertThat(cfEitherB.get()).isEqualTo(
                "cfC.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + "cfB.completeAsync_output"));
        assertThat(logsB).isEqualTo(List.of(
                "cfB.completeAsync(...)",
                "cfC.applyToEitherAsync(cfB, cfOutput -> ...) cfOutput: " + "cfB.completeAsync_output"));
    }

    @Test
    void whenCompleteAsync_success() {
        List<String> beforeLogs = Collections.synchronizedList(new ArrayList<>());
        List<String> afterLogs = Collections.synchronizedList(new ArrayList<>());

        CompletableFuture<String> cf = new CompletableFuture<String>()
                .whenCompleteAsync((input, e) -> {
                    assertThat(input).isNotNull();
                    assertThat(e).isNull();
                    beforeLogs.add(input);
                })
                .completeAsync(() -> "successful_output")
                .whenCompleteAsync((input, e) -> {
                    assertThat(input).isNotNull();
                    assertThat(e).isNull();
                    afterLogs.add(input);
                });

        assertDoesNotThrow(() -> assertThat(cf.get()).isEqualTo("successful_output"));
        assertThat(beforeLogs).isEmpty();
        assertThat(afterLogs).isEqualTo(List.of("successful_output"));
    }

    @Test
    void whenCompleteAsync_failure_completeExceptionally() {
        List<Throwable> beforeLogs = Collections.synchronizedList(new ArrayList<>());
        List<Throwable> afterLogs = Collections.synchronizedList(new ArrayList<>());
        Throwable stubThrowable = new Throwable("stub throwable msg");

        CompletableFuture<String> cfStart = new CompletableFuture<String>()
                .whenCompleteAsync((input, e) -> {
                    assertThat(input).isNull();
                    assertThat(e).isNotNull();
                    beforeLogs.add(e);
                });
        cfStart.completeExceptionally(stubThrowable);
        CompletableFuture<String> cfFinish = cfStart.
                whenCompleteAsync((input, e) -> {
                    assertThat(input).isNull();
                    assertThat(e).isNotNull();
                    afterLogs.add(e);
                });


        ExecutionException actualException1 = assertThrows(ExecutionException.class, () -> assertThat(cfStart.get()));
        assertThat(actualException1).hasCauseThat().isEqualTo(stubThrowable);
        ExecutionException actualException2 = assertThrows(ExecutionException.class, () -> assertThat(cfFinish.get()));
        assertThat(actualException2).hasCauseThat().isEqualTo(stubThrowable);
        assertThat(actualException1).isNotEqualTo(actualException2);
        assertThat(beforeLogs).isEmpty();
        assertThat(afterLogs).isEqualTo(List.of(stubThrowable));
    }

    @Test
    void handingExceptions() {
        AtomicBoolean hasCfAWhenCompleteAsyncBlockRun = new AtomicBoolean(false);
        CompletableFuture<String> cfA = new CompletableFuture<String>()
                .whenCompleteAsync((input, e) -> {
                    hasCfAWhenCompleteAsyncBlockRun.set(true);
                    assertThat(input).isNull();
                    assertThat(e).isNotNull();
                })
                .completeAsync(() -> "unused_cfA_output");
        assertThat(hasCfAWhenCompleteAsyncBlockRun.get()).isFalse();

        CompletableFuture<String> cfB = CompletableFuture.completedFuture("unused_cfB_output");

        Throwable stubThrowable = new Throwable("stub throwable msg");
        AtomicBoolean hasCfCombinedThenCombineBlockRun = new AtomicBoolean(false);
        AtomicBoolean hasCfCombinedWhenCompleteAsyncBlockRun = new AtomicBoolean(false);
        AtomicBoolean hasCfCombinedExceptionallyComposeBlockRun = new AtomicBoolean(false);
        AtomicBoolean hasCfCombinedHandleBlockRun = new AtomicBoolean(false);
        CompletableFuture<String> cfCombined = cfA
                .thenCombine(cfB, (aOut, bOut) -> {
                    hasCfCombinedThenCombineBlockRun.set(true);
                    return CompletableFuture.<String>failedFuture(stubThrowable).join();
                })
                .whenCompleteAsync((input, throwable) -> {
                    hasCfCombinedWhenCompleteAsyncBlockRun.set(true);
                    assertThat(input).isNull();
                    assertThat(throwable).isInstanceOf(CompletionException.class);
                    assertThat(throwable).hasCauseThat().isEqualTo(stubThrowable);
                })
                .exceptionallyCompose(throwable -> {
                    hasCfCombinedExceptionallyComposeBlockRun.set(true);
                    assertThat(throwable).isInstanceOf(CompletionException.class);
                    assertThat(throwable).hasCauseThat().isEqualTo(stubThrowable);
                    return CompletableFuture.failedFuture(throwable);
                })
                .handle((input, throwable) -> {
                    hasCfCombinedHandleBlockRun.set(true);
                    assertThat(input).isNull();
                    assertThat(throwable).isInstanceOf(CompletionException.class);
                    assertThat(throwable).hasCauseThat().isEqualTo(stubThrowable);
                    return CompletableFuture.<String>failedFuture(throwable).join();
                });

        ExecutionException getException = assertThrows(ExecutionException.class, () -> assertThat(cfCombined.get()));
        CompletionException joinException = assertThrows(CompletionException.class, () -> assertThat(cfCombined.join()));
        assertThat(getException).hasCauseThat().isEqualTo(stubThrowable);
        assertThat(joinException).hasCauseThat().isEqualTo(stubThrowable);
        assertThat(hasCfCombinedThenCombineBlockRun.get()).isEqualTo(true);
        assertThat(hasCfCombinedWhenCompleteAsyncBlockRun.get()).isEqualTo(true);
        assertThat(hasCfCombinedExceptionallyComposeBlockRun.get()).isEqualTo(true);
        assertThat(hasCfCombinedHandleBlockRun.get()).isEqualTo(true);
    }

    @Test
    void handingExceptions_differentInputTypeFromOutputType_handle() {
        Throwable stubThrowable = new Throwable("stub throwable msg");
        Integer stubOutput = 1000;
        Function<CompletableFuture<Integer>, CompletableFuture<String>> method = (cfInteger) -> cfInteger
                .handle((input, throwable) -> {
                    if (throwable == null) {
                        return String.valueOf(input);
                    } else {
                        return "Failed future. " + throwable;
                    }
                });
        CompletableFuture<String> cfHandledFailed = method.apply(CompletableFuture.failedFuture(stubThrowable));
        CompletableFuture<String> cfHandledCompleted = method.apply(CompletableFuture.completedFuture(stubOutput));

        assertThat(cfHandledFailed.join()).isEqualTo("Failed future. " + stubThrowable);
        assertThat(cfHandledCompleted.join()).isEqualTo(String.valueOf(stubOutput));

        assertDoesNotThrow(() -> assertThat(cfHandledFailed.get()).isEqualTo("Failed future. " + stubThrowable));
        assertDoesNotThrow(() -> assertThat(cfHandledCompleted.get()).isEqualTo(String.valueOf(stubOutput)));
    }

    @Test
    void handingExceptions_differentInputTypeFromOutputType_simple() {
        Throwable stubThrowable = new Throwable("stub throwable msg");
        Integer stubOutput = 1000;
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        Function<CompletableFuture<Integer>, CompletableFuture<String>> method = (cfInteger) -> cfInteger
                .thenApplyAsync(input -> {
                    logs.add("thenApplyAsync: " + input);
                    if (!(input instanceof Integer)) {
                        throw new RuntimeException("wrong type: " + input + " of type " + input.getClass().getSimpleName());
                    }
                    return String.valueOf(input);
                })
                .handle((input, throwable) -> {
                    if (throwable == null) {
                        logs.add("handle - success - input: " + input + ", throwable: " + throwable);
                        return input;
                    } else {
                        logs.add("handle - failure - input: " + input + ", throwable: " + throwable);
                        return "Failed future. " + throwable;
                    }
                });

        while (!logs.isEmpty()) logs.remove(0);

        CompletableFuture<String> cfHandledCompleted = method.apply(CompletableFuture.completedFuture(stubOutput));

        assertThat(cfHandledCompleted.join()).isEqualTo(String.valueOf(stubOutput));
        assertDoesNotThrow(() -> assertThat(cfHandledCompleted.get()).isEqualTo(String.valueOf(stubOutput)));
        assertThat(logs).isEqualTo(List.of(
                "thenApplyAsync: " + stubOutput,
                "handle - success - input: " + stubOutput + ", throwable: " + null));

        while (!logs.isEmpty()) logs.remove(0);

        CompletableFuture<String> cfHandledFailed = method.apply(CompletableFuture.failedFuture(stubThrowable));

        assertThat(cfHandledFailed.join()).isEqualTo("Failed future. " + new CompletionException(stubThrowable));
        assertDoesNotThrow(() -> assertThat(cfHandledFailed.get()).isEqualTo("Failed future. " + new CompletionException(stubThrowable)));
        assertThat(logs).isEqualTo(List.of(
                "handle - failure - input: " + null + ", throwable: " + new CompletionException(stubThrowable)));
    }

    @Test
    void handingExceptions_differentInputTypeFromOutputType_moreComplex() {
        Throwable stubThrowable = new Throwable("stub throwable msg");
        Integer stubOutput = 1000;
        List<String> logs = Collections.synchronizedList(new ArrayList<>());
        CompletableFuture<Integer> inputCf = CompletableFuture.completedFuture(stubOutput);
        AtomicReference<CompletableFuture<String>> unsureCf = new AtomicReference<>();
        Function<CompletableFuture<Integer>, CompletableFuture<String>> method = (cfInteger) -> cfInteger
                .thenApply(input -> {
                    logs.add("Input CF is returning: " + input);
                    return input;
                })
                .thenCombineAsync(unsureCf.get(), (input, unsureCfOut) -> {
                    logs.add("Unsure CF is returning: " + unsureCfOut);
                    return unsureCfOut;
                })
                .thenApplyAsync(input -> {
                    logs.add("Combined CF is returning: " + input);
                    return input;
                })
                .handle((input, throwable) -> {
                    if (throwable == null) {
                        logs.add("handle - success - input: " + input + ", throwable: " + throwable);
                        return input;
                    } else {
                        logs.add("handle - failure - input: " + input + ", throwable: " + throwable);
                        return "Failed future. " + throwable;
                    }
                });

        unsureCf.set(CompletableFuture.completedFuture(String.valueOf(stubOutput)));
        while (!logs.isEmpty()) logs.remove(0);

        CompletableFuture<String> cfHandledCompleted = method.apply(inputCf);

        assertThat(cfHandledCompleted.join()).isEqualTo(String.valueOf(stubOutput));
        assertDoesNotThrow(() -> assertThat(cfHandledCompleted.get()).isEqualTo(String.valueOf(stubOutput)));
        assertThat(logs).isEqualTo(List.of(
                "Input CF is returning: " + stubOutput,
                "Unsure CF is returning: " + stubOutput,
                "Combined CF is returning: " + stubOutput,
                "handle - success - input: " + stubOutput + ", throwable: " + null));

        unsureCf.set(CompletableFuture.failedFuture(stubThrowable));
        while (!logs.isEmpty()) logs.remove(0);

        CompletableFuture<String> cfHandledFailed = method.apply(inputCf);

        assertThat(cfHandledFailed.join()).isEqualTo("Failed future. " + new CompletionException(stubThrowable));
        assertDoesNotThrow(() -> assertThat(cfHandledFailed.get()).isEqualTo("Failed future. " + new CompletionException(stubThrowable)));
        assertThat(logs).isEqualTo(List.of(
                "Input CF is returning: " + stubOutput,
                "handle - failure - input: " + null + ", throwable: " + new CompletionException(stubThrowable)));
    }

    @Test
    void staticFailedFuture() {
        Throwable stubThrowable = new Throwable("");
        CompletableFuture<String> cf = CompletableFuture.failedFuture(stubThrowable);

        ExecutionException actualException = assertThrows(ExecutionException.class, cf::get);

        assertThat(actualException).hasCauseThat().isEqualTo(stubThrowable);
    }

    @Test
    void nestedCfs_cf_thenApplyToCfOfCf_thenApplyToCfByJoin_thenApplyToCfofCf_thenApplyToCfByGet() {
        String input = "input";
        CompletableFuture<String> cf = CompletableFuture
                .completedFuture(input)
                .thenApply(in -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return input + " 1) thenApply #1";
                })
                .thenApply(in -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return CompletableFuture.completedFuture(in + " 2) thenApply #2");
                })
                .thenApply(inCf -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return inCf.join() + " 3) thenApply #3";
                })
                .thenApply(in -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return CompletableFuture.completedFuture(in + " 4) thenApply #4");
                })
                .thenApply(inCf -> {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        return inCf.get() + " 5) thenApply #5";
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return CompletableFuture.<String>failedFuture(e).join();
                    } catch (ExecutionException e) {
                        return CompletableFuture.<String>failedFuture(e).join();
                    }
                });

        assertDoesNotThrow(() -> assertThat(cf.get()))
                .isEqualTo("input 1) thenApply #1 2) thenApply #2 3) thenApply #3 4) thenApply #4 5) thenApply #5");
    }

    @Test
    void completeOnTimeout_noComplete_completesAfterTimeout() {
        CompletableFuture<String> cf = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);

        assertDoesNotThrow(() -> assertThat(cf.get())).isEqualTo("after_timeout");
    }

    @Test
    void completeOnTimeout_immediateCompleteAsync_completesBeforeTimeout() {
        CompletableFuture<String> cf = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS)
                .completeAsync(() -> "before_timeout");

        assertDoesNotThrow(() -> assertThat(cf.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_immediateComplete_completesBeforeTimeout() {
        CompletableFuture<String> cf = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        cf.complete("before_timeout");

        assertDoesNotThrow(() -> assertThat(cf.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_delayedCompleteAfterTimeout_completesAfterTimeout() {
        CompletableFuture<String> cf = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS)
                .completeAsync(() -> {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "before_timeout";
                });

        assertDoesNotThrow(() -> assertThat(cf.get()).isEqualTo("after_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_1stCfGetThen2ndIsCreated_completesAfterTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);

        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("after_timeout"));

        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);

        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("after_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_1stCfSleepTimeoutDurationThen2ndIsCreated_completesAfterTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 100L, MILLISECONDS);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("after_timeout"));
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("after_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_2ndCreateImmediatelyAfter1st_1stGet_completesBeforeTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_2ndCreateImmediatelyAfter1st_2ndGet_completesBeforeTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_2ndCreatedImmediatelyAfter1st_1stGetThenSleepThen2ndGet_completesBeforeTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("before_timeout"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_2ndCreatedImmediatelyAfter1st_2ndGetThenSleepThen1stGet_completesBeforeTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("before_timeout"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeOnTimeout_2Cfs_2ndCreatedImmediatelyAfter1st_2GetsThenSleep_completesBeforeTimeout() {
        CompletableFuture<String> cfCompletedOnTimeout = new CompletableFuture<String>()
                .completeOnTimeout("after_timeout", 1000L, MILLISECONDS);
        CompletableFuture<String> cfCompleted = cfCompletedOnTimeout.completeAsync(() -> "before_timeout");

        assertThat(cfCompletedOnTimeout).isEqualTo(cfCompleted);
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("before_timeout"));
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("before_timeout"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> assertThat(cfCompleted.get()).isEqualTo("before_timeout"));
        assertDoesNotThrow(() -> assertThat(cfCompletedOnTimeout.get()).isEqualTo("before_timeout"));
    }

    @Test
    void completeExceptionally() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        Throwable stubThrowable = new Throwable();
        cf.completeExceptionally(stubThrowable);

        ExecutionException cfTimeoutGetException = assertThrows(ExecutionException.class, () -> cf.get());
        assertThat(cfTimeoutGetException).hasCauseThat().isEqualTo(stubThrowable);

        CompletableFuture<String> cfTimeoutHandle = cf.handleAsync((string, throwable) -> {
            assertThat(string).isNull();
            assertThat(throwable).isEqualTo(stubThrowable);
            return throwable.toString();
        });
        assertDoesNotThrow(() -> assertThat(cfTimeoutHandle.get()).isEqualTo(stubThrowable.toString()));
    }

    @Test
    void orTimeout_delayerExecutor_handleThrowableIsTimeoutException() {
        Thread waitThread = new Thread(() -> {
            Thread privWaitThread = Thread.currentThread();
            synchronized (privWaitThread) {
                try {
                    privWaitThread.wait();
                } catch (InterruptedException e) {
                    privWaitThread.interrupt();
                    throw new RuntimeException(e);
                }
            }
        }, "waitThread");
        waitThread.start();
        CompletableFuture<String> cfLengthyOrTimeout = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    return "not reachable";
                })
                .orTimeout(100, MILLISECONDS);
        TimeoutException getWithTimeoutException1 = assertThrows(TimeoutException.class, () -> cfLengthyOrTimeout.get(1, TimeUnit.NANOSECONDS));
        assertThat(getWithTimeoutException1).hasCauseThat().isNull();
        assertThat(getWithTimeoutException1).hasMessageThat().isNull();

        ExecutionException getWithTimeoutException2 = assertThrows(ExecutionException.class, () -> cfLengthyOrTimeout.get(100, MILLISECONDS));
        assertThat(getWithTimeoutException2).hasCauseThat().isInstanceOf(TimeoutException.class);
        assertThat(getWithTimeoutException2).hasCauseThat().hasMessageThat().isNull();
        assertThat(getWithTimeoutException2).hasMessageThat().isEqualTo(new TimeoutException().toString());

        ExecutionException getException = assertThrows(ExecutionException.class, cfLengthyOrTimeout::get);
        assertThat(getException).hasCauseThat().isInstanceOf(TimeoutException.class);
        assertThat(getException).hasCauseThat().hasMessageThat().isNull();
        assertThat(getException).hasMessageThat().isEqualTo(new TimeoutException().toString());

        CompletionException joinException = assertThrows(CompletionException.class, cfLengthyOrTimeout::join);
        assertThat(joinException).hasCauseThat().isInstanceOf(TimeoutException.class);
        assertThat(joinException).hasCauseThat().hasMessageThat().isNull();
        assertThat(joinException).hasMessageThat().isEqualTo(new TimeoutException().toString());

        CompletableFuture<String> cfHandle = cfLengthyOrTimeout.handleAsync((string, throwable) -> {
            assertThat(string).isNull();
            assertThat(throwable).isInstanceOf(TimeoutException.class);
            assertThat(throwable).hasCauseThat().isNull();
            assertThat(throwable).hasMessageThat().isNull();
            return throwable.toString();
        });
        assertThat(cfHandle.join()).isEqualTo(new TimeoutException().toString());

        waitThread.interrupt();
    }

    @Test
    void orTimeout_myImplementation_chosenExecutor_handleThrowableIsCompletionExceptionWrappedAroundTimeoutException() {
        Thread waitThread = new Thread(() -> {
            Thread privWaitThread = Thread.currentThread();
            synchronized (privWaitThread) {
                try {
                    privWaitThread.wait();
                } catch (InterruptedException e) {
                    privWaitThread.interrupt();
                    throw new RuntimeException(e);
                }
            }
        }, "waitThread");
        waitThread.start();
        CompletableFuture<String> cfLengthy = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    return "not reachable";
                });

        TimeoutException cfLengthyGetWithTimeoutException = assertThrows(TimeoutException.class, () -> cfLengthy.get(1, TimeUnit.NANOSECONDS));
        assertThat(cfLengthyGetWithTimeoutException).hasCauseThat().isNull();
        assertThat(cfLengthyGetWithTimeoutException).hasMessageThat().isNull();

        SingleThreadExecutor singleThreadExecutor = new SingleThreadExecutor();
        CompletableFuture<String> cfTimeout = new CompletableFuture<>();
        long timeout = 100;
        TimeUnit timeUnit = MILLISECONDS;
        Executor timeoutExecutor = singleThreadExecutor;
        Executor delayedExecutor = CompletableFuture.delayedExecutor(timeout, timeUnit, timeoutExecutor);
        delayedExecutor.execute(() -> cfTimeout.completeExceptionally(new TimeoutException()));

        ExecutionException cfTimeoutGetException = assertThrows(ExecutionException.class, cfTimeout::get);
        assertThat(cfTimeoutGetException).hasCauseThat().isInstanceOf(TimeoutException.class);
        assertThat(cfTimeoutGetException).hasCauseThat().hasCauseThat().isNull();
        assertThat(cfTimeoutGetException).hasCauseThat().hasMessageThat().isNull();
        assertThat(cfTimeoutGetException).hasMessageThat().isEqualTo(new TimeoutException().toString());

        CompletableFuture<String> cfTimeoutHandle = cfTimeout.handleAsync((string, throwable) -> {
            assertThat(string).isNull();
            assertThat(throwable).isInstanceOf(TimeoutException.class);
            assertThat(throwable).hasCauseThat().isNull();
            assertThat(throwable).hasMessageThat().isNull();
            return throwable.toString();
        });
        assertThat(cfTimeoutHandle.join()).isEqualTo(new TimeoutException().toString());

        CompletableFuture<String> cfTimeoutApplyHandle = cfTimeout
                .thenApplyAsync(in -> in)
                .handle((string, throwable) -> {
                    assertThat(string).isNull();
                    assertThat(throwable).isInstanceOf(CompletionException.class);
                    assertThat(throwable).hasCauseThat().isInstanceOf(TimeoutException.class);
                    assertThat(throwable).hasCauseThat().hasCauseThat().isNull();
                    assertThat(throwable).hasCauseThat().hasMessageThat().isNull();
                    assertThat(throwable).hasMessageThat().isEqualTo(new TimeoutException().toString());
                    return throwable.toString();
                });
        assertThat(cfTimeoutApplyHandle.join()).isEqualTo(new CompletionException(new TimeoutException()).toString());

        CompletableFuture<String> cfLengthyOrTimeout = cfLengthy.applyToEitherAsync(cfTimeout, in -> in);

        ExecutionException cfLengthyOrTimeoutGetException = assertThrows(ExecutionException.class, cfLengthyOrTimeout::get);
        assertThat(cfLengthyOrTimeoutGetException).hasCauseThat().isInstanceOf(TimeoutException.class);
        assertThat(cfLengthyOrTimeoutGetException).hasCauseThat().hasCauseThat().isNull();
        assertThat(cfLengthyOrTimeoutGetException).hasCauseThat().hasMessageThat().isNull();
        assertThat(cfLengthyOrTimeoutGetException).hasMessageThat().isEqualTo(new TimeoutException().toString());

        CompletableFuture<String> cfHandle = cfLengthyOrTimeout.handle((string, throwable) -> {
            assertThat(string).isNull();
            assertThat(throwable).isInstanceOf(CompletionException.class);
            assertThat(throwable).hasCauseThat().isInstanceOf(TimeoutException.class);
            assertThat(throwable).hasCauseThat().hasCauseThat().isNull();
            assertThat(throwable).hasCauseThat().hasMessageThat().isNull();
            assertThat(throwable).hasMessageThat().isEqualTo(new TimeoutException().toString());
            return throwable.toString();
        });
        assertThat(cfHandle.join()).isEqualTo(new CompletionException(new TimeoutException()).toString());

        waitThread.interrupt();
    }


    @Test
    void orTimeout_myImplementation_chosenExecutor_handleThrowableIsTimeoutException() {
        Thread waitThread = new Thread(() -> {
            Thread privWaitThread = Thread.currentThread();
            synchronized (privWaitThread) {
                try {
                    privWaitThread.wait();
                } catch (InterruptedException e) {
                    privWaitThread.interrupt();
                    throw new RuntimeException(e);
                }
            }
        }, "waitThread");
        waitThread.start();
        CompletableFuture<String> cfLengthy = new CompletableFuture<String>()
                .completeAsync(() -> {
                    try {
                        waitThread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    return "not reachable";
                });

        long timeout = 100;
        TimeUnit timeUnit = MILLISECONDS;
        SingleThreadExecutor timeoutExecutor = new SingleThreadExecutor();
        Executor delayedExecutor = CompletableFuture.delayedExecutor(timeout, timeUnit, timeoutExecutor);
        delayedExecutor.execute(() -> cfLengthy.completeExceptionally(new TimeoutException()));

        CompletableFuture<String> cfLengthyHandle = cfLengthy.handleAsync((string, throwable) -> {
            assertThat(string).isNull();
            assertThat(throwable).isInstanceOf(TimeoutException.class);
            assertThat(throwable).hasCauseThat().isNull();
            assertThat(throwable).hasMessageThat().isNull();
            return throwable.toString();
        });
        assertThat(cfLengthyHandle.join()).isEqualTo(new TimeoutException().toString());
    }

    private static class SingleThreadExecutor implements Executor {
        private final SingleThread singleThread = new SingleThread();

        @Override
        public void execute(Runnable runnable) {
            singleThread.run(runnable);
        }

        @Getter
        private static class SingleThread extends Thread {
            private static final AtomicInteger instanceCounter = new AtomicInteger(0);
            @Setter
            private Runnable runnable;

            public SingleThread() {
                super(SingleThread.class.getSimpleName() + "-" + instanceCounter.getAndIncrement());
            }

            @Override
            public void run() {
                runnable.run();
            }

            public void run(Runnable runnable) {
                setRunnable(runnable);
                run();
            }
        }
    }

    private static class WaitThread extends Thread {
        public WaitThread(String methodName) {
            super(WaitThread.class.getSimpleName() + " for " + methodName);
        }

        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void keepNotifyingUntilNotified() {
            while (getState() != State.TERMINATED) {
                synchronized (this) {
                    notify();
                }
            }
        }
    }

    private static class ThreadPerTaskExecutor implements Executor {
        private Thread thread;

        @Override
        public void execute(Runnable runnable) {
            Objects.requireNonNull(runnable);
            thread = new Thread(runnable);
            thread.start();
        }
    }
}
