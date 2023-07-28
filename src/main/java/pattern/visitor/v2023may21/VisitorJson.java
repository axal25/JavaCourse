package pattern.visitor.v2023may21;

public class VisitorJson implements IVisitor<String> {
    @Override
    public String visit(Visited1 visited1) {
        return String.format("%s: {%s}", Visited1.class.getSimpleName(), visited1);
    }

    @Override
    public String visit(Visited2 visited2) {
        return String.format("%s: {%s}", Visited2.class.getSimpleName(), visited2);
    }
}
