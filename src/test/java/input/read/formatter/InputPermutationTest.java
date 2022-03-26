package input.read.formatter;

import org.junit.jupiter.api.*;
import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for InputPermutation")
public class InputPermutationTest {

    @Test
    @Order(1)
    public void constructor() {
        InputPermutation inputPermutation = new InputPermutation("");
        assertNotNull(inputPermutation);
        assertEquals(StringUtils.EMPTY, inputPermutation.getInput());
        assertEquals(List.of(), inputPermutation.getChildren());
        assertEquals("\"\"", inputPermutation.toString());
    }

    private void assertInputPermutationAndChildren(InputPermutation inputPermutation, String inputPermutationName,
                                                   String expectedInput, String... expectedChildrenInputs) {
        assertNotNull(inputPermutation);
        assertFalse(StringUtils.isBlank(inputPermutationName));
        assertNotNull(expectedInput);
        assertNotNull(expectedChildrenInputs);
        assertEquals(expectedInput, inputPermutation.getInput());
        assertEquals(expectedChildrenInputs.length, inputPermutation.getChildren().size());
        IntStream.range(0, Math.max(expectedChildrenInputs.length, inputPermutation.getChildren().size()))
                .forEach(i -> assertEquals(
                        String.format("\"%s\"", expectedChildrenInputs[i]),
                        inputPermutation.getChildren().get(i).toString()
                ));
        StringBuilder expectedToString = new StringBuilder()
                .append(String.format("\"%s\"", StringUtils.replaceNLsWithPrintable(expectedInput)))
                .append(expectedChildrenInputs.length == 0
                        ? StringUtils.EMPTY
                        : String.format(
                        "{%s%s%s}",
                        StringUtils.NL,
                        Arrays.stream(expectedChildrenInputs).collect(Collector.of(
                                        StringBuilder::new,
                                        (sb, expectedChildInput) -> sb
                                                .append(StringUtils.isBlank(sb) ? StringUtils.EMPTY : String.format(",%s", StringUtils.NL))
                                                .append(String.format("%s\"%s\"", StringUtils.TAB, expectedChildInput)),
                                        UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                        StringBuilder::toString
                                )
                        ),
                        StringUtils.NL
                ));
        assertEquals(expectedToString.toString(), inputPermutation.toString());
    }

    @Test
    @Order(1)
    public void generation1() {
        InputPermutation inputPermutation = new InputPermutation("");
        inputPermutation.generateChildren(1, 1);
        assertInputPermutationAndChildren(inputPermutation, "inputPermutation",
                StringUtils.EMPTY,
                "1", "1LF", "1CR", "1NL");
    }

    @Test
    @Order(2)
    public void generation2() {
        InputPermutation inputPermutation = new InputPermutation("");
        inputPermutation.generateChildren(1, 2);

        assertInputPermutationAndChildren(inputPermutation.getChildren().get(0), "inputPermutationGen20",
                "1",
                "12", "12LF", "12CR", "12NL");
        assertInputPermutationAndChildren(inputPermutation.getChildren().get(1), "inputPermutationGen21",
                String.format("1%s", StringUtils.LF),
                "1LF2", "1LF2LF", "1LF2CR", "1LF2NL");
        assertInputPermutationAndChildren(inputPermutation.getChildren().get(2), "inputPermutationGen22",
                String.format("1%s", StringUtils.CR),
                "1CR2", "1CR2LF", "1CR2CR", "1CR2NL");
        assertInputPermutationAndChildren(inputPermutation.getChildren().get(3), "inputPermutationGen23",
                String.format("1%s", StringUtils.NL),
                "1NL2", "1NL2LF", "1NL2CR", "1NL2NL");
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests 3rd generation")
    public class Generation3Test {

        private final InputPermutation inputPermutation;

        public Generation3Test() {
            inputPermutation = new InputPermutation("");
            inputPermutation.generateChildren(1, 3);
        }

        @Test
        @Order(1)
        public void generation3_gen2Index0() {
            int gen2Index = 0;
            String inputPermutationNamePrefix = String.format("inputPermutationGen2%d", gen2Index);
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(0),
                    String.format("%s%d", inputPermutationNamePrefix, 0),
                    "12",
                    "123", "123LF", "123CR", "123NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(1),
                    String.format("%s%d", inputPermutationNamePrefix, 1),
                    String.format("12%s", StringUtils.LF),
                    "12LF3", "12LF3LF", "12LF3CR", "12LF3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(2),
                    String.format("%s%d", inputPermutationNamePrefix, 2),
                    String.format("12%s", StringUtils.CR),
                    "12CR3", "12CR3LF", "12CR3CR", "12CR3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(3),
                    String.format("%s%d", inputPermutationNamePrefix, 3),
                    String.format("12%s", StringUtils.NL),
                    "12NL3", "12NL3LF", "12NL3CR", "12NL3NL");
        }

        @Test
        @Order(2)
        public void generation3_gen2Index1() {
            int gen2Index = 1;
            String inputPermutationNamePrefix = String.format("inputPermutationGen2%d", gen2Index);
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(0),
                    String.format("%s%d", inputPermutationNamePrefix, 0),
                    String.format("1%s2", StringUtils.LF),
                    "1LF23", "1LF23LF", "1LF23CR", "1LF23NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(1),
                    String.format("%s%d", inputPermutationNamePrefix, 1),
                    String.format("1%s2%s", StringUtils.LF, StringUtils.LF),
                    "1LF2LF3", "1LF2LF3LF", "1LF2LF3CR", "1LF2LF3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(2),
                    String.format("%s%d", inputPermutationNamePrefix, 2),
                    String.format("1%s2%s", StringUtils.LF, StringUtils.CR),
                    "1LF2CR3", "1LF2CR3LF", "1LF2CR3CR", "1LF2CR3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(3),
                    String.format("%s%d", inputPermutationNamePrefix, 3),
                    String.format("1%s2%s", StringUtils.LF, StringUtils.NL),
                    "1LF2NL3", "1LF2NL3LF", "1LF2NL3CR", "1LF2NL3NL");
        }

        @Test
        @Order(3)
        public void generation3_gen2Index2() {
            int gen2Index = 2;
            String inputPermutationNamePrefix = String.format("inputPermutationGen2%d", gen2Index);
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(0),
                    String.format("%s%d", inputPermutationNamePrefix, 0),
                    String.format("1%s2", StringUtils.CR),
                    "1CR23", "1CR23LF", "1CR23CR", "1CR23NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(1),
                    String.format("%s%d", inputPermutationNamePrefix, 1),
                    String.format("1%s2%s", StringUtils.CR, StringUtils.LF),
                    "1CR2LF3", "1CR2LF3LF", "1CR2LF3CR", "1CR2LF3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(2),
                    String.format("%s%d", inputPermutationNamePrefix, 2),
                    String.format("1%s2%s", StringUtils.CR, StringUtils.CR),
                    "1CR2CR3", "1CR2CR3LF", "1CR2CR3CR", "1CR2CR3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(3),
                    String.format("%s%d", inputPermutationNamePrefix, 3),
                    String.format("1%s2%s", StringUtils.CR, StringUtils.NL),
                    "1CR2NL3", "1CR2NL3LF", "1CR2NL3CR", "1CR2NL3NL");
        }

        @Test
        @Order(4)
        public void generation3_gen2Index3() {
            int gen2Index = 3;
            String inputPermutationNamePrefix = String.format("inputPermutationGen2%d", gen2Index);
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(0),
                    String.format("%s%d", inputPermutationNamePrefix, 0),
                    String.format("1%s2", StringUtils.NL),
                    "1NL23", "1NL23LF", "1NL23CR", "1NL23NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(1),
                    String.format("%s%d", inputPermutationNamePrefix, 1),
                    String.format("1%s2%s", StringUtils.NL, StringUtils.LF),
                    "1NL2LF3", "1NL2LF3LF", "1NL2LF3CR", "1NL2LF3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(2),
                    String.format("%s%d", inputPermutationNamePrefix, 2),
                    String.format("1%s2%s", StringUtils.NL, StringUtils.CR),
                    "1NL2CR3", "1NL2CR3LF", "1NL2CR3CR", "1NL2CR3NL");
            assertInputPermutationAndChildren(inputPermutation.getChildren().get(gen2Index).getChildren().get(3),
                    String.format("%s%d", inputPermutationNamePrefix, 3),
                    String.format("1%s2%s", StringUtils.NL, StringUtils.NL),
                    "1NL2NL3", "1NL2NL3LF", "1NL2NL3CR", "1NL2NL3NL");
        }
    }
}
