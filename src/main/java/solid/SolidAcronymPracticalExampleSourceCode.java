package solid;

import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronymPracticalExampleSourceCode {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");

        //TODO: Implement simple reading of source code for practical example
        try {
            throw new UnsupportedOperationException("TODO: Implement simple reading of source code for practical example");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
