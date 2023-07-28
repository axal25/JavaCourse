package pattern.visitor.v2023may21;

public class VisitorXml implements IVisitor<String> {
    @Override
    public String visit(Visited1 visited1) {
        String classNameXml = getXmlClassName(Visited1.class);
        return String.format("<%s>%s</%s>", classNameXml, visited1, classNameXml);
    }

    @Override
    public String visit(Visited2 visited2) {
        String classNameXml = getXmlClassName(Visited2.class);
        return String.format("<%s>%s</%s>", classNameXml, visited2, classNameXml);
    }

    private String getXmlClassName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }
}
