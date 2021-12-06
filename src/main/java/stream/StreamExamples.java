package stream;

import utils.StaticUtils;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static stream.StreamExamplesData.INTS;
import static stream.StreamExamplesData.NAMES;

/**
 * https://www.youtube.com/watch?v=t1-YZ6bF-g0
 */
public class StreamExamples {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");
        print0To10();
        print0To10SkipFirst5();
        printSum0To10();
        printFirstIfFoundOutOfSortedStringsCBA();
        printNamesStartingWithG();
        print0to10SquaredAverage();
        printNamesLowercaseStartingWithG();
    }

    private static void print0To10() {
        staticUtils.printMethodSignature("print0To10");
        Arrays.stream(INTS).forEach(System.out::print);
        System.out.println();
    }

    private static void print0To10SkipFirst5() {
        staticUtils.printMethodSignature("print0To10SkipFirst5");
        Arrays.stream(INTS).skip(5).forEach(System.out::print);
        System.out.println();
    }

    private static void printSum0To10() {
        staticUtils.printMethodSignature("printSum0To10");
        System.out.println(Arrays.stream(INTS).sum());
    }

    private static void printFirstIfFoundOutOfSortedStringsCBA() {
        staticUtils.printMethodSignature("printFirstIfFoundOutOfSortedStringsCBA");

        String[] strings = new String[]{"C", "B", "A"};

        System.out.print("[\"C\", \"B\", \"A\"] array of Strings (concatenated): ");
        Arrays.stream(strings).forEach(System.out::print);
        System.out.println();

        System.out.print("First in sorted C, B, A arrays of Strings: ");
        Stream.of(strings).sorted().findFirst().ifPresent(System.out::println);
    }

    private static void printNamesStartingWithG() {
        staticUtils.printMethodSignature("printNamesStartingWithG");

        System.out.print("Concatenated names: ");
        Arrays.stream(NAMES).forEach(System.out::print);
        System.out.println();

        System.out.print("Concatenated names starting with G: ");
        Arrays.stream(NAMES)
                .filter(s -> s.startsWith("G"))
                .sorted()
                .forEach(System.out::print);
        System.out.println();

        System.out.print("Names: ");
        IntStream.range(0, NAMES.length)
                .mapToObj(i -> i != 0
                        ? String.format(", (%d.) %s", i, NAMES[i])
                        : String.format("(%d.) %s", i, NAMES[i])
                ).forEach(System.out::print);
        System.out.println();

        System.out.print("Indexes of names starting with G: ");
        int[] indexes = IntStream.range(0, NAMES.length)
                .filter(i -> NAMES[i].startsWith("G"))
                .toArray();
        Arrays.stream(indexes).forEach(System.out::print);
        System.out.println();

        System.out.print("Names starting with G: ");
        IntStream.range(0, indexes.length)
                .mapToObj(
                        j -> j != 0
                                ? String.format(", [%d.] (%d.) %s", j, indexes[j], NAMES[indexes[j]])
                                : String.format("[%d.] (%d.) %s", j, indexes[j], NAMES[indexes[j]])
                ).forEach(System.out::print);
        System.out.println();
    }

    private static void print0to10SquaredAverage() {
        staticUtils.printMethodSignature("print0to10SquaredAverage");

        System.out.print("0 to 10 int array (concatenated): ");
        Arrays.stream(INTS).forEach(System.out::print);
        System.out.println();

        System.out.print("0 to 10 squared average: ");
        Arrays.stream(INTS)
                .map(x -> x * x)
                .average()
                .ifPresent(System.out::println);
    }

    private static void printNamesLowercaseStartingWithG() {
        staticUtils.printMethodSignature("printNamesLowercaseStartingWithG");

        System.out.print("Names stating with G lowercase [first filter by starts with G, then map to lowercase] (concatenated): ");
        Arrays.asList(NAMES)
                .stream()
                .filter(s -> s.startsWith("G"))
                .map(String::toLowerCase)
                .forEach(System.out::print);
        System.out.println();

        System.out.print("Names lowercase stating with g [first map to lower case, then filter by starts with g] (concatenated): ");
        Arrays.asList(NAMES)
                .stream()
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("g"))
                .forEach(System.out::print);
        System.out.println();
    }
}
