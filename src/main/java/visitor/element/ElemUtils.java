package visitor.element;

import utils.ClassMethodUtils;
import visitor.visitor.I1Visitor;

class ElemUtils {

    static String elemAcceptVisitor(I1 elem, I1Visitor iVisitor) {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassAndMethodAndArgs(elem.getClass(), "accept", iVisitor.getClass()))
                .append(" | ")
                .append(iVisitor.visit(elem))
                .toString();
    }

    static String getIdentifier(I1 i1, String identifier) {
        return new StringBuilder()
                .append("identification: ")
                .append(ClassMethodUtils.getClassSimpleName(i1.getClass()))
                .append(" == ")
                .append(identifier)
                .toString();
    }
}
