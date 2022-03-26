package solid;

import input.read.menu.Menu;
import input.read.menu.Option;
import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronym {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        Menu.open(new Option[]{
                new Option("SOLID Acronym - Theory", SolidAcronymTheory::main),
                new Option(
                        "SOLID Acronym - Practical Example - Source code",
                        SolidAcronymPracticalExampleSourceCode::main
                ),
                new Option(
                        "SOLID Acronym - Practical Example - Execution and Result",
                        SolidAcronymPracticalExampleExecAndResult::main
                )
        });
    }
}
