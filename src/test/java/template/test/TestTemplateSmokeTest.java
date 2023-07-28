package template.test;


import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.google.common.truth.Truth.assertThat;


public class TestTemplateSmokeTest {

    @TestTemplate
    @ExtendWith(GenerateUsernameTestContextProvider.class)
    void generateUsername_correctFormat(GenerateUsernameTestCaseData testCaseData) {
        String actualUsername =
                new UsernameGenerator(testCaseData.isDoAddHostname())
                        .getGeneratedUsername(
                                testCaseData.getFirstName(),
                                testCaseData.getLastName());

        assertThat(actualUsername).isEqualTo(testCaseData.getUsername());
    }
}
