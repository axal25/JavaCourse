package utils;

import screen.constants.ScreenConstants;

public class StringUtils {
    public static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String NEW_LINE_FEED = "\n";
    private static final String CARRIAGE_RETURN = "\r";
    private static final String NEW_LINE = String.format("%s%s", CARRIAGE_RETURN, NEW_LINE_FEED);
    private static final String HORIZONTAL_TABULATION = "\t";

    public static final String LF = NEW_LINE_FEED;
    public static final String CR = CARRIAGE_RETURN;
    public static final String NL = NEW_LINE;
    public static final String TAB = HORIZONTAL_TABULATION;

    static final String DEFAULT_WHITE_SPACE = SPACE;

    public static <T> String toStringNullSafe(T object) {
        return object == null ? null : object.toString();
    }

    private static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    public static boolean isEmpty(StringBuilder input) {
        return input == null || input.toString().isEmpty();
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isBlank(StringBuilder input) {
        return input == null || input.toString().trim().isEmpty();
    }

    static String getCenteredString(String toPrint) {
        return getCenteredString(toPrint, EMPTY, DEFAULT_WHITE_SPACE, ScreenConstants.SCREEN_WIDTH_IN_CHARS);
    }

    static String getCenteredString(String toPrint, String indent, String spacer, int maxLength) {
        int indentFreeSpaceLength = Math.max(0, maxLength - toPrint.length());
        int indentFittingLength = Math.min(indent.length(), indentFreeSpaceLength);
        String indentFitting = indent.substring(0, indentFittingLength);
        int circumfixFreeSpaceLength = (maxLength - toPrint.length() - indent.length()) / 2;
        String circumfix = getRepeatedUntil(spacer, circumfixFreeSpaceLength);
        return new StringBuilder()
                .append(circumfix)
                .append(indentFitting)
                .append(toPrint)
                .append(circumfix)
                .toString();
    }

    public static String getRepeatedUntil(String toBeRepeated, int maxLength) {
        if (isEmpty(toBeRepeated) || maxLength < 1) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() + toBeRepeated.length() < maxLength) {
            sb.append(toBeRepeated);
        }
        sb.append(toBeRepeated, 0, maxLength - sb.length());
        return sb.toString();
    }
}
