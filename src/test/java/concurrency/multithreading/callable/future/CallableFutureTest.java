package concurrency.multithreading.callable.future;

import com.google.common.truth.Correspondence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CallableFutureTest {

    @Test
    void runnable_doesNotReturnAnything_doesNotThrowCheckedExceptions() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        assertDoesNotThrow(runnable::run);
    }

    @Test
    void callableReturnsValue_callReturnsExpectedValue() throws Exception {
        Callable<String> callableReturning = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "returns something";
            }
        };
        assertThat(callableReturning.call()).isEqualTo("returns something");
    }

    @Test
    void callableThrowsCheckedException_callThrowsExpectedException() throws Exception {
        Exception stubException = new Exception("stub exception msg");
        Callable<String> callableThrowing = new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw stubException;
            }
        };
        Exception actualException = assertThrows(Exception.class, callableThrowing::call);
        assertThat(actualException).isEqualTo(stubException);
    }

    @Test
    void callableSleeps_futureTaskGetCompletesSuccessfully() throws ExecutionException, InterruptedException {
        long callableSleepDurationMillis = 100L;
        List<String> logs = new ArrayList<>();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(callableSleepDurationMillis);
                return "message from callable.";
            }
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);

        assertThat(futureTask.state()).isEqualTo(Future.State.RUNNING);
        assertThat(futureTask.isDone()).isFalse();
        assertThat(futureTask.isCancelled()).isFalse();

        IllegalStateException resultNowEx1 = assertThrows(IllegalStateException.class, futureTask::resultNow);
        assertThat(resultNowEx1).hasMessageThat().isEqualTo("Task has not completed");

        IllegalStateException exceptionNowEx1 = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(exceptionNowEx1).hasMessageThat().isEqualTo("Task has not completed");

        long startNanoTime = System.nanoTime();
        thread.start();
        logs.add("thread started");

        assertThat(futureTask.state()).isEqualTo(Future.State.RUNNING);
        assertThat(futureTask.isDone()).isFalse();
        assertThat(futureTask.isCancelled()).isFalse();

        IllegalStateException resultNowEx3 = assertThrows(IllegalStateException.class, futureTask::resultNow);
        assertThat(resultNowEx3).hasMessageThat().isEqualTo("Task has not completed");

        IllegalStateException exceptionNowEx3 = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(exceptionNowEx3).hasMessageThat().isEqualTo("Task has not completed");

        String messageFromCallable = futureTask.get();
        logs.add("received message from future task: \"" + messageFromCallable + "\".");

        long elapsedNanos = System.nanoTime() - startNanoTime;
        logs.add(elapsedNanos + " nanoseconds has elapsed while waiting for callable to finish.");

        Correspondence<String, String> correspondence = Correspondence.from(String::contains, " contains ");
        assertThat(logs)
                .comparingElementsUsing(correspondence)
                .containsExactly(
                        "thread started",
                        "received message from future task: \"message from callable.\".",
                        "nanoseconds has elapsed while waiting for callable to finish.");
        assertThat(elapsedNanos).isAtLeast(callableSleepDurationMillis * 1_000_000L);

        assertThat(futureTask.state()).isEqualTo(Future.State.SUCCESS);
        assertThat(futureTask.isDone()).isTrue();
        assertThat(futureTask.isCancelled()).isFalse();
        assertThat(futureTask.resultNow()).isEqualTo(messageFromCallable);
        IllegalStateException exceptionNowEx4 = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(exceptionNowEx4).hasMessageThat().isEqualTo("Task completed with a result");
    }

    @Test
    void callableSleeps_threadInterrupted_throwsExecExWithCauseInterruptEx() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(100L);
                return "message from callable.";
            }
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);

        assertThat(futureTask.isDone()).isFalse();
        assertThat(futureTask.isCancelled()).isFalse();
        assertThat(futureTask.state()).isEqualTo(Future.State.RUNNING);

        thread.start();
        thread.interrupt();

        assertDoesNotThrow(() -> thread.join());
        synchronized (thread) {
            assertDoesNotThrow(thread::notify);
        }

        assertThat(futureTask.isDone()).isTrue();
        assertThat(futureTask.isCancelled()).isFalse();
        assertThat(futureTask.state()).isEqualTo(Future.State.FAILED);

        ExecutionException futureTaskGetException = assertThrows(ExecutionException.class, futureTask::get);

        assertThat(futureTaskGetException).hasMessageThat().isEqualTo(InterruptedException.class.getName() + ": sleep interrupted");
        assertThat(futureTaskGetException).hasCauseThat().isInstanceOf(InterruptedException.class);
        assertThat(futureTaskGetException).hasCauseThat().hasMessageThat().isEqualTo("sleep interrupted");

        assertThat(futureTask.isDone()).isTrue();
        assertThat(futureTask.isCancelled()).isFalse();
        assertThat(futureTask.state()).isEqualTo(Future.State.FAILED);
        assertThat(futureTask.exceptionNow()).isEqualTo(futureTaskGetException.getCause());
        IllegalStateException resultNowException = assertThrows(IllegalStateException.class, futureTask::resultNow);
        assertThat(resultNowException).hasMessageThat().isEqualTo("Task completed with exception");
    }

    @Test
    void callableThrowsInterruptedException_callableCall_throwsInterruptedException() {
        InterruptedException stubException = new InterruptedException("stub exception msg");
        Callable<String> callable = () -> {
            throw stubException;
        };

        InterruptedException callableCallException = assertThrows(InterruptedException.class, callable::call);
        assertThat(callableCallException).isEqualTo(stubException);
    }

    @Test
    void callableThrowsInterruptedException_futureTaskGetThrowsExecutionException() {
        InterruptedException stubException = new InterruptedException("stub exception msg");
        Callable<String> callable = () -> {
            Thread.sleep(250L);
            throw stubException;
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);

        TimeoutException futureTaskGetWithTimeoutException =
                assertThrows(TimeoutException.class, () -> futureTask.get(1L, TimeUnit.SECONDS));
        assertThat(futureTaskGetWithTimeoutException).hasMessageThat().isNull();
        assertThat(futureTaskGetWithTimeoutException).hasCauseThat().isNull();

        thread.start();

        futureTaskGetWithTimeoutException =
                assertThrows(TimeoutException.class, () -> futureTask.get(100L, TimeUnit.MILLISECONDS));
        assertThat(futureTaskGetWithTimeoutException).hasMessageThat().isNull();
        assertThat(futureTaskGetWithTimeoutException).hasCauseThat().isNull();

        assertDoesNotThrow(() -> thread.join());

        ExecutionException futureTaskGetException = assertThrows(ExecutionException.class, futureTask::get);
        assertThat(futureTaskGetException).hasMessageThat().isEqualTo(stubException.toString());
        assertThat(futureTaskGetException).hasCauseThat().isEqualTo(stubException);
    }

    @Test
    void callableThrowsException_threadInterrupted_futureTaskThrowsInterruptedException() {
        InterruptedException stubException = new InterruptedException("stub exception msg");
        Callable<String> callable = () -> {
            Thread.sleep(1000L);
            throw stubException;
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);

        thread.start();
        thread.interrupt();
        assertDoesNotThrow(() -> thread.join());

        ExecutionException futureTaskGetException = assertThrows(ExecutionException.class, futureTask::get);
        assertThat(futureTaskGetException).hasMessageThat().isEqualTo(new InterruptedException("sleep interrupted").toString());
        assertThat(futureTaskGetException).hasCauseThat().isNotEqualTo(stubException);
        assertThat(futureTaskGetException).hasCauseThat().isInstanceOf(InterruptedException.class);
        assertThat(futureTaskGetException).hasCauseThat().hasMessageThat().isEqualTo("sleep interrupted");
    }

    @Test
    void interruptedThread_joinInnerThread_causesInterruptedException_butHasToBeHandled() {
        Thread threadInner = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadOuter = new Thread(() -> {
            threadInner.start();

            InterruptedException outerThreadJoinThreadInnerException = null;
            try {
                threadInner.join();
            } catch (InterruptedException e) {
                outerThreadJoinThreadInnerException = e;
                Thread.currentThread().interrupt();
            }
            assertThat(outerThreadJoinThreadInnerException).isNotNull();
            assertThat(outerThreadJoinThreadInnerException).hasMessageThat().isNull();
            assertThat(outerThreadJoinThreadInnerException).hasCauseThat().isNull();
            throw new RuntimeException(outerThreadJoinThreadInnerException);
        });
        threadOuter.start();
        threadOuter.interrupt();

        assertDoesNotThrow(() -> threadInner.join(), "should not be thrown even if it's first .join() from \"main\" thread");
        assertDoesNotThrow(() -> threadOuter.join());
        assertDoesNotThrow(() -> threadInner.join());
    }

    @Test
    void interruptedThread_getInnerFutureTask_causesInterruptedException_innerThreadGetsRestarted() {
        Exception notReachable = new Exception("should not be reachable");
        Callable<String> callable = () -> {
            Thread.sleep(1000L);
            throw notReachable;
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread threadInnerOfCallable = new Thread(futureTask);
        Thread threadOuter = new Thread(() -> {
            threadInnerOfCallable.start();

            InterruptedException outerThreadJoinThreadInnerException = null;
            try {
                futureTask.get();
            } catch (InterruptedException e) {
                outerThreadJoinThreadInnerException = e;
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                assertThat("not this path: " + e).isNull();
            }
            assertThat(outerThreadJoinThreadInnerException).isNotNull();
            assertThat(outerThreadJoinThreadInnerException).hasMessageThat().isNull();
            assertThat(outerThreadJoinThreadInnerException).hasCauseThat().isNull();
            throw new RuntimeException(outerThreadJoinThreadInnerException);
        });
        threadOuter.start();
        threadOuter.interrupt();

        IllegalStateException futureTaskExceptionNow = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(futureTaskExceptionNow).hasMessageThat().isEqualTo("Task has not completed");
        assertThat(futureTaskExceptionNow).hasCauseThat().isNull();

        // futureTask.get() causes inner thread containing Callable (FutureTask) to be re-started
        Exception secondFutureTaskGetException = assertThrows(Exception.class, futureTask::get);
        assertThat(futureTaskExceptionNow).isNotEqualTo(futureTask.exceptionNow());
        assertThat(secondFutureTaskGetException).hasCauseThat().isEqualTo(futureTask.exceptionNow());
        assertThat(secondFutureTaskGetException).hasMessageThat().isEqualTo(notReachable.toString());
        assertThat(secondFutureTaskGetException).hasCauseThat().isEqualTo(notReachable);
    }

    @Test
    void interruptedThread_getInnerFutureTask_causesInterruptedException_butFutureTaskWasCancelled() {
        Exception notReachable = new Exception("should not be reachable");
        Callable<String> callable = () -> {
            Thread.sleep(1000L);
            throw notReachable;
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread threadInnerOfCallable = new Thread(futureTask);
        Thread threadOuter = new Thread(() -> {
            threadInnerOfCallable.start();

            InterruptedException outerThreadJoinThreadInnerException = null;
            try {
                futureTask.get();
            } catch (InterruptedException e) {
                outerThreadJoinThreadInnerException = e;
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                assertThat("not this path: " + e).isNull();
            }
            assertThat(outerThreadJoinThreadInnerException).isNotNull();
            assertThat(outerThreadJoinThreadInnerException).hasMessageThat().isNull();
            assertThat(outerThreadJoinThreadInnerException).hasCauseThat().isNull();
            // Difference from previous test:
            futureTask.cancel(true);
            throw new RuntimeException(outerThreadJoinThreadInnerException);
        });
        threadOuter.start();
        threadOuter.interrupt();

        IllegalStateException futureTaskExceptionNow = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(futureTaskExceptionNow).hasMessageThat().isEqualTo("Task has not completed");
        assertThat(futureTaskExceptionNow).hasCauseThat().isNull();

        // Difference from previous test:
        CancellationException secondFutureTaskGetException = assertThrows(CancellationException.class, futureTask::get);
        assertThat(secondFutureTaskGetException).hasCauseThat().isNull();
        assertThat(secondFutureTaskGetException).hasMessageThat().isNull();

        IllegalStateException secondFutureTaskExceptionNow = assertThrows(IllegalStateException.class, futureTask::exceptionNow);
        assertThat(secondFutureTaskExceptionNow).hasCauseThat().isNull();
        assertThat(secondFutureTaskExceptionNow).hasMessageThat().isEqualTo("Task was cancelled");
    }

    @Test
    void interruptedCallableThread_joinOuterThread_throwsInterruptedException_mainThreadFutureTaskGet_throwsExecutionExceptionOfInterruptedException() {
        Thread threadOuter = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Callable<String> callable = () -> {
            threadOuter.start();

            InterruptedException outerThreadJoinThreadInnerException = null;
            try {
                threadOuter.join();
            } catch (InterruptedException e) {
                outerThreadJoinThreadInnerException = e;
                Thread.currentThread().interrupt();
            }

            assertThat(outerThreadJoinThreadInnerException).isNotNull();
            assertThat(outerThreadJoinThreadInnerException).hasMessageThat().isNull();
            assertThat(outerThreadJoinThreadInnerException).hasCauseThat().isNull();
            throw outerThreadJoinThreadInnerException;
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread threadOuterCallable = new Thread(futureTask);
        threadOuterCallable.start();
        threadOuterCallable.interrupt();

        ExecutionException futureTaskGetException = assertThrows(ExecutionException.class, futureTask::get);
        assertThat(futureTaskGetException).hasCauseThat().isEqualTo(futureTask.exceptionNow());
        assertThat(futureTaskGetException).hasMessageThat().isEqualTo(new InterruptedException().toString());
        assertThat(futureTaskGetException).hasCauseThat().hasCauseThat().isNull();
        assertThat(futureTaskGetException).hasCauseThat().hasMessageThat().isNull();
    }
}
