package pattern.strategy;

import pattern.strategy.filter.StrategyFilter;
import pattern.strategy.filter.StrategyPredicateIntegerEven;
import pattern.strategy.filter.StrategyPredicateIntegerPositive;
import pattern.strategy.general.StrategyApplier;
import pattern.strategy.general.StrategyPrint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrategyMain {
    public static void main(String[] args) {
        List<Integer> input = IntStream.range(-10, 11)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("input: " + input);

        List<Integer> onlyEven = input.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("onlyEven: " + onlyEven);

        List<Integer> onlyPositive = input.stream()
                .filter(i -> i > 0)
                .collect(Collectors.toList());
        System.out.println("onlyPositive: " + onlyPositive);

        List<Integer> onlyNotOdd = StrategyFilter.getInversedFilteringStream(input,
                i -> i % 2 != 0)
                .collect(Collectors.toList());
        System.out.println("onlyNotOdd (even): " + onlyNotOdd);

        List<Integer> onlyNotNegativeAndNotZero = StrategyFilter.getInversedFilteringStream(input,
                        i -> i <= 0)
                .collect(Collectors.toList());
        System.out.println("onlyNotNegativeAndNotZero (positive): " + onlyNotNegativeAndNotZero);

        List<Integer> onlyEvenStrategyPredicateIntegerEven = StrategyFilter.getFilteringStream(input,
                new StrategyPredicateIntegerEven())
                .collect(Collectors.toList());
        System.out.println("onlyEvenStrategyPredicateIntegerEven (even): " + onlyEvenStrategyPredicateIntegerEven);

        List<Integer> onlyEvenStrategyPredicateIntegerPositive = StrategyFilter.getFilteringStream(input,
                        new StrategyPredicateIntegerPositive())
                .collect(Collectors.toList());
        System.out.println("onlyEvenStrategyPredicateIntegerPositive (positive): " + onlyEvenStrategyPredicateIntegerPositive);

        List<Integer> onlyEvenStrategyPredicateLambdaNotNegativeAndNotZero = StrategyFilter.getFilteringStream(input,
                        (i) -> !(i <= 0))
                .collect(Collectors.toList());
        System.out.println("onlyEvenStrategyPredicateLambdaNotNegativeAndNotZero (positive): " + onlyEvenStrategyPredicateLambdaNotNegativeAndNotZero);

        StrategyApplier<Object> strategyApplier = new StrategyApplier<>();
        String objectToHaveStrategyBeApplied = "objectToHaveStrategyBeApplied";
        strategyApplier.applyStrategy(objectToHaveStrategyBeApplied, new StrategyPrint());
        new StrategyApplier<>().applyStrategy("aString", (o) ->
                System.out.println("Lambda (o) -> System.out.println(...) - object: " + o));
    }
}
