package pattern.visitor.v2023may21;

public interface IVisited<RETURN_TYPE> {

    RETURN_TYPE accept(IVisitor<RETURN_TYPE> visitor);
}
