package pattern.visitor.v2023may21;

import java.util.List;

public class VisitorMain {
    public static void main(String[] args) {
        List<IVisited<String>> visiteds = List.of(new Visited1(), new Visited2());

        VisitorJsonClient visitorJsonClient = new VisitorJsonClient();
        print(visitorJsonClient, visiteds);

        VisitorXmlClient visitorXmlClient = new VisitorXmlClient();
        print(visitorXmlClient, visiteds);
    }

    private static void print(IVisitorClient<String> visitorClient, List<IVisited<String>> visiteds) {
        String result = visitorClient.visit(visiteds);
        System.out.println("result: \r\n" + result);
    }
}
