package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

import java.util.List;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.FINISH;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

public class TestContextProviderWaitInterruptAfterLocking extends TestContextProvider {

    @Override
    protected List<String> getSupportedTestTemplateMethodNames() {
        return List.of("interruptAfterLockingWait");
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
                ReentrantLockNotifier.getLog(Thread.currentThread(), inputs.getReentrantLockAccessor()),
                ReentrantLockContainer.getLogLocking(inputs.isInterruptibly(), START),
                ReentrantLockContainer.getLogLocking(inputs.isInterruptibly(), FINISH),
                ReentrantLockContainer.getLogPerfomingAction(inputs.isInterruptibly(), START),
                ReentrantLockContainer.getLogExceptionWaitOrSleep(thrownException),
                ReentrantLockContainer.getLogUnlock(),
                ReentrantLockNotifier.getLog(Thread.currentThread(), inputs.getReentrantLockAccessor()));
        RuntimeException rethrownException = new RuntimeException(thrownException);
        List<Throwable> uncaughtExceptions = List.of(rethrownException);

        return new ReentrantLockDemoTestCaseData.Expecteds(
                logs,
                uncaughtExceptions
        );
    }
}