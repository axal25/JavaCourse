package stream;

import menu.Menu;
import menu.Option;
import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class Streams {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");

        Menu.open(new Option[]{
                new Option("Stream Collector's Collect method - Sequential vs. Parallel", StreamCollectorSequentialVsParallel::main),
                new Option("Stream examples", StreamExamples::main)
        });
    }
}
