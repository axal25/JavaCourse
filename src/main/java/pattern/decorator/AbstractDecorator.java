package pattern.decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class AbstractDecorator implements IDecoratee {
    private static int instanceCounter = 0;
    private final int instanceIndex = instanceCounter++;
    private IDecoratee decoratee;

    @Override
    public void print() {
        System.out.println("printing from " + AbstractDecorator.class.getSimpleName() + " (" + instanceIndex + ")");
        if(decoratee != null) {
            decoratee.print();
        }
    }

    protected void printFrom() {
        System.out.println("printing from " + this.getClass().getSimpleName() + " (" + instanceIndex + ")");
    }

    protected void printBeforeFrom() {
        System.out.println("printing before from " + this.getClass().getSimpleName() + " (" + instanceIndex + ")");
    }

    protected void printAfterFrom() {
        System.out.println("printing after from " + this.getClass().getSimpleName() + " (" + instanceIndex + ")");
    }
}
