package pattern.visitor.v2023may21;

import java.util.List;
import java.util.stream.Collectors;

public class VisitorXmlClient implements IVisitorClient<String> {
    private final VisitorXml visitor = new VisitorXml();
    @Override
    public String visit(List<IVisited<String>> visiteds) {
        return String.format("<visiteds>%s</visiteds>",
                visiteds == null ? "" : visiteds.stream()
                        .map(visited -> visited.accept(visitor))
                        .map(visited -> "\t" + visited)
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.joining(",\r\n"),
                                        (a) -> "".equals(a) ? a : String.format("\r\n%s\r\n", a)
                                )));
    }
}
