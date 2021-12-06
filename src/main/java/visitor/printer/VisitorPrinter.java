package visitor.printer;

import main.Main;
import utils.StringUtils;
import visitor.collection.VisitorCollection;
import visitor.element.A11;
import visitor.element.B111;
import visitor.element.B112;
import visitor.element.I1;
import visitor.exporter.Exporter;
import visitor.map.common.ICommonVisitor;
import visitor.map.wrapper.ClassWrapper;
import visitor.visitor.A11Visitor;
import visitor.visitor.B111Visitor;
import visitor.visitor.B112Visitor;
import visitor.visitor.I1Visitor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class VisitorPrinter {
    private static final int column1Width = (int) (0.05 * Main.SCREEN_WIDTH_IN_CHARS);
    private static final int column2Width = (int) (0.1 * Main.SCREEN_WIDTH_IN_CHARS);
    private static final int column3Width = (int) (0.20 * Main.SCREEN_WIDTH_IN_CHARS);
    private static final int column4Width = (int) (0.65 * Main.SCREEN_WIDTH_IN_CHARS);
    private static final String tableIndent = "";
    private static final String tableColumnSeparator = " | ";
    private static final String tableRowSeparator = "-";

    private static final VisitorCollection visitorCollection = new VisitorCollection();

    public static void test() {
        Map<String, Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>>> testResults = getTestResults();
        printTestResults(testResults);
    }

    private static void printTestResults(Map<String, Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>>> testResults) {
        testResults.entrySet().forEach(testResult -> {
            testResult.getValue().entrySet().forEach(methodTestResult -> {
                printTableSeparatorRow();
                printTableRow("Method", "Visitor class", "Element class", "Result");
                printTableSeparatorRow();
                methodTestResult.getValue().entrySet().forEach(elementTestResult -> {
                    printTableRow(
                            testResult.getKey(),
                            methodTestResult.getKey().getWrappedClass().getSimpleName(),
                            elementTestResult.getKey().getWrappedClass().getName(),
                            elementTestResult.getValue()
                    );
                });
            });
        });
    }

    private static void printTableSeparatorRow() {
        System.out.printf(
                new StringBuilder()
                        .append(tableIndent)
                        .append("%")
                        .append(Main.SCREEN_WIDTH_IN_CHARS)
                        .append("s \n")
                        .toString(),
                StringUtils.getRepeatedUntil(tableRowSeparator, Main.SCREEN_WIDTH_IN_CHARS + 10)
        );
    }

    private static void printTableRow(String col1, String col2, String col3, String col4) {
        System.out.printf(
                new StringBuilder()
                        .append(tableIndent)
                        .append("%")
                        .append(column1Width)
                        .append("s").append(tableColumnSeparator).append("%")
                        .append(column2Width)
                        .append("s").append(tableColumnSeparator).append("%")
                        .append(column3Width)
                        .append("s").append(tableColumnSeparator).append("%")
                        .append(column4Width)
                        .append("s \n")
                        .toString(),
                col1, col2, col3, col4
        );
    }

    private static Map<String, Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>>> getTestResults() {
        Map<String, Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>>> methodToVisitors =
                new LinkedHashMap<>();

        methodToVisitors.put("Export", getTestExports());
        methodToVisitors.put("Visit", getTestVisits());
        methodToVisitors.put("Accept", getTestAccepts());

        return methodToVisitors;
    }

    private static Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> getTestExports() {
        Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> visitorsToElements = new LinkedHashMap<>();

        visitorsToElements.put(new ClassWrapper<>(Exporter.class), getTestExport(visitorCollection.exporter));

        return visitorsToElements;
    }

    private static Map<ClassWrapper<? extends I1>, String> getTestExport(Exporter exporter) {
        Map<ClassWrapper<? extends I1>, String> elementsToResults = new LinkedHashMap<>();

        elementsToResults.put(new ClassWrapper<>(I1.class), exporter.export(visitorCollection.i1));
        elementsToResults.put(new ClassWrapper<>(A11.class), exporter.export(visitorCollection.a11));
        elementsToResults.put(new ClassWrapper<>(B111.class), exporter.export(visitorCollection.b111));
        elementsToResults.put(new ClassWrapper<>(B112.class), exporter.export(visitorCollection.b112));

        IntStream.range(0, visitorCollection.i1s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper<>(visitorCollection.i1s[i].getKey()),
                        exporter.export(visitorCollection.i1s[i].getValue())
                ));

        IntStream.range(0, visitorCollection.a11s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper(visitorCollection.a11s[i].getKey()),
                        exporter.export(visitorCollection.a11s[i].getValue())
                ));

        return elementsToResults;
    }

    private static Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> getTestVisits() {
        Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> visitorsToElements = new LinkedHashMap<>();

        visitorsToElements.put(new ClassWrapper<>(I1Visitor.class), getTestVisit(visitorCollection.i1Visitor));
        visitorsToElements.put(new ClassWrapper<>(A11Visitor.class), getTestVisit(visitorCollection.a11Visitor));
        visitorsToElements.put(new ClassWrapper<>(B111Visitor.class), getTestVisit(visitorCollection.b111Visitor));
        visitorsToElements.put(new ClassWrapper<>(B112Visitor.class), getTestVisit(visitorCollection.b112Visitor));

        IntStream.range(0, visitorCollection.i1Visitors.length)
                .forEach(i -> visitorsToElements.put(
                        new ClassWrapper<>(visitorCollection.i1Visitors[i].getKey()),
                        getTestVisit(visitorCollection.i1Visitors[i].getValue())
                ));

        IntStream.range(0, visitorCollection.a11Visitors.length)
                .forEach(i -> visitorsToElements.put(
                        new ClassWrapper<>(visitorCollection.a11Visitors[i].getKey()),
                        getTestVisit(visitorCollection.a11Visitors[i].getValue())
                ));

        return visitorsToElements;
    }

    private static Map<ClassWrapper<? extends I1>, String> getTestVisit(I1Visitor i1Visitor) {
        Map<ClassWrapper<? extends I1>, String> elementsToResults = new LinkedHashMap<>();

        elementsToResults.put(new ClassWrapper<>(I1.class), i1Visitor.visit(visitorCollection.i1));
        elementsToResults.put(new ClassWrapper<>(A11.class), i1Visitor.visit(visitorCollection.a11));
        elementsToResults.put(new ClassWrapper<>(B111.class), i1Visitor.visit(visitorCollection.b111));
        elementsToResults.put(new ClassWrapper<>(B112.class), i1Visitor.visit(visitorCollection.b112));

        IntStream.range(0, visitorCollection.i1s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper<>(visitorCollection.i1s[i].getKey()),
                        i1Visitor.visit(visitorCollection.i1s[i].getValue())
                ));

        IntStream.range(0, visitorCollection.a11s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper(visitorCollection.a11s[i].getKey()),
                        i1Visitor.visit(visitorCollection.a11s[i].getValue())
                ));

        return elementsToResults;
    }

    private static Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> getTestAccepts() {
        Map<ClassWrapper<? extends ICommonVisitor>, Map<ClassWrapper<? extends I1>, String>> visitorsToElements = new LinkedHashMap<>();

        visitorsToElements.put(new ClassWrapper<>(I1Visitor.class), getTestAccept(visitorCollection.i1Visitor));
        visitorsToElements.put(new ClassWrapper<>(A11Visitor.class), getTestAccept(visitorCollection.a11Visitor));
        visitorsToElements.put(new ClassWrapper<>(B111Visitor.class), getTestAccept(visitorCollection.b111Visitor));
        visitorsToElements.put(new ClassWrapper<>(B112Visitor.class), getTestAccept(visitorCollection.b112Visitor));

        IntStream.range(0, visitorCollection.i1Visitors.length)
                .forEach(i -> visitorsToElements.put(
                        new ClassWrapper<>(visitorCollection.i1Visitors[i].getKey()),
                        getTestAccept(visitorCollection.i1Visitors[i].getValue())
                ));

        IntStream.range(0, visitorCollection.a11Visitors.length)
                .forEach(i -> visitorsToElements.put(
                        new ClassWrapper<>(visitorCollection.a11Visitors[i].getKey()),
                        getTestAccept(visitorCollection.a11Visitors[i].getValue())
                ));

        return visitorsToElements;
    }

    private static Map<ClassWrapper<? extends I1>, String> getTestAccept(I1Visitor i1Visitor) {
        Map<ClassWrapper<? extends I1>, String> elementsToResults = new LinkedHashMap<>();

        elementsToResults.put(new ClassWrapper<>(I1.class), visitorCollection.i1.accept(i1Visitor));
        elementsToResults.put(new ClassWrapper<>(A11.class), visitorCollection.a11.accept(i1Visitor));
        elementsToResults.put(new ClassWrapper<>(B111.class), visitorCollection.b111.accept(i1Visitor));
        elementsToResults.put(new ClassWrapper<>(B112.class), visitorCollection.b112.accept(i1Visitor));

        IntStream.range(0, visitorCollection.i1s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper<>(visitorCollection.i1s[i].getKey()),
                        visitorCollection.i1s[i].getValue().accept(i1Visitor)
                ));

        IntStream.range(0, visitorCollection.a11s.length)
                .forEach(i -> elementsToResults.put(
                        new ClassWrapper(visitorCollection.a11s[i].getKey()),
                        visitorCollection.a11s[i].getValue().accept(i1Visitor)
                ));

        return elementsToResults;
    }
}
