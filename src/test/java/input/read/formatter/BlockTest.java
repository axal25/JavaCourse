package input.read.formatter;

import org.junit.jupiter.api.*;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for Block")
public class BlockTest {

    @Test
    @Order(1)
    public void constructor() {
        Block block = new Block();
        assertEquals(block.getContents().toString(), StringUtils.EMPTY);
        assertFalse(block.isNlMissingAtTheEnd());
        assertEquals(block.getLines(), 1);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendStaticInput {

        @Test
        @Order(1)
        public void appendStaticLFLFLF() {
            assertStaticInputs(StringUtils.LF, StringUtils.LF, StringUtils.LF);
        }

        @Test
        @Order(2)
        public void appendStaticCRCRCR() {
            assertStaticInputs(StringUtils.CR, StringUtils.CR, StringUtils.CR);
        }

        @Test
        @Order(3)
        public void appendStaticNLNLNL() {
            assertStaticInputs(StringUtils.NL, StringUtils.NL, StringUtils.NL);
        }

        @Test
        @Order(4)
        public void appendStaticLFNLNL() {
            assertStaticInputs(StringUtils.LF, StringUtils.NL, StringUtils.NL);
        }

        @Test
        @Order(5)
        public void appendStaticLFLFNL() {
            assertStaticInputs(StringUtils.LF, StringUtils.LF, StringUtils.NL);
        }

        @Test
        @Order(6)
        public void appendStaticLFCRCR() {
            assertStaticInputs(StringUtils.LF, StringUtils.CR, StringUtils.CR);
        }

        @Test
        @Order(7)
        public void appendStaticLFLFCR() {
            assertStaticInputs(StringUtils.LF, StringUtils.LF, StringUtils.CR);
        }

        @Test
        @Order(8)
        public void appendStaticCRCRNL() {
            assertStaticInputs(StringUtils.CR, StringUtils.CR, StringUtils.NL);
        }

        @Test
        @Order(9)
        public void appendStaticCRNLNL() {
            assertStaticInputs(StringUtils.CR, StringUtils.NL, StringUtils.NL);
        }

        @Test
        @Order(10)
        public void appendStaticNLLFLF() {
            assertStaticInputs(StringUtils.NL, StringUtils.LF, StringUtils.LF);
        }

        @Test
        @Order(11)
        public void appendStaticNLNLLF() {
            assertStaticInputs(StringUtils.NL, StringUtils.NL, StringUtils.LF);
        }

        @Test
        @Order(12)
        public void appendStaticNLCRCR() {
            assertStaticInputs(StringUtils.NL, StringUtils.CR, StringUtils.CR);
        }

        @Test
        @Order(13)
        public void appendStaticNLLFCR() {
            assertStaticInputs(StringUtils.NL, StringUtils.LF, StringUtils.CR);
        }

        @Test
        @Order(14)
        public void appendStaticNLCRLF() {
            assertStaticInputs(StringUtils.NL, StringUtils.CR, StringUtils.LF);
        }

        private void assertStaticInputs(String inputNL1, String inputNL2, String inputNL3) {
            Block block = new Block();
            int expectedLines = 1;
            boolean isInputNL1and2SingleNL = StringUtils.NL.equals(String.format("%s%s", inputNL1, inputNL2));

            block.append("");
            assertEquals(StringUtils.EMPTY, block.getContents().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(" ");
            expectedLines++;
            assertEquals(String.format(" %s", StringUtils.NL), block.getContents().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(" ");
            assertEquals(String.format("  %s", StringUtils.NL), block.getContents().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append("a");
            assertEquals(String.format("  a%s", StringUtils.NL), block.getContents().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(" b ");
            assertEquals(String.format("  a b %s", StringUtils.NL), block.getContents().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(inputNL1);
            assertEquals(String.format("  a b %s", inputNL1), block.getContents().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(inputNL2);
            if (!isInputNL1and2SingleNL) {
                expectedLines++;
            }

            assertEquals(String.format("  a b %s%s", inputNL1, inputNL2), block.getContents().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(" c ");
            expectedLines++;
            assertEquals(String.format("  a b %s%s c %s", inputNL1, inputNL2, StringUtils.NL), block.getContents().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(inputNL3);
            assertEquals(String.format("  a b %s%s c %s", inputNL1, inputNL2, inputNL3), block.getContents().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());

            block.append(String.format(" d %s", StringUtils.NL));
            expectedLines++;
            assertEquals(String.format("  a b %s%s c %s d %s", inputNL1, inputNL2, inputNL3, StringUtils.NL), block.getContents().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());
            
            String repeatedAppendButAtOnce = block.getContents().toString();
            block.append(repeatedAppendButAtOnce);
            assertEquals(
                    String.format("%s%s", repeatedAppendButAtOnce, repeatedAppendButAtOnce),
                    block.getContents().toString()
            );
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines * 2 - 1, block.getLines());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendOnceVsGradually {

        @Test
        @Order(1)
        public void appendStatic1LF2CR_appendOnce() {
            assertAppendOnce(String.format("1%s2%s", StringUtils.LF, StringUtils.CR), false, 3);
        }

        @Test
        @Order(2)
        public void appendStatic1LF2CR_appendGradually() {
            assertAppendGradually(String.format("1%s2%s", StringUtils.LF, StringUtils.CR), false, 3);
        }

        @Test
        @Order(3)
        public void appendStatic1LF2NL_appendOnce() {
            assertAppendOnce(String.format("1%s2%s", StringUtils.LF, StringUtils.NL), false, 3);
        }

        @Test
        @Order(4)
        public void appendStatic1LF2NL_appendGradually() {
            assertAppendGradually(String.format("1%s2%s", StringUtils.LF, StringUtils.NL), false, 3);
        }

        @Test
        @Order(5)
        public void appendStatic1CR2LF_appendOnce() {
            assertAppendOnce(String.format("1%s2%s", StringUtils.CR, StringUtils.LF), false, 3);
        }

        @Test
        @Order(6)
        public void appendStatic1CR2LF_appendGradually() {
            assertAppendGradually(String.format("1%s2%s", StringUtils.CR, StringUtils.LF), false, 3);
        }

        private void assertAppendOnce(String expectedInput, boolean expectedIsNlMissingAtTheEnd, int expectedLines) {
            Block block = new Block().append(expectedInput);
            assertEquals(expectedInput, block.getContents().toString());
            assertEquals(expectedIsNlMissingAtTheEnd, block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());
        }

        private void assertAppendGradually(String expectedInput, boolean expectedIsNlMissingAtTheEnd, int expectedLines) {
            Block block = new Block();
            for (int i = 0; i < expectedInput.length(); i++) {
                String substring = expectedInput.substring(i, i + 1);
                if (StringUtils.LF.equals(substring)
                        && i + 1 < expectedInput.length()
                        && StringUtils.NL.equals(expectedInput.substring(i, i + 2))) {
                    substring = expectedInput.substring(i, i + 2);
                    i++;
                }
                block.append(substring);
            }
            assertEquals(expectedInput, block.getContents().toString());
            assertEquals(expectedIsNlMissingAtTheEnd, block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendInputPermutationTest {
        private final InputPermutation inputPermutation;
        private final List<Block> blocks;

        public AppendInputPermutationTest() {
            inputPermutation = new InputPermutation("");
            inputPermutation.generateChildren(1, 2);
            blocks = Collections.unmodifiableList(getBlocks(inputPermutation));
        }

        private List<Block> getBlocks(InputPermutation inputPermutation) {
            List<Block> childrenBlocks = inputPermutation.getChildren().stream()
                    .flatMap(child -> getBlocks(child).stream())
                    .collect(Collectors.toList());
            List<Block> allBlocks = new ArrayList<>();
            allBlocks.add(new Block().append(inputPermutation.getInput()));
            allBlocks.addAll(childrenBlocks);
            return allBlocks;
        }

        @Test
        @Order(1)
        public void assertBlocksFromInputPermutation() {
            assertEquals(1 + 4 + 4 * 4, blocks.size());
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Gen1 {

            @Test
            @Order(1)
            public void append1() {
                int index = 0;
                assertEquals(StringUtils.EMPTY, blocks.get(index).getContents().toString());
                assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                assertEquals(1, blocks.get(index).getLines());
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            public class Gen2 {

                @Test
                @Order(1)
                public void append2_1() {
                    int index = 1;
                    assertEquals(String.format("1%s", StringUtils.NL), blocks.get(index).getContents().toString());
                    assertTrue(blocks.get(index).isNlMissingAtTheEnd());
                    assertEquals(2, blocks.get(index).getLines());
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_1 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        int index = 2;
                        assertEquals(String.format("12%s", StringUtils.NL), blocks.get(index).getContents().toString());
                        assertTrue(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(2, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        int index = 3;
                        assertEquals(String.format("12%s", StringUtils.LF), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(2, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        int index = 4;
                        assertEquals(String.format("12%s", StringUtils.CR), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(2, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        int index = 5;
                        assertEquals(String.format("12%s", StringUtils.NL), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(2, blocks.get(index).getLines());
                    }
                }

                @Test
                @Order(2)
                public void append2_2() {
                    int index = 6;
                    assertEquals(String.format("1%s", StringUtils.LF), blocks.get(index).getContents().toString());
                    assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                    assertEquals(2, blocks.get(index).getLines());
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_2 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        int index = 7;
                        assertEquals(String.format("1%s2%s", StringUtils.LF, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertTrue(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        int index = 8;
                        assertEquals(String.format("1%s2%s", StringUtils.LF, StringUtils.LF), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        int index = 9;
                        assertEquals(String.format("1%s2%s", StringUtils.LF, StringUtils.CR), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        int index = 10;
                        assertEquals(String.format("1%s2%s", StringUtils.LF, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }
                }

                @Test
                @Order(3)
                public void append2_3() {
                    int index = 11;
                    assertEquals(String.format("1%s", StringUtils.CR), blocks.get(index).getContents().toString());
                    assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                    assertEquals(2, blocks.get(index).getLines());
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_3 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        int index = 12;
                        assertEquals(String.format("1%s2%s", StringUtils.CR, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertTrue(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        int index = 13;
                        assertEquals(String.format("1%s2%s", StringUtils.CR, StringUtils.LF), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        int index = 14;
                        assertEquals(String.format("1%s2%s", StringUtils.CR, StringUtils.CR), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        int index = 15;
                        assertEquals(String.format("1%s2%s", StringUtils.CR, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }
                }

                @Test
                @Order(4)
                public void append2_4() {
                    int index = 16;
                    assertEquals(String.format("1%s", StringUtils.NL), blocks.get(index).getContents().toString());
                    assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                    assertEquals(2, blocks.get(index).getLines());
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_4 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        int index = 17;
                        assertEquals(String.format("1%s2%s", StringUtils.NL, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertTrue(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        int index = 18;
                        assertEquals(String.format("1%s2%s", StringUtils.NL, StringUtils.LF), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        int index = 19;
                        assertEquals(String.format("1%s2%s", StringUtils.NL, StringUtils.CR), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        int index = 20;
                        assertEquals(String.format("1%s2%s", StringUtils.NL, StringUtils.NL), blocks.get(index).getContents().toString());
                        assertFalse(blocks.get(index).isNlMissingAtTheEnd());
                        assertEquals(3, blocks.get(index).getLines());
                    }
                }
            }
        }
    }
}
