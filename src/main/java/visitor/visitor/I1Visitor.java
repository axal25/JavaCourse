package visitor.visitor;

import visitor.element.A11;
import visitor.element.B111;
import visitor.element.B112;
import visitor.element.I1;
import visitor.map.common.ICommonVisitor;
import visitor.visitor.utils.VisitorUtils;

public interface I1Visitor extends ICommonVisitor {

    public final String methodName = "visit";

    default public String visit(I1 i1) {
//        return "[I1]" + VisitorUtils.visitorVisitElem(this, methodName, i1);
        return "[I1]" + VisitorUtils.visitorVisitElem(I1Visitor.class, methodName, I1.class, i1);
    }

    default public String visit(A11 a11) {
        return "[A11]" + VisitorUtils.visitorVisitElem(this, methodName, a11);
    }

    default public String visit(B111 b111) {
        return "[B111]" + VisitorUtils.visitorVisitElem(this, methodName, b111);
    }

    default public String visit(B112 b112) {
        return "[B112]" + VisitorUtils.visitorVisitElem(this, methodName, b112);
    }
}
