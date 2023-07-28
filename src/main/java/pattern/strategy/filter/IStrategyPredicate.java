package pattern.strategy.filter;

public interface IStrategyPredicate<T> {
    boolean testPredicate(T t);
}
