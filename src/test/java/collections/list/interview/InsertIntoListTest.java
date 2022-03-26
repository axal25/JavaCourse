package collections.list.interview;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for InsertIntoList")
public class InsertIntoListTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class InsertExceptionsTest {

        @Test
        @Order(1)
        public void nullList() {
            assertThrows(NullPointerException.class, () -> InsertIntoList.insert(null, 0, 0));
        }

        @Test
        @Order(2)
        public void indexLargerThanSize() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> InsertIntoList.insert(new ArrayList<>(), 0, 1));
        }

        @Test
        @Order(3)
        public void indexLowerThan0() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> InsertIntoList.insert(new ArrayList<>(), 0, -1));
        }
    }

    private static <T extends Object> void assertInsert(List<T> list, T insertable, int index,
                                                        int expectedSizeBeforeInsert, int expectedSizeAfterInsert) {
        assertEquals(expectedSizeBeforeInsert, list.size());
        List listBefore = new ArrayList(list);
        assertTrue(InsertIntoList.insert(list, insertable, index));
        for (int i = 0; i < list.size(); i++) {
            if (i < index) {
                assertEquals(listBefore.get(i), list.get(i));
            } else if (i == index) {
                assertEquals(insertable, list.get(i));
            } else if (i > index) {
                assertEquals(listBefore.get(i - 1), list.get(i));
            }
        }
        assertEquals(expectedSizeAfterInsert, list.size());
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class InsertionsAfterLastTest {

        @Test
        @Order(1)
        public void insertAt0ForSize0() {
            assertInsert(new ArrayList<>(), 0, 0, 0, 1);
        }

        @Test
        @Order(2)
        public void insertAt1ForSize1() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            assertInsert(list, 1, 1, 1, 2);
        }

        @Test
        @Order(3)
        public void insertAt2ForSize2() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            assertInsert(list, 2, 2, 2, 3);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class InsertionsAtMiddleTest {

        @Test
        @Order(7)
        public void testInsert3At2() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            assertInsert(list, 3, 2, 3, 4);
        }

        @Test
        @Order(8)
        public void testInsert4At2() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            InsertIntoList.insert(list, 3, 2);
            assertInsert(list, 4, 2, 4, 5);
        }

        @Test
        @Order(9)
        public void testInsert5At2() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            InsertIntoList.insert(list, 3, 2);
            InsertIntoList.insert(list, 4, 2);
            assertInsert(list, 5, 2, 5, 6);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class InsertionsAt0Test {

        @Test
        @Order(10)
        public void testInsert6At0() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            InsertIntoList.insert(list, 3, 2);
            InsertIntoList.insert(list, 4, 2);
            InsertIntoList.insert(list, 5, 2);
            assertInsert(list, 6, 0, 6, 7);
        }

        @Test
        @Order(10)
        public void testInsert7At0() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            InsertIntoList.insert(list, 3, 2);
            InsertIntoList.insert(list, 4, 2);
            InsertIntoList.insert(list, 5, 2);
            InsertIntoList.insert(list, 6, 0);
            assertInsert(list, 7, 0, 7, 8);
        }

        @Test
        @Order(10)
        public void testInsert8At0() {
            List<Integer> list = new ArrayList<>();
            InsertIntoList.insert(list, 0, 0);
            InsertIntoList.insert(list, 1, 1);
            InsertIntoList.insert(list, 2, 2);
            InsertIntoList.insert(list, 3, 2);
            InsertIntoList.insert(list, 4, 2);
            InsertIntoList.insert(list, 5, 2);
            InsertIntoList.insert(list, 6, 0);
            InsertIntoList.insert(list, 7, 0);
            assertInsert(list, 8, 0, 8, 9);
        }
    }
}
