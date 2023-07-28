package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

class InputsProviderWait {

    static ReentrantLockDemoTestCaseData.Inputs getInputs(
            boolean isInterruptibly) {

        LogsCopyOnAdd logs = new LogsCopyOnAdd();
        ReentrantLockContainer reentrantLockContainer = new ReentrantLockWaitContainer(logs);
        ReentrantLockAccessor reentrantLockAccessor = new ReentrantLockWaitAccessor(logs, reentrantLockContainer, isInterruptibly);
        ReentrantLockNotifier reentrantLockNotifier = new ReentrantLockNotifier(logs, reentrantLockAccessor);

        return new ReentrantLockDemoTestCaseData.Inputs(
                isInterruptibly,
                logs,
                reentrantLockContainer,
                reentrantLockAccessor,
                reentrantLockNotifier,
                null);
    }
}
