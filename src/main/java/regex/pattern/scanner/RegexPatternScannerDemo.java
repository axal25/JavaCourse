package regex.pattern.scanner;

import utils.StaticOrMainMethodUtils;
import utils.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegexPatternScannerDemo {
    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    private static final int horizon = 0;

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticOrMainMethodUtils.printMainSignature();
        testPatterns();
    }

    private static void testPatterns() {
        testPattern(Pattern.compile("[+-\\\\*/]"));
        testPattern(Pattern.compile("[+-\\\\*/]+"));
        testPattern(Pattern.compile("[+-/]"));
        testPattern(Pattern.compile("[+-/*]"));
        testPattern(Pattern.compile("[+-]"));
        testPattern(Pattern.compile("[+-]+"));
    }

    private static void testPattern(Pattern pattern) {
        List<String> testeds = Arrays.asList("+", "-", "*", "/", "--", "++", "-+", "+-", "//", "**", "+1", "-1", "1", "2", "33", "-11", "+11", "--11", "++11");
        int[] i = new int[1];
        i[0] = 0;
        System.out.println(String.format(
                "---------------------------------------------- %30s ----------------------------------------------",
                String.format("\"%s\"", pattern.toString()
                )));
        testeds.forEach(tested -> {
            i[0]++;
            testPattern(pattern, tested, i[0]);
        });
    }

    private static void testPattern(Pattern pattern, String tested, int i) {
        String[] outputs = new String[3];
        outputs[0] = StringUtils.EMPTY;
        outputs[1] = StringUtils.EMPTY;
        outputs[2] = StringUtils.EMPTY;
        if (pattern.matcher(tested).matches()) {
            outputs[0] = String.format("Matcher matches \"%s\".", tested);
        }
        if (pattern.matcher(tested).find()) {
            outputs[1] = String.format("Matcher finds \"%s\".", tested);
        }
        String found;
        if ((found = new Scanner(tested).findWithinHorizon(pattern, horizon)) != null) {
            outputs[2] = String.format("Scanner findsWithHorizon \"%s\". Found: \"%s\".", tested, found);
        }
        System.out.println(String.format("%3d. %10s | %35s | %35s | %35s", i, tested, outputs[0], outputs[1], outputs[2]));
    }
}
