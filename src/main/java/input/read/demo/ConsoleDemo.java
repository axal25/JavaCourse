package input.read.demo;

import utils.StaticUtils;
import utils.StringUtils;

import java.io.Console;
import java.lang.invoke.MethodHandles;

public class ConsoleDemo {
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMainSignature();
        String input = null;
        Console console = System.console();

        if (console != null) {
            InputReadDemoMessageUtils.promptForInput();
            InputReadDemoMessageUtils.promptForValue(InputReadDemoMessageUtils.getClassNullSafe(input));

            input = console.readLine();
            InputReadDemoMessageUtils.printInput(input, InputReadDemoMessageUtils.getClassNullSafe(input));

            InputReadDemoMessageUtils.promptToContinue();
            System.console().readLine();
        } else {
            try {
                throw new UnsupportedOperationException(
                        String.format("%s%s%s",
                                "Could not get Console console from System.console().",
                                StringUtils.NL,
                                "Maybe Console is unsupported on this platform? It may be cause by use of IDE."
                        )
                );
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }
    }
}
