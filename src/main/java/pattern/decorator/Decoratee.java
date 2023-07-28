package pattern.decorator;

public class Decoratee implements IDecoratee {
    @Override
    public void print() {
        System.out.println("printing from " + Decoratee.class.getSimpleName());
    }
}
