package visitor.visitor.utils;

import utils.ClassMethodUtils;
import visitor.element.I1;
import visitor.map.common.ICommonVisitor;

public class VisitorUtils {

    public static String visitorVisitElem(ICommonVisitor iCommonVisitor, String methodName, I1 i1) {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassAndMethodAndArgs(iCommonVisitor.getClass(), methodName, i1.getClass()))
                .append(" >> ")
                .append(i1.getIdentifier())
                .toString();
    }

    public static String visitorVisitElem(Class<? extends ICommonVisitor> visitorClass, String methodName, Class<? extends I1> elemClass, I1 i1) {
        return new StringBuilder()
                .append(ClassMethodUtils.getClassAndMethodAndArgs(visitorClass, methodName, elemClass))
                .append(" >> ")
                .append(i1.getIdentifier())
                .toString();
    }
}
