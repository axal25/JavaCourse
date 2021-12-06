package input.read;

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
            InputMessageUtils.promptForInput();
            InputMessageUtils.promptForValue(InputMessageUtils.getClassNullSafe(input));

            input = console.readLine();
            InputMessageUtils.printInput(input, InputMessageUtils.getClassNullSafe(input));

            InputMessageUtils.promptToContinue();
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
