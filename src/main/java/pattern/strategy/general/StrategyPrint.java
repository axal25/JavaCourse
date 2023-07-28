package pattern.strategy.general;

public class StrategyPrint implements IStrategy<Object> {
    @Override
    public void apply(Object o) {
        System.out.println(this.getClass().getSimpleName() + "#apply(Object o) - implements IStrategy<Object> - object: " + o.toString());
    }
}
