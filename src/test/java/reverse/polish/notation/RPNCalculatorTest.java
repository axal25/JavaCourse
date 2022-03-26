package reverse.polish.notation;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test RPNCalculator")
public class RPNCalculatorTest {

    private void assertRpnInputExpectedActualResultsTuplesEntry(final String rpnInput, final Integer expectedResult, final int testIndex, final int testClassIndex) {
        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, expectedResult, testIndex + testClassIndex);
    }

    private void assertRpnInputExpectedActualResultsTuplesEntry(final String rpnInput, final Integer expectedResult, final int tuplesIndex) {
        assertEquals(expectedResult, new RPNCalculator(rpnInput).getResult());
        assertEquals(
                rpnInput,
                ReversePolishNotationAkaPostfixNotationDemo.getRpnInputExpectedActualResultsTuples().get(tuplesIndex)
                        .getInput()
        );
        assertEquals(
                expectedResult,
                ReversePolishNotationAkaPostfixNotationDemo.getRpnInputExpectedActualResultsTuples().get(tuplesIndex)
                        .getExpected()
        );
        assertEquals(
                expectedResult,
                ReversePolishNotationAkaPostfixNotationDemo.getRpnInputExpectedActualResultsTuples().get(tuplesIndex)
                        .getActual()
        );
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Calculator Tests")
    public class CalculateTest {

        @BeforeAll
        public void beforeAll() {
        }

        @AfterAll
        public void afterAll() {
        }

        @BeforeEach
        void beforeEach() {
        }

        @AfterEach
        void afterEach() {
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example1Test {
            @Test
            @Order(0)
            public void calculateTest1() {
                final String rpnInput = "2";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 2, 0, 0);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example2Test {
            @Test
            @Order(1)
            public void calculateTest1() {
                final String rpnInput = "3 2 +";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 + 2, 1, 1);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 5, 1, 1);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example3Test {
            @Test
            @Order(2)
            public void calculateTest1() {
                final String rpnInput = "3 4 +";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 + 4, 2, 2);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 7, 2, 2);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example4Test {
            @Test
            @Order(3)
            public void calculateTest1() {
                final String rpnInput = "12 4 /";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 12 / 4, 3, 3);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3, 3, 3);
            }

            @Test
            @Order(4)
            public void calculateTest2() {
                final String rpnInput = "3 1 -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 - 1, 4, 3);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 2, 4, 3);
            }

            @Test
            @Order(5)
            public void calculateTest3() {
                final String rpnInput = "12 4 / 1 -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 12 / 4 - 1, 5, 3);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 2, 5, 3);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example5Test {
            @Test
            @Order(6)
            public void calculateTest1() {
                final String rpnInput = "4 1 -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4 - 1, 6, 4);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3, 6, 4);
            }

            @Test
            @Order(7)
            public void calculateTest2() {
                final String rpnInput = "12 3 /";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 12 / 3, 7, 4);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 7, 4);
            }

            @Test
            @Order(8)
            public void calculateTest3() {
                final String rpnInput = "12 4 1 - /";
                assertEquals(12 / (4 - 1), new RPNCalculator(rpnInput).getResult());
                assertEquals(4, new RPNCalculator(rpnInput).getResult());
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 12 / (4 - 1), 8, 4);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 8, 4);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class Example6Test {
            @Test
            @Order(9)
            public void calculateTest1() {
                final String rpnInput = "3 4 -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 - 4, 9, 5);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, -1, 9, 5);
            }

            @Test
            @Order(10)
            public void calculateTest2() {
                final String rpnInput = "-1 5 +";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, -1 + 5, 10, 5);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 10, 5);
            }

            @Test
            @Order(11)
            public void calculateTest3() {
                final String rpnInput = "3 4 - 5 +";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 - 4 + 5, 11, 5);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 11, 5);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class ExampleTest7 {
            @Test
            @Order(12)
            public void calculateTest1() {
                final String rpnInput = "4 5 +";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4 + 5, 12, 6);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 9, 12, 6);
            }

            @Test
            @Order(13)
            public void calculateTest2() {
                final String rpnInput = "3 9 -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 - 9, 13, 6);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, -6, 13, 6);
            }

            @Test
            @Order(14)
            public void calculateTest3() {
                final String rpnInput = "3 4 5 + -";
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3 - (4 + 5), 14, 6);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, -6, 14, 6);
            }
        }

        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class ExampleTest8 {

            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            public class ExampleTest81 {

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class ExampleTest811 {

                    @Nested
                    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                    public class ExampleTest8111 {

                        @Nested
                        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                        public class ExampleTest81111 {
                            @Test
                            @Order(15)
                            public void calculateTest1() {
                                final String rpnInput = "1 1 +";
                                assertEquals(1 + 1, new RPNCalculator(rpnInput).getResult());
                                assertEquals(2, new RPNCalculator(rpnInput).getResult());
                                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 1 + 1, 15, 7);
                                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 2, 15, 7);
                            }
                        }

                        @Test
                        @Order(16)
                        public void calculateTest1() {
                            final String rpnInput = "7 2 -";
                            assertEquals((7 - (2)), new RPNCalculator(rpnInput).getResult());
                            assertEquals(5, new RPNCalculator(rpnInput).getResult());
                            assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (7 - (2)), 16, 8);
                            assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 5, 16, 8);
                        }

                        @Test
                        @Order(17)
                        public void calculateTest2() {
                            final String rpnInput = "7 1 1 + -";
                            assertEquals((7 - (1 + 1)), new RPNCalculator(rpnInput).getResult());
                            assertEquals(5, new RPNCalculator(rpnInput).getResult());
                            assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (7 - (1 + 1)), 17, 8);
                            assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 5, 17, 8);
                        }
                    }

                    @Test
                    @Order(18)
                    public void calculateTest1() {
                        final String rpnInput = "15 5 /";
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (15 / ((5))), 18, 9);
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3, 18, 9);
                    }

                    @Test
                    @Order(19)
                    public void calculateTest2() {
                        final String rpnInput = "15 7 1 1 + - /";
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (15 / (7 - (1 + 1))), 19, 9);
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 3, 19, 9);
                    }
                }

                @Test
                @Order(20)
                public void calculateTest1() {
                    final String rpnInput = "3 3 *";
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, ((((3))) * 3), 20, 10);
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 9, 20, 10);
                }

                @Test
                @Order(21)
                public void calculateTest2() {
                    final String rpnInput = "15 7 1 1 + - / 3 *";
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, ((((3))) * 3), 21, 10);
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 9, 21, 10);
                }
            }


            @Nested
            @TestInstance(TestInstance.Lifecycle.PER_CLASS)
            public class ExampleTest82 {

                @Nested
                @TestInstance(TestInstance.Lifecycle.PER_CLASS)
                public class ExampleTest821 {

                    @Test
                    @Order(22)
                    public void calculateTest1() {
                        final String rpnInput = "1 1 +";
                        assertEquals((1 + 1), new RPNCalculator(rpnInput).getResult());
                        assertEquals(2, new RPNCalculator(rpnInput).getResult());
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (1 + 1), 22, 11);
                        assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 2, 22, 11);
                    }
                }

                @Test
                @Order(23)
                public void calculateTest1() {
                    final String rpnInput = "2 2 +";
                    assertEquals((2 + (2)), new RPNCalculator(rpnInput).getResult());
                    assertEquals(4, new RPNCalculator(rpnInput).getResult());
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (2 + (2)), 23, 12);
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 23, 12);
                }

                @Test
                @Order(24)
                public void calculateTest2() {
                    final String rpnInput = "2 1 1 + +";
                    assertEquals((2 + (1 + 1)), new RPNCalculator(rpnInput).getResult());
                    assertEquals(4, new RPNCalculator(rpnInput).getResult());
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, (2 + (1 + 1)), 24, 12);
                    assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 4, 24, 12);
                }
            }

            @Test
            @Order(25)
            public void calculateTest1() {
                final String rpnInput = "9 4 -";
                assertEquals(((((9)))) - ((4)), new RPNCalculator(rpnInput).getResult());
                assertEquals(5, new RPNCalculator(rpnInput).getResult());
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, ((((9)))) - ((4)), 25, 13);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 5, 25, 13);
            }


            @Test
            @Order(26)
            public void calculateTest2() {
                final String rpnInput = "15 7 1 1 + - / 3 * 2 1 1 + + -";
                assertEquals(((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1)), new RPNCalculator(rpnInput).getResult());
                assertEquals(5, new RPNCalculator(rpnInput).getResult());
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, ((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1)), 26, 13);
                assertRpnInputExpectedActualResultsTuplesEntry(rpnInput, 5, 26, 13);
            }
        }
    }
}
