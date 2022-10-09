package input.read.formatter.block;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArraySet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CopyOnWriteSynchronizedBlockTest {

    @Test
    @Order(1)
    public void concurrencyTest() throws InterruptedException {
        String[] inputStrings = new String[]{"inputString1\r\n", "inputString2\n", "inputString3\r", "inputString4\r\n"};
        final CopyOnWriteSynchronizedBlock copyOnWriteSyncBlock = new CopyOnWriteSynchronizedBlock()
                .append(inputStrings[0]);

        Thread firstThread = new Thread(() -> {
            copyOnWriteSyncBlock.append(inputStrings[1]);
            assertThat(copyOnWriteSyncBlock,
                    is(not(equalTo(
                            new CopyOnWriteSynchronizedBlock().append(inputStrings[0]).append(inputStrings[1])))));
        });
        Thread secondThread = new Thread(() -> {
            copyOnWriteSyncBlock.append(inputStrings[2]);
            assertThat(copyOnWriteSyncBlock,
                    is(not(equalTo(
                            new CopyOnWriteSynchronizedBlock().append(inputStrings[0]).append(inputStrings[2])))));
        });

        firstThread.start();
        secondThread.start();

        copyOnWriteSyncBlock.append(inputStrings[3]);

        firstThread.join();
        secondThread.join();

        CopyOnWriteArraySet<String> expected3 = new CopyOnWriteArraySet<>();
        expected3.add(inputStrings[0]);
        expected3.add(inputStrings[3]);

        assertThat(copyOnWriteSyncBlock,
                is(not(equalTo(
                        new CopyOnWriteSynchronizedBlock().append(inputStrings[0]).append(inputStrings[3])))));
        assertThat(copyOnWriteSyncBlock.getContents().toString(), containsString(inputStrings[0]));
        assertThat(copyOnWriteSyncBlock.getContents().toString(), containsString(inputStrings[1]));
        assertThat(copyOnWriteSyncBlock.getContents().toString(), containsString(inputStrings[2]));
        assertThat(copyOnWriteSyncBlock.getContents().toString(), containsString(inputStrings[3]));
    }
}
