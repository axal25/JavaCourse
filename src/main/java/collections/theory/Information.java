package collections.theory;

import input.read.formatter.PrintFormatter;
import utils.ClassMethodUtils;
import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Information {
    private String className;
    private StringBuilder contents;
    private List<Information> children;

    Information(String className) {
        this.className = className;
        this.contents = new StringBuilder();
        this.children = new LinkedList<>();
    }

    Information(Class clazz) {
        this.className = ClassMethodUtils.getClassSimpleName(clazz);
        this.contents = new StringBuilder();
        this.children = new LinkedList<>();
    }

    Information appendln(List<String> toBeAddeds) {
        toBeAddeds.forEach(this::appendln);
        return this;
    }

    Information appendln(String toBeAdded) {
        return append(String.format("%s%s", toBeAdded, StringUtils.NL));
    }

    public Information append(String toBeAdded) {
        contents.append(String.format("%s%s", StringUtils.TAB, toBeAdded));
        return this;
    }

    public Information addChild(Information child) {
        children.add(child);
        return this;
    }

    public void print() {
        PrintFormatter.print(getPrintString());
    }

    private String getPrintString() {
        return children.stream().map(child -> String.format("%s%s%s", StringUtils.NL, StringUtils.NL, child.getPrintString())).collect(
                Collectors.collectingAndThen(
                        Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                StringBuilder::toString
                        ),
                        childrenPrintString -> String.format(
                                "[%s]%s%sStructure:%s%s%s%s%s%s",
                                className,
                                StringUtils.NL,
                                StringUtils.TAB,
                                StringUtils.NL,
                                getStructure(String.format("%s%s", StringUtils.TAB, StringUtils.TAB)),
                                StringUtils.NL,
                                contents,
                                StringUtils.NL,
                                childrenPrintString
                        )
                )
        );
    }

    private String getStructure(String indent) {
        String nextIndent = String.format("%s%s", indent, StringUtils.TAB);
        return children.stream().map(child -> child.getStructure(nextIndent))
                .collect(
                        Collectors.collectingAndThen(
                                Collector.of(
                                        StringBuilder::new,
                                        StringBuilder::append,
                                        UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                        StringBuilder::toString
                                ),
                                childrenStructure -> String.format("%s%s%s%s", indent, className, StringUtils.NL, childrenStructure)
                        )
                );
    }
}
