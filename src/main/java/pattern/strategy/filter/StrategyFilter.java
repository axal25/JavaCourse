package pattern.strategy.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StrategyFilter {
    public static <T> Stream<T> getInversedFilteringStream(Collection<T> ts, Predicate<? super T> predicateForT) {
        return ts.stream().filter((t) -> !predicateForT.test(t));
    }

    public static <T> Stream<T> getFilteringStream(Collection<T> ts, IStrategyPredicate<T> predicate) {
        List<T> newTs = new ArrayList<>();
        for(T t : ts) {
            if(predicate.testPredicate(t)) {
                newTs.add(t);
            }
        }
        return newTs.stream();
    }
}
