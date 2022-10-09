package input.read.formatter.block;

import utils.ClassMethodUtils;
import utils.StringUtils;

import java.util.Objects;

import static java.lang.Math.max;

public class Block implements Blocky {
    private int lines;
    private final StringBuilder contents;
    private boolean isNlMissingAtTheEnd;

    public Block() {
        this(1, new StringBuilder(), false);
    }

    Block(Block block) {
        this(block.lines, new StringBuilder().append(block.contents), block.isNlMissingAtTheEnd);
    }

    private Block(int lines, StringBuilder contents, boolean isNlMissingAtTheEnd) {
        this.lines = lines;
        this.contents = contents;
        this.isNlMissingAtTheEnd = isNlMissingAtTheEnd;
    }

    @Override
    public Block append(String toBeAdded) {
        lines += getLines(toBeAdded);
        contents.append(toBeAdded);
        setIsNlMissingAtTheEnd();
        return this;
    }

    private void setIsNlMissingAtTheEnd() {
        isNlMissingAtTheEnd = getLastIndexOfNl() != contents.length() - 1 && contents.length() != 0;
    }

    private int getLastIndexOfNl() {
        return max(
                contents.lastIndexOf(StringUtils.NL),
                max(
                        contents.lastIndexOf(StringUtils.LF),
                        contents.lastIndexOf(StringUtils.CR)
                )
        );
    }

    private int getLines(final String toBeAdded) {
        return contents.toString().endsWith(StringUtils.LF)
                && toBeAdded.startsWith(StringUtils.CR)
                ? getNextNlCount(toBeAdded) - 1
                : getNextNlCount(toBeAdded);
    }

    private int getNextNlCount(final String toBeAdded) {
        int nextAnyNlIndex = getNextAnyNlIndex(toBeAdded);
        boolean notFoundNextNl = nextAnyNlIndex == -1;
        return notFoundNextNl ? 0 : getNextNlCount(toBeAdded, nextAnyNlIndex);
    }

    private int getNextNlCount(final String toBeAdded, final int nextAnyNlIndex) {
        return getNextNlCount(
                toBeAdded.substring(
                        getNextSubStringStartIndex(toBeAdded, nextAnyNlIndex)
                )
        ) + 1;
    }

    private int getNextSubStringStartIndex(final String toBeAdded, final int nextAnyNlIndex) {
        return nextAnyNlIndex + 1 + getNlOffset(toBeAdded, nextAnyNlIndex);
    }

    private int getNlOffset(final String toBeAdded, final int nextAnyNlIndex) {
        return nextAnyNlIndex == toBeAdded.indexOf(StringUtils.NL) ? 1 : 0;
    }

    private int getNextAnyNlIndex(final String toBeAdded) {
        return getNextAnyNlIndex(
                toBeAdded.indexOf(StringUtils.LF),
                getNextAnyNlIndex(
                        toBeAdded.indexOf(StringUtils.CR),
                        toBeAdded.indexOf(StringUtils.NL)
                )
        );
    }

    private int getNextAnyNlIndex(final int nextAnyNl, final int nextSpecificNl) {
        return nextAnyNl < 0 || (nextSpecificNl > -1 && nextSpecificNl < nextAnyNl)
                ? nextSpecificNl
                : nextAnyNl;
    }

    @Override
    public int getLines() {
        return lines;
    }

    @Override
    public StringBuilder getContents() {
        return contents;
    }


    @Override
    public int getLinesToPrint() {
        return isNlMissingAtTheEnd() ? lines + 1 : lines;
    }

    @Override
    public StringBuilder getContentsToPrint() {
        return isNlMissingAtTheEnd() ? new StringBuilder(contents).append(StringUtils.NL) : contents;
    }

    @Override
    public boolean isNlMissingAtTheEnd() {
        return isNlMissingAtTheEnd;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassSimpleName(getClass()))
                .append("{")
                .append(StringUtils.NL)
                .append("lines=")
                .append(lines)
                .append(",")
                .append(StringUtils.NL)
                .append("contents=\"")
                .append(contents)
                .append("\",")
                .append(StringUtils.NL)
                .append("isNlMissingAtTheEnd=")
                .append(isNlMissingAtTheEnd)
                .append(StringUtils.NL)
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        Block block = null;
        if (object instanceof CopyOnWriteBlock) {
            CopyOnWriteBlock copyOnWriteBlock = (CopyOnWriteBlock) object;
            block = copyOnWriteBlock.getBlock();
        }
        if (object instanceof Block) {
            block = (Block) object;
        }
        if (block == null) return false;
        return lines == block.lines
                && Objects.equals(contents.toString(), block.contents.toString())
                && Objects.equals(isNlMissingAtTheEnd, block.isNlMissingAtTheEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines, contents, isNlMissingAtTheEnd);
    }

    @Override
    public Block clone() {
        return new Block(lines, new StringBuilder().append(contents), isNlMissingAtTheEnd);
    }
}
