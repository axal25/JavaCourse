package utils.exceptions;

import java.util.function.BinaryOperator;

public class UnsupportedCollectorParallelLeftFoldOperation extends UnsupportedOperationException {
    private UnsupportedCollectorParallelLeftFoldOperation() {
        super("Collector's fold-left operation cannot be supported in parallel.", new CollectorOperation(new ParallelOperation(new FoldLeftOperation(null))));
    }

    public static <T> BinaryOperator<T> getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation() {
        return (t, u) -> {
            throw new UnsupportedCollectorParallelLeftFoldOperation();
        };
    }
}
