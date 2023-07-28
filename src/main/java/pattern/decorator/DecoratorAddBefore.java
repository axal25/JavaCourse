package pattern.decorator;

public class DecoratorAddBefore extends AbstractDecorator {

    public DecoratorAddBefore(IDecoratee decoratee) {
        super(decoratee);
    }

    public DecoratorAddBefore() {
        super();
    }

    @Override
    public void print() {
        printFrom();
        printBeforeFrom();
        super.print();
    }
}
