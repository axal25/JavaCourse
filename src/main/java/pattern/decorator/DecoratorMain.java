package pattern.decorator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DecoratorMain {
    public static void main(String[] args) {
        printDecoratorClient();

        printDecoratorClient(Decoratee.class);

        printDecoratorClient(DecoratorAddNothing.class);
        printDecoratorClient(Decoratee.class, DecoratorAddNothing.class);

        printDecoratorClient(DecoratorAddAfter.class);
        printDecoratorClient(Decoratee.class, DecoratorAddAfter.class);

        printDecoratorClient(DecoratorAddBefore.class);
        printDecoratorClient(Decoratee.class, DecoratorAddBefore.class);

        printDecoratorClient(Decoratee.class, DecoratorAddNothing.class, DecoratorAddAfter.class, DecoratorAddBefore.class);
    }

    @SafeVarargs
    private static void printDecoratorClient(Class<? extends IDecoratee>... decoratees) {
        List<String> decorateesClassSimpleNames = Arrays.stream(decoratees)
                .map(Class::getSimpleName).
                collect(Collectors.toList());
        System.out.println("DecoratorClient.print(" + decorateesClassSimpleNames + "): " + decorateesClassSimpleNames);
        DecoratorClient.print(decoratees);

        System.out.println();
    }
}
