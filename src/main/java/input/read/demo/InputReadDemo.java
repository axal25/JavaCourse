package input.read.demo;

import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class InputReadDemo {
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMainSignature();
        BufferedReaderDemo.main();
        ScannerDemo.main();
        ConsoleDemo.main();
    }
}
