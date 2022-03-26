package input.read.formatter;

import utils.ClassMethodUtils;
import utils.StringUtils;

public class Block {
    private int lines;
    private StringBuilder contents;
    private Boolean isNlMissingAtTheEnd;

    public Block() {
        lines = 1;
        contents = new StringBuilder();
        isNlMissingAtTheEnd = false;
    }

    public Block append(String toBeAdded) {
        lines += getLines(toBeAdded);
        contents.append(toBeAdded);
        isNlMissingAtTheEnd = null;
        return this;
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

    public int getLines() {
        return isNlMissingAtTheEnd() ? lines + 1 : lines;
    }

    public StringBuilder getContents() {
        return isNlMissingAtTheEnd() ? new StringBuilder(contents).append(StringUtils.NL) : contents;
    }

    boolean isNlMissingAtTheEnd() {
        if (isNlMissingAtTheEnd == null) {
            setIsNlMissingAtTheEnd();
        }
        return isNlMissingAtTheEnd;
    }

    private void setIsNlMissingAtTheEnd() {
        this.isNlMissingAtTheEnd = getLastNlIndex() != contents.length() - 1 && contents.length() != 0;
    }

    private int getLastNlIndex() {
        return Math.max(
                contents.lastIndexOf(StringUtils.NL),
                Math.max(
                        contents.lastIndexOf(StringUtils.LF),
                        contents.lastIndexOf(StringUtils.CR)
                )
        );
    }

    @Override
    public String toString() {
        return String.format("%s{%slines=%s,%scontents=\"%s\"%s}",
                ClassMethodUtils.getClassSimpleName(this.getClass()),
                StringUtils.NL,
                lines,
                StringUtils.NL,
                StringUtils.replaceNLsWithPrintable(contents.toString()),
                StringUtils.NL
        );
    }
}
