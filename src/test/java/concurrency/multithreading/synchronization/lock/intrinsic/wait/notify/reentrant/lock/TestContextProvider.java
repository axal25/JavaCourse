package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class TestContextProvider implements TestTemplateInvocationContextProvider {

    protected abstract List<String> getSupportedTestTemplateMethodNames();

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        Optional<Method> testMethod = context.getTestMethod();

        return testMethod.isPresent()
                && (getSupportedTestTemplateMethodNames().stream().anyMatch(supportedTestTemplateMethodName ->
                testMethod.get().toString().contains(
                        ReentrantLockDemoTest.class.getName() +
                                "." +
                                supportedTestTemplateMethodName +
                                "(" +
                                ReentrantLockDemoTestCaseData.class.getName() +
                                ")")));
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                getTestContext(true),
                getTestContext(false)
        );
    }

    private ReentrantLockDemoTestContext getTestContext(boolean isInterruptibly) {
        final ReentrantLockDemoTestCaseData.Inputs inputs = getInputs(isInterruptibly);
        return new ReentrantLockDemoTestContext(
                new ReentrantLockDemoTestCaseData(
                        "isInterruptibly = " +
                                inputs.isInterruptibly() +
                                ", wait or sleep = " +
                                ((inputs.getReentrantLockAccessor().getClass() == ReentrantLockWaitAccessor.class
                                        && inputs.getReentrantLockContainer().getClass() == ReentrantLockWaitContainer.class)
                                        ? "WAIT"
                                        : (inputs.getReentrantLockAccessor().getClass() == ReentrantLockSleepAccessor.class
                                        && inputs.getReentrantLockContainer().getClass() == ReentrantLockSleepContainer.class)
                                        ? "SLEEP"
                                        : "undefined: " +
                                        inputs.getReentrantLockAccessor().getClass() +
                                        ", " +
                                        inputs.getReentrantLockContainer().getClass()),
                        inputs,
                        getExpecteds(inputs)));
    }

    protected abstract ReentrantLockDemoTestCaseData.Inputs getInputs(
            boolean isInterruptibly);

    protected abstract ReentrantLockDemoTestCaseData.Expecteds getExpecteds(
            ReentrantLockDemoTestCaseData.Inputs inputs);
}
