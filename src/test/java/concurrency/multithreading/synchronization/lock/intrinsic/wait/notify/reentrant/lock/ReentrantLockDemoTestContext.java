package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.extension.*;

import java.util.List;

@AllArgsConstructor
public class ReentrantLockDemoTestContext implements TestTemplateInvocationContext {

    private final ReentrantLockDemoTestCaseData testCaseData;

    @Override
    public String getDisplayName(int invocationIndex) {
        return String.format(
                "%s %s",
                TestTemplateInvocationContext.super.getDisplayName(invocationIndex),
                testCaseData.getDisplayName()
        );
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return List.of(
                new ReentrantLockDemoParameterResolver(testCaseData),
                new BeforeTestExecutionCallback() {
                    @Override
                    public void beforeTestExecution(ExtensionContext context) {
                        System.out.println(BeforeTestExecutionCallback.class.getSimpleName() + "::beforeTestExecution - " + testCaseData);
                    }
                },
                new AfterTestExecutionCallback() {
                    @Override
                    public void afterTestExecution(ExtensionContext context) {
                        System.out.println(AfterTestExecutionCallback.class.getSimpleName() + "::afterTestExecution - " + testCaseData);
                    }
                }
        );
    }
}
