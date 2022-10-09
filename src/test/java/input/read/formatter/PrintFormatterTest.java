package input.read.formatter;

import input.read.formatter.block.Block;
import input.read.formatter.block.Blocky;
import input.read.formatter.block.CopyOnWriteBlock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("PrintFormatter Test")
@ExtendWith(MockitoExtension.class)
public class PrintFormatterTest {

    @Captor
    private ArgumentCaptor<Scanner> scannerAC;
    @Captor
    private ArgumentCaptor<Blocky> blockAC;
    @Captor
    private ArgumentCaptor<List<Blocky>> blocksAC;
    @Captor
    private ArgumentCaptor<String> stringAC1;
    @Captor
    private ArgumentCaptor<String> stringAC2;

    @Spy
    private PrintFormatter printFormatterSpy;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("#printInBlocks() Tests")
    public class PrintInBlocksTest {

        @Test
        @Order(1)
        public void printInBlocks_input_9chars() {
            String expectedInputText = "testLine1";
            printFormatterSpy.printInBlocks(expectedInputText);

            verify(printFormatterSpy, times(1)).printInBlocks(expectedInputText);

            verify(printFormatterSpy, times(1)).getBlocks(stringAC1.capture());

            assertThat(stringAC1.getValue(), is(equalTo(expectedInputText)));


            verify(printFormatterSpy, times(1)).getBlocks(scannerAC.capture());

            Scanner expectedScanner = new Scanner(expectedInputText);
            while (expectedScanner.hasNextLine()) {
                expectedScanner.nextLine();
            }
            assertThat(scannerAC.getValue().toString(), is(equalTo(expectedScanner.toString())));

            verify(printFormatterSpy, times(1)).splitLinesIfNeeded(blockAC.capture(), stringAC2.capture());

            CopyOnWriteBlock expectedCopyOnWriteBlock = new CopyOnWriteBlock().append(expectedInputText);
            assertThat(blockAC.getValue(), is(equalTo(expectedCopyOnWriteBlock)));
            expectedCopyOnWriteBlock.append("\nntest\n");
            assertThat(stringAC2.getValue(), is(equalTo(expectedInputText)));

            verify(printFormatterSpy, times(1)).printBlocks(blocksAC.capture());

            List<Blocky> expectedCopyOnWriteBlocks = new CopyOnWriteArrayList<>();
            expectedCopyOnWriteBlocks.add(expectedCopyOnWriteBlock);
            assertThat(blocksAC.getValue(), is(equalTo(expectedCopyOnWriteBlocks)));

            verifyNoMoreInteractions(printFormatterSpy);
        }


        @Test
        @Order(1)
        public void printInBlocks_2lines() {
            String expectedInputText = "testLine1\ntestLine2";
            printFormatterSpy.printInBlocks(expectedInputText);

            verify(printFormatterSpy, times(1)).printInBlocks(expectedInputText);

            verify(printFormatterSpy, times(1)).getBlocks(stringAC1.capture());
            assertThat(stringAC1.getValue(), is(equalTo(expectedInputText)));

            Scanner expectedScanner = new Scanner(expectedInputText);
            while (expectedScanner.hasNextLine()) {
                expectedScanner.nextLine();
            }
            verify(printFormatterSpy, times(1)).getBlocks(scannerAC.capture());
            assertThat(scannerAC.getValue().toString(), is(equalTo(expectedScanner.toString())));

            String[] expectedBlock2Lines = expectedInputText.split("\n");

            verify(printFormatterSpy, times(2)).splitLinesIfNeeded(blockAC.capture(), stringAC2.capture());

            Block expectedBlock1 = new Block().append("testLine1").append("testLine2");
            assertThat(blockAC.getAllValues().get(0), is(equalTo(expectedBlock1)));
            assertThat(blockAC.getAllValues().get(1), is(equalTo(expectedBlock1)));
            assertThat(blockAC.getAllValues(), is(equalTo(List.of(expectedBlock1, expectedBlock1))));
            assertThat(blockAC.getAllValues().get(0) == blockAC.getAllValues().get(1), is(equalTo(true)));

            assertThat(stringAC2.getAllValues().size(), is(equalTo(2)));
            assertThat(stringAC2.getAllValues().get(0), is(equalTo(expectedBlock2Lines[0])));
            assertThat(stringAC2.getAllValues().get(1), is(equalTo(expectedBlock2Lines[1])));
            assertThat(stringAC2.getAllValues(), is(equalTo(List.of(expectedBlock2Lines[0], expectedBlock2Lines[1]))));

            verify(printFormatterSpy, times(1)).printBlocks(blocksAC.capture());

            List<Block> expectedBlocks = new CopyOnWriteArrayList<>();
            expectedBlocks.add(expectedBlock1);
            assertThat(blocksAC.getValue(), is(equalTo(expectedBlocks)));

            verifyNoMoreInteractions(printFormatterSpy);

            fail("figure this out");
        }
    }
}
