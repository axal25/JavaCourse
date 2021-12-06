package visitor.main;

import utils.StaticUtils;
import visitor.printer.VisitorPrinter;

import java.lang.invoke.MethodHandles;

public class VisitorMain {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());
    
    public static void main() {
        staticUtils.printMethodSignature("main");

        VisitorPrinter.test();
    }
}
