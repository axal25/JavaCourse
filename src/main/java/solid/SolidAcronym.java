package solid;

import input.read.menu.Menu;
import input.read.menu.Option;
import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronym {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");

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
