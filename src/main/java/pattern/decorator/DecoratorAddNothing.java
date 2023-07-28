package pattern.decorator;

public class DecoratorAddNothing extends AbstractDecorator {

    public DecoratorAddNothing(IDecoratee decoratee) {
        super(decoratee);
    }

    public DecoratorAddNothing() {
        super();
    }

    @Override
    public void print() {
        printFrom();
        super.print();
    }
}
