package utils;

import main.Main;
import org.junit.jupiter.api.*;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("StringUtils")
public class StringUtilsTest {
    private TestUtils testUtils = new TestUtils(new TestColumnStringUtils(Main.SCREEN_WIDTH_IN_CHARS - 50, 0.2, 0.8));

    @Nested
    @DisplayName("getRepeatedUntil(String toBeRepeated, int maxLength)")
    public class GetRepeatedUntil {

        @Test
        @Order(1)
        @DisplayName("toBeRepeated.length() == 1")
        public void getRepeatedUntil1() {
            assertEquals("", StringUtils.getRepeatedUntil("0", 0));
            assertEquals("1", StringUtils.getRepeatedUntil("1", 1));
            assertEquals("22", StringUtils.getRepeatedUntil("2", 2));
            assertEquals("333", StringUtils.getRepeatedUntil("3", 3));
        }

        @Test
        @Order(2)
        @DisplayName("toBeRepeated.length() > 1")
        public void getRepeatedUntil2() {
            assertEquals("", StringUtils.getRepeatedUntil("000", 0));
            assertEquals("1", StringUtils.getRepeatedUntil("111", 1));
            assertEquals("22", StringUtils.getRepeatedUntil("22", 2));
            assertEquals("333", StringUtils.getRepeatedUntil("333", 3));
        }
    }

    @Nested
    @DisplayName("getCenteredString(String toPrint)")
    public class GetCenteredStringS1 {

        private final String expectedInput = "inputString";
        private final int expectedCircumfixLength = (Main.SCREEN_WIDTH_IN_CHARS - expectedInput.length()) / 2;
        private final String expectedCircumfix =
                StringUtils.getRepeatedUntil(StringUtils.DEFAULT_WHITE_SPACE, expectedCircumfixLength);
        private final String expectedWhole = new StringBuilder()
                .append(expectedCircumfix).append(expectedInput).append(expectedCircumfix).toString();
        private final String actualWhole = StringUtils.getCenteredString(expectedInput);

        @Test
        @Order(1)
        @DisplayName("whole")
        public void whole() {
            testUtils.assertEqualsWithMessage("expectedWhole", expectedWhole, "actualWhole", actualWhole);
        }

        @Test
        @Order(2)
        @DisplayName("input")
        public void input() {
            String actualInput = actualWhole
                    .substring(expectedCircumfixLength, expectedCircumfixLength + expectedInput.length());
            testUtils.assertEqualsWithMessage("expectedInput", expectedInput, "actualInput", actualInput);
        }

        @Test
        @Order(3)
        @DisplayName("prefix")
        public void prefix() {
            String actualPrefix = actualWhole.substring(0, expectedCircumfixLength);
            testUtils.assertEqualsWithMessage("expectedCircumfix", expectedCircumfix, "actualPrefix", actualPrefix);
        }

        @Test
        @Order(4)
        @DisplayName("suffix")
        public void suffix() {
            String actualSuffix = actualWhole.substring(expectedCircumfixLength + expectedInput.length());
            testUtils.assertEqualsWithMessage("expectedCircumfix", expectedCircumfix, "actualSuffix", actualSuffix);
        }
    }

    @Nested
    @DisplayName("getCenteredStringS3I1(String toPrint, String indent, String spacer, int maxLength)")
    public class GetCenteredStringS3I1 {

        private final String expectedInput = "inputString";
        private final String[] expectedInputIndents = new String[]{">", ">>>", ">", ">>>"};
        private final String[] expectedSpacers = new String[]{"+", "+++", "+", "+++"};
        private final int[] expectedMaxLengths = new int[]{
                expectedInput.length() + expectedInputIndents[0].length() + 2 * expectedSpacers[0].length(),
                expectedInput.length() + expectedInputIndents[1].length() + 2 * expectedSpacers[1].length(),
                expectedInput.length(),
                expectedInput.length()
        };
        private final String[] expectedFittingIndents = new String[]{">", ">>>", "", ""};
        private final String[] expectedCircumfixes = new String[]{"+", "+++", "", ""};
        private final String[] expectedWholes = IntStream.range(0, expectedCircumfixes.length)
                .mapToObj(i -> new StringBuilder()
                        .append(expectedCircumfixes[i])
                        .append(expectedFittingIndents[i])
                        .append(expectedInput)
                        .append(expectedCircumfixes[i])
                        .toString()
                ).toArray(String[]::new);
        private final String[] actualWholes = IntStream.range(
                        0, Math.min(Math.min(expectedFittingIndents.length, expectedSpacers.length), expectedMaxLengths.length))
                .mapToObj(i ->
                        StringUtils.getCenteredString(
                                expectedInput,
                                expectedFittingIndents[i],
                                expectedSpacers[i],
                                expectedMaxLengths[i]
                        )
                ).toArray(String[]::new);

        @Test
        @Order(1)
        @DisplayName("whole")
        public void whole() {
            testUtils.assertEqualsWithMessage(
                    "expectedWholes", expectedWholes,
                    "actualWholes", actualWholes
            );
        }

        @Test
        @Order(2)
        @DisplayName("indent")
        public void indent() {
            String[] actualIndents = IntStream.range(0, actualWholes.length)
                    .mapToObj(i ->
                            actualWholes[i].substring(
                                    expectedCircumfixes[i].length(),
                                    expectedCircumfixes[i].length() + expectedFittingIndents[i].length()
                            )
                    ).toArray(String[]::new);
            testUtils.assertEqualsWithMessage(
                    "expectedIndents", expectedFittingIndents,
                    "actualIndents", actualIndents
            );
        }

        @Test
        @Order(3)
        @DisplayName("input")
        public void input() {
            String[] actualInputs = IntStream.range(0, actualWholes.length)
                    .mapToObj(i ->
                            actualWholes[i].substring(
                                    expectedCircumfixes[i].length() + expectedFittingIndents[i].length(),
                                    expectedCircumfixes[i].length() + expectedFittingIndents[i].length() + expectedInput.length()
                            )
                    ).toArray(String[]::new);
            testUtils.assertEqualsWithMessage(
                    "expectedInput", expectedInput,
                    "actualInputs", actualInputs
            );
        }

        @Test
        @Order(3)
        @DisplayName("prefix")
        public void prefix() {
            String[] actualPrefixes = IntStream.range(0, actualWholes.length)
                    .mapToObj(i -> actualWholes[i].substring(0, expectedCircumfixes[i].length())).toArray(String[]::new);
            testUtils.assertEqualsWithMessage(
                    "expectedCircumfixes", expectedCircumfixes,
                    "actualPrefixes", actualPrefixes
            );
        }

        @Test
        @Order(4)
        @DisplayName("suffix")
        public void suffix() {
            String[] actualSuffixes = IntStream.range(0, actualWholes.length)
                    .mapToObj(i -> actualWholes[i].substring(expectedCircumfixes[i].length() + expectedFittingIndents[i].length() + expectedInput.length()))
                    .toArray(String[]::new);
            testUtils.assertEqualsWithMessage(
                    "expectedCircumfixes", expectedCircumfixes,
                    "actualSuffixes", actualSuffixes
            );
        }
    }
}