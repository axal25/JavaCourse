package pattern.strategy.general;

public class StrategyApplier <T> {
    public void applyStrategy(T t, IStrategy<T> strategyT) {
        strategyT.apply(t);
    }
}
