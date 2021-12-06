package utils;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtils {

    private final TestColumnStringUtils testColumnStringUtils;

    TestUtils(TestColumnStringUtils testColumnStringUtils) {
        this.testColumnStringUtils = testColumnStringUtils;
    }

    void assertEqualsWithMessage(String expectedVariableName, String expectedVariableValue,
                                 String actualVariableName, String actualVariableValue) {
        String length = ".length()";
        assertEquals(
                expectedVariableValue,
                actualVariableValue,
                testColumnStringUtils.getColumnedMessage(
                        new TestColumnStringUtils.VarPair(
                                new StringBuilder().append(expectedVariableName).append(length).toString(),
                                expectedVariableValue.length()
                        ),
                        new TestColumnStringUtils.VarPair(
                                new StringBuilder().append(actualVariableName).append(length).toString(),
                                actualVariableValue.length()
                        ),
                        new TestColumnStringUtils.VarPair(expectedVariableName, expectedVariableValue),
                        new TestColumnStringUtils.VarPair(actualVariableName, actualVariableValue)
                )
        );
    }

    void assertEqualsWithMessage(String expectedVariableName, String expectedVariableValue,
                                 String actualVariableName, String[] actualVariableValues) {
        IntStream.range(0, actualVariableValues.length).forEach(i ->
                assertEqualsWithMessage(
                        String.format("%s[%d]", expectedVariableName, i), expectedVariableValue,
                        String.format("%s[%d]", actualVariableName, i), actualVariableValues[i]
                )
        );
    }

    void assertEqualsWithMessage(String expectedVariableName, String[] expectedVariableValues,
                                 String actualVariableName, String[] actualVariableValues) {
        if (expectedVariableValues.length != actualVariableValues.length) {
            throw new RuntimeException(
                    String.format(
                            "Unequal lengths for %s: %d, %s: %d",
                            expectedVariableName,
                            expectedVariableValues.length,
                            actualVariableName,
                            actualVariableValues.length
                    )
            );
        }
        IntStream.range(0, actualVariableValues.length).forEach(i ->
                assertEqualsWithMessage(
                        String.format("%s[%d]", expectedVariableName, i), expectedVariableValues[i],
                        String.format("%s[%d]", actualVariableName, i), actualVariableValues[i]
                )
        );
    }
}
