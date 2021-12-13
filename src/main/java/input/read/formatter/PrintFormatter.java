package input.read.formatter;

import input.read.common.InputCommons;
import screen.constants.ScreenConstants;
import utils.StringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PrintFormatter {
    private final List<Block> blocks = new LinkedList<>();

    private PrintFormatter(String stringToBePrinted) {
        Scanner scannerToBePrinted = new Scanner(stringToBePrinted);
        populateBlocks(scannerToBePrinted);
        scannerToBePrinted.close();
    }

    private void populateBlocks(final Scanner scannerToBePrinted) {
        Block block = new Block();
        int i = 1;
        while (scannerToBePrinted.hasNextLine()) {
            splitLine(block, scannerToBePrinted.nextLine());
            block = getNewBlockAndAddOldIfFull(block);
            i++;
        }
        blocks.add(block);
    }

    private void splitLine(final Block block, final String currentReadLine) {
        int firstUnreadLineCharIndex = 0;
        while (firstUnreadLineCharIndex < currentReadLine.length()) {
            int nextFirstUnreadLineCharIndex = firstUnreadLineCharIndex + Math.min(currentReadLine.length() - firstUnreadLineCharIndex, ScreenConstants.SCREEN_WIDTH_IN_CHARS);
            block.append(currentReadLine.substring(firstUnreadLineCharIndex, nextFirstUnreadLineCharIndex));
            firstUnreadLineCharIndex = nextFirstUnreadLineCharIndex;
        }
    }

    private Block getNewBlockAndAddOldIfFull(final Block block) {
        if (block.getLines() > ScreenConstants.SCREEN_HEIGHT_IN_CHARS - 3) {
            blocks.add(block);
            return new Block();
        }
        return block;
    }

    public static void print(String stringToBePrinted) {
        Iterator<Block> blocksIterator = new PrintFormatter(stringToBePrinted).blocks.iterator();
        while (blocksIterator.hasNext()) {
            System.out.print(blocksIterator.next().getContents());
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
}
