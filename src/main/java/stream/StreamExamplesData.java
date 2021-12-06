package stream;

import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class StreamExamplesData {
    static final String[] NAMES = new String[]{
            "Al", "Ally",
            "Bal", "Bally",
            "Cal", "Cally",
            "Dal", "Dally",
            "Gal", "Gally",
            "Eal", "Eally"
    };
    private static final int[] ints1 = new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    };
    private static final int[] ints2 = IntStream.range(0, 10).toArray();
    static final int[] INTS = getInts();

    private static int[] getInts() {
        validateInts1AndInts2HaveEqualElements();
        return ints2;
    }

    private static void validateInts1AndInts2HaveEqualElements() {
        if (ints1.length != ints2.length) {
            throw new RuntimeException(
                    String.format(
                            "Programming logic error. ints1.length: %d != ints2.length: %d",
                            ints1.length,
                            ints2.length
                    )
            );
        }
        IntStream.range(0, Math.min(ints1.length, ints2.length))
                .filter(i -> ints1[i] != ints2[i])
                .findAny()
                .ifPresent(i -> {
                    throw new RuntimeException(
                            (String) IntStream.range(0, Math.min(ints1.length, ints2.length))
                                    .boxed()
                                    .collect(
                                            Collectors.collectingAndThen(
                                                    Collector.of(
                                                            StringBuilder::new,
                                                            (sb, j) -> sb
                                                                    .append(StringUtils.NL)
                                                                    .append(StringUtils.TAB)
                                                                    .append(j + 1)
                                                                    .append(".")
                                                                    .append(i == j ? "!!!" : StringUtils.EMPTY)
                                                                    .append(ints1[j])
                                                                    .append(i == j ? " != " : " == ")
                                                                    .append(ints2[j])
                                                                    .append(i + 1 == j ? "!!!" : StringUtils.EMPTY),
                                                            UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                                            StringBuilder::toString
                                                    ),
                                                    areasString -> new StringBuilder()
                                                            .append("Programming logic error.")
                                                            .append(areasString)
                                                            .toString()
                                            )
                                    )
                    );
                });
    }
}
