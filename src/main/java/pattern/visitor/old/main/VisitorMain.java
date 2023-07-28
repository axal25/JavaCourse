package pattern.visitor.old.main;

import utils.StaticOrMainMethodUtils;
import pattern.visitor.old.printer.VisitorPrinter;

import java.lang.invoke.MethodHandles;

public class VisitorMain {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        VisitorPrinter.test();
    }
}
