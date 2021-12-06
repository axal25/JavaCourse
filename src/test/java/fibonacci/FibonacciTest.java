package fibonacci;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("other.Test for fibonacci.Fibonacci")
public class FibonacciTest {

    private static final int[] expFibSeq = new int[]{
            0, 1, 1, 2, 3, 5, 8, 13, 21, 34,
            55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181,
            6765
    };

    private static final int expFibSeqMax = expFibSeq[expFibSeq.length - 1];

    private static final int fibMemNumMax = 40;

    @BeforeAll
    public void beforeAll() {
        System.out.println(getClass().getSimpleName() + " - beforeAll");
    }

    @AfterAll
    public void afterAll() {
        System.out.println(getClass().getSimpleName() + " - afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println(getClass().getSimpleName() + " - beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println(getClass().getSimpleName() + " - afterEach");
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests for V1")
    public class V1Test {

        @BeforeAll
        public void beforeAll() {
            System.out.println(getClass().getSimpleName() + " - beforeAll");
        }

        @AfterAll
        public void afterAll() {
            System.out.println(getClass().getSimpleName() + " - afterAll");
        }

        @BeforeEach
        void beforeEach() {
            System.out.println(getClass().getSimpleName() + " - beforeEach");
        }

        @AfterEach
        void afterEach() {
            System.out.println(getClass().getSimpleName() + " - afterEach");
        }

        @Test
        @Order(1)
        public void getMember() {
            System.out.println(getClass().getSimpleName() + " - getMember - 1");
            for (int i = 0; i < expFibSeq.length; i++) {
                assertEquals(
                        expFibSeq[i],
                        Fibonacci.V1.getMember(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(2)
        public void getMemberGreaterOrEqualTo() {
            System.out.println(getClass().getSimpleName() + " - getMemberGreaterOrEqualTo - 2");
            for (int i = 0; i < expFibSeqMax; i++) {
                final int finalI = i;
                assertTrue(
                        Arrays.stream(expFibSeq)
                                .anyMatch(expectedFibonacciNumber -> finalI == expectedFibonacciNumber)
                                ? i == Fibonacci.V1.getMemberGreaterOrEqualTo(i)
                                : i < Fibonacci.V1.getMemberGreaterOrEqualTo(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(3)
        public void isMember() {
            System.out.println(getClass().getSimpleName() + " - isMember - 3");
            for (int i = 0; i < expFibSeqMax; i++) {
                final int finalI = i;
                assertEquals(
                        Arrays.stream(expFibSeq)
                                .anyMatch(expectedFibonacciNumber -> finalI == expectedFibonacciNumber),
                        Fibonacci.V1.isMember(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(4)
        public void getMember_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - getMember_throwsExceptions - 4");
            assertThrows(Exception.class, () -> Fibonacci.V1.getMember(-1));
        }

        @Test
        @Order(5)
        public void getMemberGreaterOrEqualTo_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - getMemberGreaterOrEqualTo_throwsExceptions - 5");
            assertThrows(Exception.class, () -> Fibonacci.V1.getMemberGreaterOrEqualTo(-1));
        }

        @Test
        @Order(6)
        public void isMember_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - isMember_throwsExceptions - 6");
            assertThrows(Exception.class, () -> Fibonacci.V1.isMember(-1));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests for V2")
    public class V2Test {

        @BeforeAll
        public void beforeAll() {
            System.out.println(getClass().getSimpleName() + " - beforeAll");
        }

        @AfterAll
        public void afterAll() {
            System.out.println(getClass().getSimpleName() + " - afterAll");
        }

        @BeforeEach
        void beforeEach() {
            System.out.println(getClass().getSimpleName() + " - beforeEach");
        }

        @AfterEach
        void afterEach() {
            System.out.println(getClass().getSimpleName() + " - afterEach");
        }

        @Test
        @Order(1)
        public void getMember() {
            System.out.println(getClass().getSimpleName() + " - getMember - 1");
            for (int i = 0; i < expFibSeq.length; i++) {
                assertEquals(
                        expFibSeq[i],
                        Fibonacci.V2.getMember(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(2)
        public void getMemberGreaterOrEqualTo() {
            System.out.println(getClass().getSimpleName() + " - getMemberGreaterOrEqualTo - 2");
            for (int i = 0; i < expFibSeqMax; i++) {
                final int finalI = i;
                assertTrue(
                        Arrays.stream(expFibSeq)
                                .anyMatch(expectedFibonacciNumber -> finalI == expectedFibonacciNumber)
                                ? i == Fibonacci.V2.getMemberGreaterOrEqualTo(i)
                                : i < Fibonacci.V2.getMemberGreaterOrEqualTo(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(3)
        public void isMember() {
            System.out.println(getClass().getSimpleName() + " - isMember - 3");
            for (int i = 0; i < expFibSeqMax; i++) {
                final int finalI = i;
                assertEquals(
                        Arrays.stream(expFibSeq)
                                .anyMatch(expectedFibonacciNumber -> finalI == expectedFibonacciNumber),
                        Fibonacci.V2.isMember(i),
                        new StringBuilder().append("For index: ").append(i).toString()
                );
            }
        }

        @Test
        @Order(4)
        public void getMember_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - getMember_throwsExceptions - 4");
            assertThrows(Exception.class, () -> Fibonacci.V2.getMember(-1));
        }

        @Test
        @Order(5)
        public void getMemberGreaterOrEqualTo_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - getMemberGreaterOrEqualTo_throwsExceptions - 5");
            assertThrows(Exception.class, () -> Fibonacci.V2.getMemberGreaterOrEqualTo(-1));
        }

        @Test
        @Order(6)
        public void isMember_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - isMember_throwsExceptions - 6");
            assertThrows(Exception.class, () -> Fibonacci.V2.isMember(-1));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests for V3")
    public class V3 {

        @BeforeAll
        public void beforeAll() {
            System.out.println(getClass().getSimpleName() + " - beforeAll");
        }

        @AfterAll
        public void afterAll() {
            System.out.println(getClass().getSimpleName() + " - afterAll");
        }

        @BeforeEach
        void beforeEach() {
            System.out.println(getClass().getSimpleName() + " - beforeEach");
        }

        @AfterEach
        void afterEach() {
            System.out.println(getClass().getSimpleName() + " - afterEach");
        }

        @Test
        @Order(3)
        public void isMember() {
            System.out.println(getClass().getSimpleName() + " - isMember - 3");
            for (int candidate = 0; candidate < expFibSeqMax; candidate++) {
                final int finalCandidate = candidate;
                assertEquals(
                        Arrays.stream(expFibSeq)
                                .anyMatch(expectedFibonacciNumber -> finalCandidate == expectedFibonacciNumber),
                        Fibonacci.V2.isMember(candidate),
                        new StringBuilder().append("For index: ").append(candidate).toString()
                );
            }
        }

        @Test
        @Order(6)
        public void isMember_throwsExceptions() {
            System.out.println(getClass().getSimpleName() + " - isMember_throwsExceptions - 6");
            assertThrows(Exception.class, () -> Fibonacci.V1.isMember(-1));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests different versions against each other")
    public class AgainstEachOther {

        @BeforeAll
        public void beforeAll() {
            System.out.println(getClass().getSimpleName() + " - beforeAll");
        }

        @AfterAll
        public void afterAll() {
            System.out.println(getClass().getSimpleName() + " - afterAll");
        }

        @BeforeEach
        void beforeEach() {
            System.out.println(getClass().getSimpleName() + " - beforeEach");
        }

        @AfterEach
        void afterEach() {
            System.out.println(getClass().getSimpleName() + " - afterEach");
        }

        @Test
        @Order(1)
        public void getMember() {
            System.out.println(getClass().getSimpleName() + " - getMember - 1");
            if (Float.MAX_VALUE < Integer.MAX_VALUE) {
                throw new RuntimeException("Cannot convert every Integer to Float");
            }
            int currMember = 0;
            for (int i = 0; i < fibMemNumMax; i++) {
                currMember = Fibonacci.V1.getMember(i);
                if (i < expFibSeq.length) {
                    assertEquals(
                            expFibSeq[i],
                            currMember,
                            new StringBuilder().append("For index: ").append(i).toString()
                    );
                }
                assertEquals(currMember, Fibonacci.V2.getMember(i));
            }
        }

        @Test
        @Order(2)
        public void getMemberGreaterOrEqualTo() {
            System.out.println(getClass().getSimpleName() + " - getMemberGreaterOrEqualTo - 2");
            if (Float.MAX_VALUE < Integer.MAX_VALUE) {
                throw new RuntimeException("Cannot convert every Integer to Float");
            }
            int currMember = 0;
            for (int i = 0; i < fibMemNumMax; i++) {
                String errMsg = new StringBuilder().append("For index: ").append(i).toString();
                final int finalI = i;
                currMember = Fibonacci.V1.getMemberGreaterOrEqualTo(i);
                int matching = Arrays.stream(expFibSeq).filter(member -> member == finalI).findAny().orElse(-1);
                if (i < expFibSeqMax && matching != -1) {
                    assertEquals(matching, currMember, errMsg);
                }
                assertEquals(currMember, Fibonacci.V2.getMemberGreaterOrEqualTo(i), errMsg);
            }
        }

        @Test
        @Order(3)
        public void isMember() {
            System.out.println(getClass().getSimpleName() + " - isMember - 3");
            int currMember = 0;
            for (int i = 0; (currMember = Fibonacci.V1.getMember(i)) < expFibSeqMax; i++) {
                String errMsg = new StringBuilder().append("For index: ").append(i).toString();
                boolean isMember = Fibonacci.V1.isMember(i);
                final int finalI = i;
                if (i < expFibSeqMax) {
                    assertEquals(Arrays.stream(expFibSeq).anyMatch(member -> member == finalI), isMember, errMsg);
                }
                assertEquals(isMember, Fibonacci.V2.isMember(i), errMsg);
                assertEquals(isMember, Fibonacci.V3.isMember(i), errMsg);
            }
        }
    }
}