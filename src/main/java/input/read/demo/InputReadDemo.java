package input.read.demo;

import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class InputReadDemo {
    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMainSignature();
        BufferedReaderDemo.main();
        ScannerDemo.main();
        ConsoleDemo.main();
    }
}
