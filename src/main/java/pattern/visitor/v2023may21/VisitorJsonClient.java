package pattern.visitor.v2023may21;

import java.util.List;
import java.util.stream.Collectors;

public class VisitorJsonClient implements IVisitorClient<String> {
    private final VisitorJson visitor = new VisitorJson();
    @Override
    public String visit(List<IVisited<String>> visiteds) {
        return String.format("[%s]",
                visiteds == null ? "" : visiteds.stream().map(visited -> "\t"+ visited.accept(visitor)).collect(
                        Collectors.collectingAndThen(
                                Collectors.joining(",\r\n"),
                                (a) -> "".equals(a) ? a : String.format("\r\n%s\r\n", a)
                        )));
    }
}
