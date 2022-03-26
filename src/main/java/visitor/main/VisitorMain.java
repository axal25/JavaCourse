package visitor.main;

import utils.StaticOrMainMethodUtils;
import visitor.printer.VisitorPrinter;

import java.lang.invoke.MethodHandles;

public class VisitorMain {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        VisitorPrinter.test();
    }
}
