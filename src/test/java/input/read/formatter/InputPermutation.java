package input.read.formatter;

import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class InputPermutation {

    private static final List<String> newLinePermutations =
            List.of(StringUtils.EMPTY, StringUtils.LF, StringUtils.CR, StringUtils.NL);

    private final String input;
    private List<InputPermutation> children;

    InputPermutation(String input) {
        this.input = input;
        this.children = new LinkedList<>();
    }

    private InputPermutation(String input, List<InputPermutation> children) {
        this.input = input;
        this.children = Collections.unmodifiableList(children);
    }

    String getInput() {
        return input;
    }

    List<InputPermutation> getChildren() {
        return children;
    }

    void generateChildren(final Integer childrenAppender, final int generationLimit) {
        children = generationLimit == 0
                ? List.of()
                : Collections.unmodifiableList(getNewChildrenChildren(
                input,
                childrenAppender == null ? StringUtils.EMPTY : String.valueOf(childrenAppender)
        ));
        children.forEach(child -> child.generateChildren(
                childrenAppender == null ? 0 : childrenAppender + 1,
                generationLimit - 1
        ));
    }

    private static List<InputPermutation> getNewChildrenChildren(final String input, final String childrenAppender) {
        return newLinePermutations.stream()
                .map(newLine -> new InputPermutation(String.format("%s%s%s", input, childrenAppender, newLine)))
                .collect(Collectors.toList());
    }

    private static List<InputPermutation> replaceNLsWithPrintable(final List<InputPermutation> list) {
        return list.stream().map(inputPermutation -> new InputPermutation(
                StringUtils.replaceNLsWithPrintable(inputPermutation.input),
                replaceNLsWithPrintable(inputPermutation.children)
        )).collect(Collectors.toList());
    }

    private static String replacedWithPrintableToString(final List<InputPermutation> list, final String indent) {
        return replaceNLsWithPrintable(list).stream().collect(Collector.of(
                StringBuilder::new,
                (sb, inputPermutation) -> {
                    String appender = String.format(
                            "%s%s",
                            String.format("%s\"%s\"", indent, inputPermutation.input),
                            inputPermutation.children.isEmpty()
                                    ? StringUtils.EMPTY
                                    : String.format("{%s%s%s%s}",
                                    StringUtils.NL,
                                    replacedWithPrintableToString(
                                            inputPermutation.children,
                                            String.format("%s%s", indent, StringUtils.TAB)
                                    ),
                                    StringUtils.NL,
                                    indent
                            )
                    );
                    sb.append(
                            StringUtils.isBlank(sb)
                                    ? appender
                                    : String.format(",%s%s", StringUtils.NL, appender)
                    );
                },
                UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                StringBuilder::toString
        ));
    }

    @Override
    public String toString() {
        return replacedWithPrintableToString(List.of(this), StringUtils.EMPTY);
    }
}
