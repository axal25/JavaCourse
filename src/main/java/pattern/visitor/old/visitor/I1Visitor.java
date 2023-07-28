package pattern.visitor.old.visitor;

import pattern.visitor.old.element.B111;
import pattern.visitor.old.element.I1;
import pattern.visitor.old.map.common.ICommonVisitor;
import pattern.visitor.old.visitor.utils.VisitorUtils;
import pattern.visitor.old.element.A11;
import pattern.visitor.old.element.B112;

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
