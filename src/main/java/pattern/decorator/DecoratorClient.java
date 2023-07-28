package pattern.decorator;

import java.util.Arrays;
import java.util.List;

public class DecoratorClient {
    @SafeVarargs
    public static void print(Class<? extends IDecoratee>... decorateesArray) {
        List<Class<? extends IDecoratee>> decoratees = decorateesArray == null
                ? List.of()
                : Arrays.asList(decorateesArray);

        IDecoratee decoratee = null;

        if(decoratees.contains(Decoratee.class)) {
            decoratee = new Decoratee();
        }

        if(decoratees.contains(DecoratorAddNothing.class)) {
            decoratee = new DecoratorAddNothing(decoratee);
        }

        if(decoratees.contains(DecoratorAddAfter.class)) {
            decoratee = new DecoratorAddAfter(decoratee);
        }

        if(decoratees.contains(DecoratorAddBefore.class)) {
            decoratee = new DecoratorAddBefore(decoratee);
        }

        if(decoratee != null) {
            decoratee.print();
        }
    }
}
