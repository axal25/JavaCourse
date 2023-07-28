package pattern.visitor.old.element;

public abstract class A11 implements I1 {
    private final String identifier = "A11";

    @Override
    public String getIdentifier() {
        return ElemUtils.getIdentifier(this, identifier);
    }
}
