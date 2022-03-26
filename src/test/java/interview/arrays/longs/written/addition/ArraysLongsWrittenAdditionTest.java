package interview.arrays.longs.written.addition;

import org.junit.jupiter.api.*;
import utils.CollectionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for InsertIntoList")
public class ArraysLongsWrittenAdditionTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class InsertExceptionsTest {

    }

    private void assertAdd(int[] expectedSum, int[] first, int[] second) {
        assertEquals(
                CollectionUtils.toList(expectedSum),
                CollectionUtils.toList(ArraysLongsWrittenAddition.add(first, second))
        );
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements0 {

        @Test
        @Order(0)
        public void test0() {
            assertAdd(new int[]{}, new int[]{}, new int[]{});
        }

        @Test
        @Order(0)
        public void test1() {
            assertAdd(new int[]{1}, new int[]{1}, new int[]{});
        }

        @Test
        @Order(0)
        public void test2() {
            assertAdd(new int[]{2}, new int[]{}, new int[]{2});
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements1 {

        @Test
        @Order(1)
        public void test3() {
            assertAdd(new int[]{3}, new int[]{1}, new int[]{2});
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements2 {

        @Test
        @Order(1)
        public void test4() {
            assertAdd(new int[]{2, 4}, new int[]{1}, new int[]{2, 3});
        }

        @Test
        @Order(1)
        public void test5() {
            assertAdd(new int[]{1, 5}, new int[]{1, 2}, new int[]{3});
        }

        @Test
        @Order(1)
        public void test6() {
            assertAdd(new int[]{4, 6}, new int[]{1, 2}, new int[]{3, 4});
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements3 {

        @Test
        @Order(1)
        public void test7() {
            assertAdd(new int[]{2, 3, 5}, new int[]{1}, new int[]{2, 3, 4});
        }

        @Test
        @Order(1)
        public void test8() {
            assertAdd(new int[]{1, 2, 7}, new int[]{1, 2, 3}, new int[]{4});
        }

        @Test
        @Order(1)
        public void test9() {
            assertAdd(new int[]{5, 7, 9}, new int[]{1, 2, 3}, new int[]{4, 5, 6});
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements4 {

        @Test
        @Order(1)
        public void test10() {
            assertAdd(new int[]{2, 3, 4, 6}, new int[]{1}, new int[]{2, 3, 4, 5});
        }

        @Test
        @Order(1)
        public void test11() {
            assertAdd(new int[]{1, 2, 3, 9}, new int[]{1, 2, 3, 4}, new int[]{5});
        }

        @Test
        @Order(1)
        public void test12() {
            assertAdd(new int[]{6, 9, 1, 2}, new int[]{1, 2, 3, 4}, new int[]{5, 6, 7, 8});
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class Elements5 {

        @Test
        @Order(1)
        public void test10() {
            assertAdd(new int[]{2, 3, 4, 5, 7}, new int[]{1}, new int[]{2, 3, 4, 5, 6});
        }

        @Test
        @Order(1)
        public void test11() {
            assertAdd(new int[]{1, 2, 3, 5, 1}, new int[]{1, 2, 3, 4, 5}, new int[]{6});
        }

        @Test
        @Order(1)
        public void test12() {
            assertAdd(new int[]{8, 0, 2, 3, 5}, new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 0});
        }
    }
}
