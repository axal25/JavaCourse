package input.read.formatter.block;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for CopyOnWriteBlock")
public class CopyOnWriteBlockTest {

    @Test
    @Order(1)
    public void noArgsConstructor_blockAndCopyOnWriteBlock_areEqual() {
        Block block = new Block();
        CopyOnWriteBlock copyOnWriteBlock = new CopyOnWriteBlock();

        assertThat(copyOnWriteBlock.getContents().toString(), is(equalTo(block.getContents().toString())));
        assertThat(copyOnWriteBlock.getLines(), is(equalTo(block.getLines())));

        assertThat(copyOnWriteBlock.isNlMissingAtTheEnd(), is(equalTo(block.isNlMissingAtTheEnd())));

        assertThat(copyOnWriteBlock.getContentsToPrint().toString(), is(equalTo(block.getContentsToPrint().toString())));
        assertThat(copyOnWriteBlock.getLinesToPrint(), is(equalTo(block.getLinesToPrint())));

        assertThat(copyOnWriteBlock, is(equalTo(block)));
    }

    @Test
    @Order(2)
    public void append_blockAndCopyOnWriteBlock_areEqual() {
        String inputString = "inputString";

        Block block = new Block().append(inputString);
        CopyOnWriteBlock copyOnWriteBlock = new CopyOnWriteBlock().append(inputString);

        assertThat(copyOnWriteBlock.getContents().toString(), is(equalTo(block.getContents().toString())));
        assertThat(copyOnWriteBlock.getLines(), is(equalTo(block.getLines())));

        assertThat(copyOnWriteBlock.isNlMissingAtTheEnd(), is(equalTo(block.isNlMissingAtTheEnd())));

        assertThat(copyOnWriteBlock.getContentsToPrint().toString(), is(equalTo(block.getContentsToPrint().toString())));
        assertThat(copyOnWriteBlock.getLinesToPrint(), is(equalTo(block.getLinesToPrint())));

        assertThat(copyOnWriteBlock.getBlock(), is(equalTo(block)));
        assertThat(copyOnWriteBlock, is(equalTo(block)));
    }

    @Test
    @Order(3)
    public void noArgsConstructor_copyOnWriteBlockAndClone_areEqual() {
        CopyOnWriteBlock copyOnWriteBlock = new CopyOnWriteBlock();
        CopyOnWriteBlock clone = copyOnWriteBlock.clone();

        assertThat(clone.getContents().toString(), is(equalTo(copyOnWriteBlock.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(copyOnWriteBlock.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(copyOnWriteBlock.isNlMissingAtTheEnd())));

        assertThat(clone.getContentsToPrint().toString(), is(equalTo(copyOnWriteBlock.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(copyOnWriteBlock.getLinesToPrint())));

        assertThat(clone, is(equalTo(copyOnWriteBlock)));
    }

    @Test
    @Order(4)
    public void append_copyOnWriteBlockAndClone_areEqual() {
        String inputString = "inputString";

        CopyOnWriteBlock copyOnWriteBlock = new CopyOnWriteBlock().append(inputString);
        CopyOnWriteBlock clone = copyOnWriteBlock.clone();

        assertThat(clone.getContents().toString(), is(equalTo(copyOnWriteBlock.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(copyOnWriteBlock.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(copyOnWriteBlock.isNlMissingAtTheEnd())));

        assertThat(clone.getContentsToPrint().toString(), is(equalTo(copyOnWriteBlock.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(copyOnWriteBlock.getLinesToPrint())));

        assertThat(clone, is(equalTo(copyOnWriteBlock)));
    }

    @Test
    @Order(5)
    public void appendAfterClone_copyOnWriteBlockAndClone_areEqual() {
        String inputString = "inputString";

        CopyOnWriteBlock copyOnWriteBlock = new CopyOnWriteBlock();
        CopyOnWriteBlock clone = copyOnWriteBlock.clone();

        copyOnWriteBlock.append(inputString);
        clone.append(inputString);

        assertThat(clone.getContents().toString(), is(equalTo(copyOnWriteBlock.getContents().toString())));
        assertThat(clone.getLines(), is(equalTo(copyOnWriteBlock.getLines())));

        assertThat(clone.isNlMissingAtTheEnd(), is(equalTo(copyOnWriteBlock.isNlMissingAtTheEnd())));

        assertThat(clone.getContentsToPrint().toString(), is(equalTo(copyOnWriteBlock.getContentsToPrint().toString())));
        assertThat(clone.getLinesToPrint(), is(equalTo(copyOnWriteBlock.getLinesToPrint())));

        assertThat(clone, is(equalTo(copyOnWriteBlock)));
    }

    @Test
    @Order(6)
    public void preAppendBlock_andPostAppendBlock_areNotEqual() {
        String inputString = "inputString";

        CopyOnWriteBlock currentCopyOnWriteBlock = new CopyOnWriteBlock();
        Block prevBlock = currentCopyOnWriteBlock.getBlock();
        CopyOnWriteBlock prevCopyOnWriteBlocks = currentCopyOnWriteBlock.clone();

        currentCopyOnWriteBlock.append(inputString);

        assertThat(prevBlock, is(not(equalTo(currentCopyOnWriteBlock.getBlock()))));
        assertThat(prevBlock != currentCopyOnWriteBlock.getBlock(), is(equalTo(true)));

        assertThat(prevCopyOnWriteBlocks, is(equalTo(new Block())));
        assertThat(prevCopyOnWriteBlocks, is(equalTo(new CopyOnWriteBlock())));

        assertThat(currentCopyOnWriteBlock.getBlock(), is(equalTo(new CopyOnWriteBlock().append(inputString).getBlock())));
        assertThat(currentCopyOnWriteBlock, is(equalTo(new CopyOnWriteBlock().append(inputString))));
    }

    @Test
    @Order(7)
    public void concurrencyTest() throws InterruptedException {
        String[] inputStrings = new String[]{"inputString1\r\n", "inputString2\n", "inputString3\r", "inputString4\r\n"};
        final CopyOnWriteBlock block = new CopyOnWriteBlock().append(inputStrings[0]);

        Thread firstThread = new Thread(() -> {
            block.append(inputStrings[1]);
            assertThat(block, is(equalTo(new CopyOnWriteBlock().append(inputStrings[0]).append(inputStrings[1]))));
        });
        Thread secondThread = new Thread(() -> {
            block.append(inputStrings[2]);
            assertThat(block, is(equalTo(new CopyOnWriteBlock().append(inputStrings[0]).append(inputStrings[2]))));
        });

        firstThread.start();
        secondThread.start();

        block.append(inputStrings[3]);

        firstThread.join();
        secondThread.join();

        assertThat(block,
                is(not(equalTo(
                        new CopyOnWriteBlock().append(inputStrings[0]).append(inputStrings[3])))));
        assertThat(block.getContents().toString(), containsString(inputStrings[0]));
        assertThat(block.getContents().toString(), containsString(inputStrings[1]));
        assertThat(block.getContents().toString(), containsString(inputStrings[2]));
        assertThat(block.getContents().toString(), containsString(inputStrings[3]));
    }
}
