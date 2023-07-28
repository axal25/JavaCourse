package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsCopyOnAdd;

import java.util.List;

import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.ReentrantLockDemoTest.SLEEP_DURATION_MILLIS;
import static concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.StartOrFinish.START;

public class TestContextProviderSleepInterruptBeforeLocking extends TestContextProvider {

    @Override
    protected List<String> getSupportedTestTemplateMethodNames() {
        return List.of("interruptBeforeLockingSleep");
    }

    @Override
    protected ReentrantLockDemoTestCaseData.Inputs getInputs(boolean isInterruptibly) {
        return InputsProviderSleep.getInputs(isInterruptibly, SLEEP_DURATION_MILLIS);
    }

    @Override
    protected ReentrantLockDemoTestCaseData.Expecteds getExpecteds(
            ReentrantLockDemoTestCaseData.Inputs inputs) {

        InterruptedException thrownException = new InterruptedException();
        LogsCopyOnAdd logs = LogsCopyOnAdd.of(
                ReentrantLockAccessor.getLog(inputs.getReentrantLockAccessor(), inputs.isInterruptibly(), START),
                ReentrantLockAccessor.getLogExceptionWaitOrSleep(thrownException));
        RuntimeException rethrownException = new RuntimeException(thrownException);
        List<Throwable> uncaughtExceptions = List.of(rethrownException);

        return new ReentrantLockDemoTestCaseData.Expecteds(
                logs,
                uncaughtExceptions
        );
    }
}
