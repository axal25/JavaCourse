package concurrency.multithreading.executor.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutorServiceNewThreadPoolExecutor {

    @Test
    void keepAliveTime_graduallyIncreasesThreads_killsOffIdleThreads() {
        int corePoolSize = 15;
        int maximumPoolSize = 25;
        long keepAliveTime = 100;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        int maxQueueSize = 10;
        int maxThreadPoolExecutorCapacity = maximumPoolSize + maxQueueSize;
        BlockingQueue<Runnable> runnables = new LinkedBlockingDeque<>(maxQueueSize);
        try (ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                runnables)) {
            Callable<String> callable = () -> {
                Thread.sleep(500);
                return "callable";
            };
            List<FutureTask<String>> futureTasks =
                    IntStream.range(0, maxThreadPoolExecutorCapacity)
                            .mapToObj(unused -> new FutureTask<>(callable)).collect(Collectors.toList());

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(0);
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(0);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(0);
            assertThat(threadPoolExecutor.getQueue()).hasSize(0);

            IntStream.range(0, corePoolSize).forEach(i -> threadPoolExecutor.execute(futureTasks.get(i)));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(corePoolSize);
            assertThat(threadPoolExecutor.getQueue()).isEmpty();
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(corePoolSize);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(corePoolSize);

            IntStream.range(corePoolSize, maximumPoolSize).forEach(i -> threadPoolExecutor.execute(futureTasks.get(i)));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(corePoolSize);
            assertThat(threadPoolExecutor.getQueue()).hasSize(maxQueueSize);
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(corePoolSize);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(corePoolSize);

            IntStream.range(maximumPoolSize, maxThreadPoolExecutorCapacity)
                    .forEach(i -> threadPoolExecutor.execute(futureTasks.get(i)));

            FutureTask<String> runnableOneTooMuch = new FutureTask<>(callable);
            RejectedExecutionException exceedMaxThreadPoolExecutorCapacityException =
                    assertThrows(RejectedExecutionException.class, () ->
                            threadPoolExecutor.execute(runnableOneTooMuch));
            assertThat(exceedMaxThreadPoolExecutorCapacityException).hasMessageThat()
                    .isEqualTo("Task " + runnableOneTooMuch + " rejected from " + threadPoolExecutor);
            assertThat(exceedMaxThreadPoolExecutorCapacityException).hasCauseThat().isNull();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(maximumPoolSize);
            assertThat(threadPoolExecutor.getQueue()).hasSize(maxQueueSize);
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(maximumPoolSize);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(maximumPoolSize);

            futureTasks.forEach(futureTask -> assertDoesNotThrow(() -> futureTask.get()));

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(maximumPoolSize);
            assertThat(threadPoolExecutor.getQueue()).isEmpty();
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(0);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(corePoolSize);

            try {
                Thread.sleep(keepAliveTime + 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertThat(threadPoolExecutor.getLargestPoolSize()).isEqualTo(maximumPoolSize);
            assertThat(threadPoolExecutor.getQueue()).isEmpty();
            assertThat(threadPoolExecutor.getActiveCount()).isEqualTo(0);
            assertThat(threadPoolExecutor.getPoolSize()).isEqualTo(corePoolSize);
        }
    }
}
