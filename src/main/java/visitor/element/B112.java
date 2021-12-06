package visitor.element;

public class B112 extends A11 {
    private final String identifier = "B112";

    @Override
    public String getIdentifier() {
        return ElemUtils.getIdentifier(this, identifier);
    }
}
