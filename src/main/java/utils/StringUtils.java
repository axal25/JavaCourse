package utils;

import main.Main;

public class StringUtils {
    public static String EMPTY = "";
    private static String SPACE = " ";
    private static String NEW_LINE = "\n\r";
    private static String TABULATION = "\t";

    public static String NL = NEW_LINE;
    public static String TAB = TABULATION;

    static String DEFAULT_WHITE_SPACE = SPACE;

    private static boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }

    private static boolean isEmpty(StringBuilder input) {
        return input == null || input.toString().isEmpty();
    }

    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isBlank(StringBuilder input) {
        return input == null || input.toString().trim().isEmpty();
    }

    static String getCenteredString(String toPrint) {
        return getCenteredString(toPrint, EMPTY, DEFAULT_WHITE_SPACE, Main.SCREEN_WIDTH_IN_CHARS);
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
