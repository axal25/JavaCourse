package interview.arrays.longs.written.addition;

import java.util.stream.IntStream;

public class ArraysLongsWrittenAddition {

    public static int[] add(int[] first, int[] second) {
        int[] smaller;
        int[] bigger;
        if (first.length > second.length) {
            smaller = second;
            bigger = first;
        } else {
            smaller = first;
            bigger = second;
        }

        int lengthDifference = bigger.length - smaller.length;
        int[] sumInverse = bigger.length == 0
                ? new int[0]
                : IntStream.range(0, bigger.length)
                .map(i -> Math.abs(i - (bigger.length - 1)))
                .map(i -> i - lengthDifference > -1 ? bigger[i] + smaller[i - lengthDifference] : bigger[i]
                ).toArray();

        int[] sum = sumInverse.length == 0
                ? new int[0]
                : IntStream.range(0, sumInverse.length)
                .map(i -> Math.abs(i - (bigger.length - 1)))
                .map(i -> sumInverse[i])
                .toArray();

        IntStream.range(0, sum.length - 1)
                .map(i -> Math.abs(i - (sum.length - 1)))
                .forEach(i -> {
                    sum[i - 1] = sum[i - 1] + sum[i] / 10;
                    sum[i] = sum[i] % 10;
                });

        return sum;
    }
}
