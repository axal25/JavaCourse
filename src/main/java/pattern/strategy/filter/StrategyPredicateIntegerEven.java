package pattern.strategy.filter;

public class StrategyPredicateIntegerEven implements IStrategyPredicate<Integer> {
    @Override
    public boolean testPredicate(Integer i) {
        return i % 2 == 0;
    }
}
