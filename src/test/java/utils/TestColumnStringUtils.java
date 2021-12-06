package utils;

import java.util.stream.IntStream;

import static utils.StringUtils.NL;

class TestColumnStringUtils {
    private final int maxLength;
    private final int column1Width;
    private final int column2Width;

    TestColumnStringUtils(int maxLength, double column1PercentWidth, double column2PercentWidth) {
        this.maxLength = maxLength;
        this.column1Width = (int) (maxLength * column1PercentWidth);
        this.column2Width = (int) (maxLength * column2PercentWidth);
    }

    private String getColumnedMessage(String variableName, String variableValue) {
        return String.format(
                new StringBuilder()
                        .append("%").append(column1Width).append("s")
                        .append(": ")
                        .append("%").append(column2Width).append("s")
                        .toString(),
                variableName,
                new StringBuilder().append("\"").append(variableValue).append("\"")
        );
    }

    private String getColumnedMessage(String variableName, int variableValue) {
        return String.format(
                new StringBuilder()
                        .append("%").append(column1Width).append("s")
                        .append(": ")
                        .append("%").append(column2Width).append("s")
                        .toString(),
                variableName,
                variableValue
        );
    }

    String getColumnedMessage(Object variableName, Object variableValue) {
        return getColumnedMessage((String) variableName, variableValue);
    }

    private String getColumnedMessage(String variableName, Object variableValue) {
        if (variableValue instanceof Integer) {
            return getColumnedMessage(variableName, (int) variableValue);
        }
        return getColumnedMessage(variableName, variableValue.toString());
    }

    String getColumnedMessage(VarPair<?>... varPairs) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, varPairs.length).forEach(i ->
                sb.append(NL).append(getColumnedMessage(varPairs[i].getKey(), varPairs[i].getValue()))
                        .append(i == varPairs.length - 1 ? StringUtils.EMPTY : ", ")
        );
        return sb.toString();
    }

    static class VarPair<V> extends Pair<String, V> {
        VarPair(String variableName, V variableValue) {
            super(variableName, variableValue);
        }
    }
}
