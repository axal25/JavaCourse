package visitor.element;

import visitor.visitor.A11Visitor;
import visitor.visitor.B111Visitor;
import visitor.visitor.B112Visitor;
import visitor.visitor.I1Visitor;

public interface I1 {
    public final String identifier = "I1";

    default public String getIdentifier() {
        return ElemUtils.getIdentifier(this, "I1");
    }

    default public String accept(I1Visitor iVisitor) {
        return "[I1Visitor]" + ElemUtils.elemAcceptVisitor(this, iVisitor);
    }

    default public String accept(A11Visitor a11Visitor) {
        return "[A11Visitor]" + ElemUtils.elemAcceptVisitor(this, a11Visitor);
    }

    default public String accept(B111Visitor b111Visitor) {
        return "[B111Visitor]" + ElemUtils.elemAcceptVisitor(this, b111Visitor);
    }

    default public String accept(B112Visitor b112Visitor) {
        return "[B112Visitor]" + ElemUtils.elemAcceptVisitor(this, b112Visitor);
    }
}