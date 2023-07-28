package pattern.visitor.v2023may21;

public interface IVisitor<RETURN_TYPE> {
    RETURN_TYPE visit(Visited1 visited1);
    RETURN_TYPE visit(Visited2 visited2);
}
