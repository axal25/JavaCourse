package interview.challanges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubstringComparisonTest {
    private static final String[] argsEmpty = new String[]{};
    private static final Lock lock = new ReentrantLock();
    public ByteArrayOutputStream testOutStream;
    InputStream originalIn;
    PrintStream originalOut = System.out;
    ByteArrayInputStream testIn;
    private PrintStream testOut;

    @BeforeEach
    public void setUp() {
        lock.lock();
        originalIn = System.in;
        originalOut = System.out;
        testOutStream = new ByteArrayOutputStream();
        testOut = new PrintStream(testOutStream);
        System.setOut(testOut);
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        lock.unlock();
    }

    public void setInput(String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    @Test
    void a_1_returns_smallest_a_largest_a() {
        setInput("a 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "a" +
                        "\n" +
                        "a" +
                        "\n");
    }

    @Test
    void abc_1_returns_smallest_a_largest_b() {
        setInput("abc 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "a" +
                        "\n" +
                        "c" +
                        "\n");
    }

    @Test
    void ab_1_returns_smallest_a_largest_b() {
        setInput("ab 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "a" +
                        "\n" +
                        "b" +
                        "\n");
    }

    @Test
    void AB_1_returns_smallest_A_largest_b() {
        setInput("AB 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "A" +
                        "\n" +
                        "B" +
                        "\n");
    }

    @Test
    void aA_1_returns_smallest_A_largest_a() {
        setInput("aA 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "A" +
                        "\n" +
                        "a" +
                        "\n");
    }

    @Test
    void aB_1_returns_smallest_B_largest_a() {
        setInput("aB 1");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "B" +
                        "\n" +
                        "a" +
                        "\n");
    }

    @Test
    void abc_3_returns_smallest_abc_largest_abc() {
        setInput("abc 3");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "abc" +
                        "\n" +
                        "abc" +
                        "\n");
    }

    @Test
    void abcd_3_returns_smallest_abc_largest_bcd() {
        setInput("abcd 3");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "abc" +
                        "\n" +
                        "bcd" +
                        "\n");
    }

    @Test
    void abcdABCD_3_returns_smallest_ABC_largest_dAB() {
        setInput("abcdABCD 3");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "ABC" +
                        "\n" +
                        "dAB" +
                        "\n");
    }

    @Test
    void exampleFromChallange_welcometojava_3_returns_smallest_ava_largest_wel() {
        setInput("welcometojava 3");

        SubstringComparison.main(argsEmpty);

        assertThat(testOutStream.toString()).isEqualTo(
                "ava" +
                        "\n" +
                        "wel" +
                        "\n");
    }

    @Test
    void a_2_throwsIllegalArgumentException() {
        setInput("a 2");

        assertThrows(IllegalArgumentException.class, () -> SubstringComparison.main(argsEmpty));
        assertThat(testOutStream.toString()).isEmpty();
    }

    @Test
    void _2_throwsNoSuchElementException() {
        setInput(" 2");

        assertThrows(NoSuchElementException.class, () -> SubstringComparison.main(argsEmpty));
        assertThat(testOutStream.toString()).isEmpty();
    }

    @Test
    void getSubstrings_null_2_throwsNullPointerException() {
        setInput(" 2");

        assertThrows(NullPointerException.class, () -> SubstringComparison.getSubstrings(null, 2));
        assertThat(testOutStream.toString()).isEmpty();
    }
}
