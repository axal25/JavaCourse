package template.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
class GenerateUsernameTestCaseData {

    private final String displayName;
    private final boolean doAddHostname;
    private final String firstName;
    private final String lastName;
    private final String username;
}
