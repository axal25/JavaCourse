package pattern.decorator;

public class DecoratorAddAfter extends AbstractDecorator {

    public DecoratorAddAfter(IDecoratee decoratee) {
        super(decoratee);
    }

    public DecoratorAddAfter() {
        super();
    }

    @Override
    public void print() {
        printFrom();
        super.print();
        printAfterFrom();
    }
}
