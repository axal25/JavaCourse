package stream;

import utils.StaticUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Source: https://stackoverflow.com/questions/29210176/can-a-collectors-combiner-function-ever-be-used-on-sequential-streams
 * A careful reading of the streams implementation code in ReduceOps.java (http://hg.openjdk.java.net/jdk8/jdk8/jdk/file/jdk8-b132/src/share/classes/java/util/stream/ReduceOps.java)
 * reveals that the combine function is called only when a ReduceTask completes, and ReduceTask instances are used only when evaluating a pipeline in parallel. Thus, in the current implementation, the combiner is never called when evaluating a sequential pipeline.
 * There is nothing in the specification that guarantees this, however. A Collector is an interface that makes requirements on its implementations, and there are no exemptions granted for sequential streams. Personally, I find it difficult to imagine why sequential pipeline evaluation might need to call the combiner, but someone with more imagination than me might find a clever use for it, and implement it. The specification allows for it, and even though today's implementation doesn't do it, you still have to think about it.
 * This should not surprising. The design center of the streams API is to support parallel execution on an equal footing with sequential execution. Of course, it is possible for a program to observe whether it is being executed sequentially or in parallel. But the design of the API is to support a style of programming that allows either.
 * If you're writing a collector and you find that it's impossible (or inconvenient, or difficult) to write an associative combiner function, leading you to want to restrict your stream to sequential execution, maybe this means you're heading in the wrong direction. It's time to step back a bit and think about approaching the problem a different way.
 * A common reduction-style operation that doesn't require an associative combiner function is called fold-left. The main characteristic is that the fold function is applied strictly left-to-right, proceeding one at a time. I'm not aware of a way to parallelize fold-left.
 * When people try to contort collectors the way we've been talking about, they're usually looking for something like fold-left. The Streams API doesn't have direct API support for this operation, but it's pretty easy to write. For example, suppose you want to reduce a list of strings using this operation: repeat the first string and then append the second. It's pretty easy to demonstrate that this operation isn't associative:
 */
public class StreamCollectorSequentialVsParallel {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");

        final Collector<Integer, ?, List<Integer>> sequentialCollector = Collector.of(
                ArrayList::new,
                List::add,
                UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation()
        );

        // approach sequentialStream
        Stream<Integer> sequentialStream = IntStream.range(0, 10_000_000).boxed();

        System.out.println("isParallel:" + sequentialStream.isParallel());
        List<Integer> sequentialInts = sequentialStream.collect(sequentialCollector);

        // approach parallelStream
        Stream<Integer> parallelStream = IntStream.range(0, 10_000_000).parallel().boxed();

        System.out.println("isParallel:" + parallelStream.isParallel());
        try {
            List<Integer> parallelInts = parallelStream.collect(sequentialCollector);
        } catch (UnsupportedCollectorParallelLeftFoldOperation e) {
            e.printStackTrace();
        }
    }
}
