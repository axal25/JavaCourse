package solid;

import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronymPracticalExampleSourceCode {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        //TODO: Implement simple reading of source code for practical example
        try {
            throw new UnsupportedOperationException("TODO: Implement simple reading of source code for practical example");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
