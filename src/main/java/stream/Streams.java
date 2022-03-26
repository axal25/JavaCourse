package stream;

import input.read.menu.Menu;
import input.read.menu.Option;
import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class Streams {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        Menu.open(new Option[]{
                new Option("Stream Collector's Collect method - Sequential vs. Parallel", StreamCollectorSequentialVsParallel::main),
                new Option("Stream examples", StreamExamples::main)
        });
    }
}
