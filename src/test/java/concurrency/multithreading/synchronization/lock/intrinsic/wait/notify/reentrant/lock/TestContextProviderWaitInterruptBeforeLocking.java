package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

import java.util.List;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

public class TestContextProviderWaitInterruptBeforeLocking extends TestContextProvider {

    @Override
    protected List<String> getSupportedTestTemplateMethodNames() {
        return List.of("interruptBeforeLockingWait");
    }

    @Override
    protected ReentrantLockDemoTestCaseData.Inputs getInputs(boolean isInterruptibly) {
        return InputsProviderWait.getInputs(isInterruptibly);
    }

    @Override
    protected ReentrantLockDemoTestCaseData.Expecteds getExpecteds(
            ReentrantLockDemoTestCaseData.Inputs inputs) {

        InterruptedException thrownException = new InterruptedException();
        LogsCopyOnAdd logs = LogsCopyOnAdd.of(
                ReentrantLockAccessor.getLog(inputs.getReentrantLockAccessor(), inputs.isInterruptibly(), START),
                ReentrantLockAccessor.getLogExceptionWaitOrSleep(thrownException),
                ReentrantLockNotifier.getLog(Thread.currentThread(), inputs.getReentrantLockAccessor()),
                ReentrantLockNotifier.getLog(Thread.currentThread(), inputs.getReentrantLockAccessor()));
        RuntimeException rethrownException = new RuntimeException(thrownException);
        List<Throwable> uncaughtExceptions = List.of(rethrownException);

        return new ReentrantLockDemoTestCaseData.Expecteds(
                logs,
                uncaughtExceptions
        );
    }
}
