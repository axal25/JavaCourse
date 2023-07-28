package pattern.visitor.old.element;

public class B111 extends A11 {
    private final String identifier = "B111";

    @Override
    public String getIdentifier() {
        return ElemUtils.getIdentifier(this, identifier);
    }
}
