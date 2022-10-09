package input.read.formatter.block;

import org.junit.jupiter.api.*;
import utils.StringUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for Block")
public class BlockTest {

    @Test
    @Order(1)
    public void noArgsConstructor() {
        Block block = new Block();

        assertThat(block.getContents().toString(), is(equalTo(StringUtils.EMPTY)));
        assertThat(block.getLines(), is(equalTo(1)));

        assertThat(block.isNlMissingAtTheEnd(), is(equalTo(false)));

        assertThat(block.getContentsToPrint().toString(), is(equalTo(StringUtils.EMPTY)));
        assertThat(block.getLinesToPrint(), is(equalTo(1)));
    }

    @Test
    @Order(2)
    public void noArgsConstructor_blockAndClone_areEqual() {
        Block block = new Block();
        Block clone = block.clone();

        assertThat(block.getContents().toString(), is(equalTo(StringUtils.EMPTY)));
        assertThat(clone.getContents().toString(), is(equalTo(block.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(block.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(block.isNlMissingAtTheEnd())));

        assertThat(block.getContentsToPrint().toString(), is(equalTo(StringUtils.EMPTY)));
        assertThat(clone.getContentsToPrint().toString(), is(equalTo(block.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(block.getLinesToPrint())));

        assertThat(clone, is(equalTo(block)));
    }

    @Test
    @Order(3)
    public void append_blockAndClone_areEqual() {
        String inputString = "inputString";

        Block block = new Block().append(inputString);
        Block clone = block.clone();

        assertThat(block.getContents().toString(), is(equalTo(inputString)));
        assertThat(clone.getContents().toString(), is(equalTo(block.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(block.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(block.isNlMissingAtTheEnd())));

        assertThat(block.getContentsToPrint().toString(), is(equalTo(String.format("%s%s", inputString, StringUtils.NL))));
        assertThat(clone.getContentsToPrint().toString(), is(equalTo(block.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(block.getLinesToPrint())));

        assertThat(clone, is(equalTo(block)));
    }

    @Test
    @Order(4)
    public void appendAfterClone_blockAndClone_areEqual() {
        String inputString = "inputString";

        Block block = new Block();
        Block clone = block.clone();

        block.append(inputString);
        clone.append(inputString);

        assertThat(block.getContents().toString(), is(equalTo(inputString)));
        assertThat(clone.getContents().toString(), is(equalTo(block.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(block.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(block.isNlMissingAtTheEnd())));

        assertThat(block.getContentsToPrint().toString(), is(equalTo(String.format("%s%s", inputString, StringUtils.NL))));
        assertThat(clone.getContentsToPrint().toString(), is(equalTo(block.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(block.getLinesToPrint())));

        assertThat(clone, is(equalTo(block)));
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendStaticInput {

        private void assertStaticInputs(String inputNL1, String inputNL2, String inputNL3) {
            Block block = new Block();
            int expectedLines = 1;
            int expectedLinesToPrint = 1;
            boolean isInputNL1and2SingleNL = StringUtils.NL.equals(String.format("%s%s", inputNL1, inputNL2));

            block.append("");
            assertEquals(StringUtils.EMPTY, block.getContents().toString());
            assertEquals(StringUtils.EMPTY, block.getContentsToPrint().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLines, block.getLines());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(" ");
            expectedLinesToPrint++;
            assertEquals(String.format(" %s", StringUtils.NL), block.getContentsToPrint().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(" ");
            assertEquals(String.format("  %s", StringUtils.NL), block.getContentsToPrint().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append("a");
            assertEquals(String.format("  a%s", StringUtils.NL), block.getContentsToPrint().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(" b ");
            assertEquals(String.format("  a b %s", StringUtils.NL), block.getContentsToPrint().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(inputNL1);
            assertEquals(String.format("  a b %s", inputNL1), block.getContentsToPrint().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(inputNL2);
            if (!isInputNL1and2SingleNL) {
                expectedLinesToPrint++;
            }

            assertEquals(String.format("  a b %s%s", inputNL1, inputNL2), block.getContentsToPrint().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(" c ");
            expectedLinesToPrint++;
            assertEquals(String.format("  a b %s%s c %s", inputNL1, inputNL2, StringUtils.NL), block.getContentsToPrint().toString());
            assertTrue(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(inputNL3);
            assertEquals(String.format("  a b %s%s c %s", inputNL1, inputNL2, inputNL3), block.getContentsToPrint().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            block.append(String.format(" d %s", StringUtils.NL));
            expectedLinesToPrint++;
            assertEquals(String.format("  a b %s%s c %s d %s", inputNL1, inputNL2, inputNL3, StringUtils.NL), block.getContentsToPrint().toString());
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint, block.getLinesToPrint());

            String repeatedAppendButAtOnce = block.getContentsToPrint().toString();
            block.append(repeatedAppendButAtOnce);
            assertEquals(
                    String.format("%s%s", repeatedAppendButAtOnce, repeatedAppendButAtOnce),
                    block.getContentsToPrint().toString()
            );
            assertFalse(block.isNlMissingAtTheEnd());
            assertEquals(expectedLinesToPrint * 2 - 1, block.getLines());
        }

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
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendOnceVsGradually {

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
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class AppendStaticInputTest {

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Gen1 {

            private void assertAppend(String inputContents,
                                      String expectedContentsToPrint,
                                      boolean expectedIsNlMissingAtTheEnd,
                                      int expectedLines,
                                      int expectedLinesToPrint) {
                Block block = new Block().append(inputContents);
                assertEquals(inputContents, block.getContents().toString());
                assertEquals(expectedContentsToPrint, block.getContentsToPrint().toString());
                assertEquals(expectedIsNlMissingAtTheEnd, block.isNlMissingAtTheEnd());
                assertEquals(expectedLines, block.getLines());
                assertEquals(expectedLinesToPrint, block.getLinesToPrint());
            }

            @Test
            @Order(1)
            public void append1() {
                assertAppend(StringUtils.EMPTY, StringUtils.EMPTY, false, 1, 1);
            }

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            public class Gen2 {

                @Test
                @Order(1)
                public void append2_1() {
                    assertAppend("1", String.format("1%s", StringUtils.NL), true, 1, 2);
                }

                @Test
                @Order(2)
                public void append2_2() {
                    assertAppend(String.format("1%s", StringUtils.LF), String.format("1%s", StringUtils.LF), false, 2, 2);
                }

                @Test
                @Order(3)
                public void append2_3() {
                    assertAppend(String.format("1%s", StringUtils.CR), String.format("1%s", StringUtils.CR), false, 2, 2);
                }

                @Test
                @Order(4)
                public void append2_4() {
                    assertAppend(String.format("1%s", StringUtils.NL), String.format("1%s", StringUtils.NL), false, 2, 2);
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_1 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        assertAppend("12", String.format("12%s", StringUtils.NL), true, 1, 2);
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        assertAppend(String.format("12%s", StringUtils.LF), String.format("12%s", StringUtils.LF), false, 2, 2);
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        assertAppend(String.format("12%s", StringUtils.CR), String.format("12%s", StringUtils.CR), false, 2, 2);
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        assertAppend(String.format("12%s", StringUtils.NL), String.format("12%s", StringUtils.NL), false, 2, 2);
                    }
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_2 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        assertAppend(String.format("1%s2", StringUtils.LF), String.format("1%s2%s", StringUtils.LF, StringUtils.NL), true, 2, 3);
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        assertAppend(String.format("1%s2%s", StringUtils.LF, StringUtils.LF), String.format("1%s2%s", StringUtils.LF, StringUtils.LF), false, 3, 3);
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        assertAppend(String.format("1%s2%s", StringUtils.LF, StringUtils.CR), String.format("1%s2%s", StringUtils.LF, StringUtils.CR), false, 3, 3);
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        assertAppend(String.format("1%s2%s", StringUtils.LF, StringUtils.NL), String.format("1%s2%s", StringUtils.LF, StringUtils.NL), false, 3, 3);
                    }
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_3 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        assertAppend(String.format("1%s2", StringUtils.CR), String.format("1%s2%s", StringUtils.CR, StringUtils.NL), true, 2, 3);
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        assertAppend(String.format("1%s2%s", StringUtils.CR, StringUtils.LF), String.format("1%s2%s", StringUtils.CR, StringUtils.LF), false, 3, 3);
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        assertAppend(String.format("1%s2%s", StringUtils.CR, StringUtils.CR), String.format("1%s2%s", StringUtils.CR, StringUtils.CR), false, 3, 3);
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        assertAppend(String.format("1%s2%s", StringUtils.CR, StringUtils.NL), String.format("1%s2%s", StringUtils.CR, StringUtils.NL), false, 3, 3);
                    }
                }

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class Gen3Of2_4 {

                    @Test
                    @Order(1)
                    public void append3_1() {
                        assertAppend(String.format("1%s2", StringUtils.NL), String.format("1%s2%s", StringUtils.NL, StringUtils.NL), true, 2, 3);
                    }

                    @Test
                    @Order(2)
                    public void append3_2() {
                        assertAppend(String.format("1%s2%s", StringUtils.NL, StringUtils.LF), String.format("1%s2%s", StringUtils.NL, StringUtils.LF), false, 3, 3);
                    }

                    @Test
                    @Order(3)
                    public void append3_3() {
                        assertAppend(String.format("1%s2%s", StringUtils.NL, StringUtils.CR), String.format("1%s2%s", StringUtils.NL, StringUtils.CR), false, 3, 3);
                    }

                    @Test
                    @Order(4)
                    public void append3_4() {
                        assertAppend(String.format("1%s2%s", StringUtils.NL, StringUtils.NL), String.format("1%s2%s", StringUtils.NL, StringUtils.NL), false, 3, 3);
                    }
                }
            }
        }
    }
}
