package pattern.visitor.v2023may21;

import java.util.List;

public interface IVisitorClient<RETURN_TYPE> {
    RETURN_TYPE visit(List<IVisited<RETURN_TYPE>> visiteds);
}
