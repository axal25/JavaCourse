package input.read.formatter;

import input.read.common.InputCommons;
import input.read.formatter.block.Blocky;
import input.read.formatter.block.CopyOnWriteBlock;
import screen.constants.ScreenConstants;
import utils.StringUtils;
import utils.VisibleForTesting;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class PrintFormatter {

    private PrintFormatter() {
    }

    public static void print(String stringToBePrinted) {
        new PrintFormatter().printInBlocks(stringToBePrinted);
    }

    @VisibleForTesting
    void printInBlocks(final String stringToBePrinted) {
        printBlocks(getBlocks(stringToBePrinted));
    }

    @VisibleForTesting
    void printBlocks(List<Blocky> blocks) {
        Iterator<Blocky> blocksIterator = blocks.iterator();
        while (blocksIterator.hasNext()) {
            System.out.print(blocksIterator.next().getContentsToPrint());
            if (blocksIterator.hasNext()) {
                System.out.println();
                InputCommons.promptForEnter(
                        String.format(
                                "%s%s%s%s%sThere is more to print.",
                                StringUtils.TAB, StringUtils.TAB, StringUtils.TAB, StringUtils.TAB, StringUtils.TAB
                        )
                );
                System.out.println();
            }
        }
    }

    @VisibleForTesting
    List<Blocky> getBlocks(final String stringToBePrinted) {
        Scanner scannerToBePrinted = new Scanner(stringToBePrinted);
        List<Blocky> blocks = getBlocks(scannerToBePrinted);
        scannerToBePrinted.close();
        return blocks;
    }

    @VisibleForTesting
    List<Blocky> getBlocks(final Scanner scannerToBePrinted) {
        Blocky block = new CopyOnWriteBlock();
        List<Blocky> blocks = new CopyOnWriteArrayList<>();
        blocks.add(block);
        while (scannerToBePrinted.hasNextLine()) {
            splitLinesIfNeeded(block, scannerToBePrinted.nextLine());
            if (block.getLinesToPrint() > ScreenConstants.SCREEN_HEIGHT_IN_CHARS - 3) {
                block = new CopyOnWriteBlock();
                blocks.add(block);
            }
        }
        return blocks;
    }

    @VisibleForTesting
    void splitLinesIfNeeded(final Blocky block, final String currentReadLine) {
        int firstUnreadLineCharIndex = 0;
        while (firstUnreadLineCharIndex < currentReadLine.length()) {
            int nextFirstUnreadLineCharIndex = firstUnreadLineCharIndex + Math.min(currentReadLine.length() - firstUnreadLineCharIndex, ScreenConstants.SCREEN_WIDTH_IN_CHARS);
            block.append(currentReadLine.substring(firstUnreadLineCharIndex, nextFirstUnreadLineCharIndex));
            firstUnreadLineCharIndex = nextFirstUnreadLineCharIndex;
        }
    }
}
