package template.test;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

public class GenerateUsernameTestContextProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return Stream.of(
                new GenerateUsernameTestContext(new GenerateUsernameTestCaseData(
                        "doAddHostname == true",
                        true,
                        "jacek",
                        "oles",
                        "joles@joles.pl"
                )),
                new GenerateUsernameTestContext(new GenerateUsernameTestCaseData(
                        "doAddHostname == false",
                        false,
                        "jacek",
                        "oles",
                        "joles"
                ))
        );
    }
}
