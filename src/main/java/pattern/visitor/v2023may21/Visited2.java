package pattern.visitor.v2023may21;

public class Visited2 implements IVisited<String> {
    @Override
    public String accept(IVisitor<String> visitor) {
        return visitor.visit(this);
    }
}
