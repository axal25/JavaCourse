package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class InputsProviderSleep {

    static ReentrantLockDemoTestCaseData.Inputs getInputs(
            boolean isInterruptibly, long sleepDurationMillis) {

        LogsCopyOnAdd logs = new LogsCopyOnAdd();
        ReentrantLockContainer reentrantLockContainer = new ReentrantLockSleepContainer(logs, sleepDurationMillis);
        ReentrantLockAccessor reentrantLockAccessor = new ReentrantLockSleepAccessor(logs, reentrantLockContainer, isInterruptibly, sleepDurationMillis);

        return new ReentrantLockDemoTestCaseData.Inputs(
                isInterruptibly,
                logs,
                reentrantLockContainer,
                reentrantLockAccessor,
                null,
                sleepDurationMillis);
    }
}
