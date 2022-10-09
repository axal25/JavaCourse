package collections.copyOnWriteSet;

import org.junit.jupiter.api.*;

import java.util.concurrent.CopyOnWriteArraySet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for CopyOnWriteSet")
public class CopyOnWriteSetTest {

    @Test
    @Order(1)
    public void concurrencyTest() throws InterruptedException {
        String[] inputStrings = new String[]{"inputString1\r\n", "inputString2\n", "inputString3\r", "inputString4\r\n"};
        final CopyOnWriteArraySet<String> copyOnWriteSet = new CopyOnWriteArraySet<>();
        copyOnWriteSet.add(inputStrings[0]);

        Thread firstThread = new Thread(() -> {
            copyOnWriteSet.add(inputStrings[1]);
            CopyOnWriteArraySet<String> expected1 = new CopyOnWriteArraySet<>();
            expected1.add(inputStrings[0]);
            expected1.add(inputStrings[1]);
            assertThat(copyOnWriteSet, is(not(equalTo(expected1))));
        });
        Thread secondThread = new Thread(() -> {
            copyOnWriteSet.add(inputStrings[2]);
            CopyOnWriteArraySet<String> expected2 = new CopyOnWriteArraySet<>();
            expected2.add(inputStrings[0]);
            expected2.add(inputStrings[2]);
            assertThat(copyOnWriteSet, is(not(equalTo(expected2))));
        });

        firstThread.start();
        secondThread.start();

        copyOnWriteSet.add(inputStrings[3]);

        firstThread.join();
        secondThread.join();

        CopyOnWriteArraySet<String> expected3 = new CopyOnWriteArraySet<>();
        expected3.add(inputStrings[0]);
        expected3.add(inputStrings[3]);

        assertThat(copyOnWriteSet, is(not(equalTo(expected3))));
        assertThat(copyOnWriteSet, hasItems(inputStrings));
    }
}
