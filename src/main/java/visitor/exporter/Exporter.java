package visitor.exporter;

import visitor.element.A11;
import visitor.element.B111;
import visitor.element.B112;
import visitor.element.I1;
import visitor.map.common.ICommonVisitor;
import visitor.visitor.utils.VisitorUtils;

public class Exporter implements ICommonVisitor {

    private final String methodName = "export";

    public String export(I1 i1) {
        return VisitorUtils.visitorVisitElem(this, methodName, i1);
    }

    public String export(A11 a11) {
        return VisitorUtils.visitorVisitElem(this, methodName, a11);
    }

    public String export(B111 b111) {
        return VisitorUtils.visitorVisitElem(this, methodName, b111);
    }

    public String export(B112 b112) {
        return VisitorUtils.visitorVisitElem(this, methodName, b112);
    }
}
