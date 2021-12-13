package input.read.formatter;

import utils.StringUtils;

public class Block {
    private int lines;
    private StringBuilder contents;

    public Block() {
        lines = 0;
        contents = new StringBuilder();
    }

    public Block append(String toBeAdded) {
        lines += getLfCount(toBeAdded);
        contents.append(toBeAdded);
        if (isNlMissingAtTheEnd(toBeAdded)) {
            contents.append(StringUtils.NL);
            lines++;
        }
        return this;
    }

    private int getLfCount(final String toBeAdded) {
        int lfIndex = toBeAdded.indexOf(StringUtils.LF);
        int crIndex = toBeAdded.indexOf(StringUtils.CR);
        return lfIndex == -1 && crIndex == -1
                ? 0
                : getLfCount(toBeAdded.substring(Math.min(Math.max(lfIndex, crIndex) + 1, toBeAdded.length()))) + 1;
    }

    private boolean isNlMissingAtTheEnd(String toBeAdded) {
        int lastLfIndex = toBeAdded.lastIndexOf(StringUtils.LF);
        int lastCrIndex = toBeAdded.lastIndexOf(StringUtils.CR);
        return lastLfIndex != toBeAdded.length() - 1
                && lastCrIndex != toBeAdded.length() - 1
                && (lastLfIndex != toBeAdded.length() - 2
                || lastCrIndex != toBeAdded.length() - 1);
    }

    public int getLines() {
        return lines;
    }

    public StringBuilder getContents() {
        return contents;
    }
}
