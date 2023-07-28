package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
class ReentrantLockDemoTestCaseData {

    private final String displayName;
    private final Inputs inputs;
    private final Expecteds expecteds;

    @ToString
    @Getter
    @AllArgsConstructor
    static class Inputs {
        private final boolean isInterruptibly;
        private final LogsCopyOnAdd logs;
        private final ReentrantLockContainer reentrantLockContainer;
        private final ReentrantLockAccessor reentrantLockAccessor;
        private final ReentrantLockNotifier reentrantLockNotifier;
        private final Long sleepDurationMillis;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    static class Expecteds {
        private final LogsCopyOnAdd logs;
        private final List<Throwable> uncaughtExceptions;
    }
}
