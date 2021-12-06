package input.read;

import utils.StaticUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

public class BufferedReaderDemo {
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMainSignature();
        String inputString = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DecoratorInputStream(System.in)));


        InputMessageUtils.promptForInput();
        InputMessageUtils.promptForValue(InputMessageUtils.getClassNullSafe(inputString));

        try {
            inputString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputMessageUtils.printInput(inputString, InputMessageUtils.getClassNullSafe(inputString));

        InputMessageUtils.promptToContinue();
        try {
            inputString = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
