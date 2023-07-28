package pattern.strategy.filter;

public class StrategyPredicateIntegerPositive implements IStrategyPredicate<Integer> {
    @Override
    public boolean testPredicate(Integer i) {
        return i > 0;
    }
}
