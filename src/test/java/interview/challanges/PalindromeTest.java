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

public class PalindromeTest {
    private static final String[] emptyArgs = new String[]{};
    private static final Lock lock = new ReentrantLock();
    private PrintStream originalOut;
    private ByteArrayOutputStream testOutStream;
    private PrintStream testOut;
    private InputStream originalIn;
    private InputStream testIn;

    @BeforeEach
    void setUp() {
        lock.lock();
        originalOut = System.out;
        testOutStream = new ByteArrayOutputStream();
        testOut = new PrintStream(testOutStream);
        System.setOut(testOut);
        originalIn = System.in;
    }

    private void setInput(String input) {
        testIn = new ByteArrayInputStream(input == null ? new byte[]{} : input.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    void tearDown() {
        lock.unlock();
    }

    @Test
    void null_Yes() {
        setInput(null);

        assertThrows(NoSuchElementException.class, () -> Palindrome.main(emptyArgs));

        assertThat(testOutStream.toString()).isEmpty();
    }

    @Test
    void isPalindrome_null_true() {
        assertThat(Palindrome.isPalindrome(null)).isTrue();
    }

    @Test
    void empty_Yes_throwsNoSuchElementException_outIsEmpty() {
        setInput("");

        assertThrows(NoSuchElementException.class, () -> Palindrome.main(emptyArgs));
        assertThat(testOutStream.toString()).isEmpty();
    }

    @Test
    void isPalindrome_empty_Yes() {
        assertThat(Palindrome.isPalindrome("")).isTrue();
    }

    @Test
    void blank_Yes_throwsNoSuchElementException_outIsEmpty() {
        setInput(" ");

        assertThrows(NoSuchElementException.class, () -> Palindrome.main(emptyArgs));
        assertThat(testOutStream.toString()).isEmpty();
    }

    @Test
    void isPalindrome_blank_true() {
        assertThat(Palindrome.isPalindrome(" ")).isTrue();
    }

    @Test
    void a_Yes() {
        setInput("a");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("Yes\n");
    }

    @Test
    void ab_No() {
        setInput("ab");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("No\n");
    }

    @Test
    void aba_Yes() {
        setInput("aba");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("Yes\n");
    }

    @Test
    void abba_Yes() {
        setInput("abba");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("Yes\n");
    }

    @Test
    void abab_No() {
        setInput("abab");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("No\n");
    }

    @Test
    void ababa_Yes() {
        setInput("ababa");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("Yes\n");
    }

    @Test
    void challenge_example_madam_Yes() {
        setInput("madam");

        Palindrome.main(emptyArgs);

        assertThat(testOutStream.toString()).isEqualTo("Yes\n");
    }
}
