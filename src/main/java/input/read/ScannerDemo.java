package input.read;

import utils.StaticUtils;
import utils.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.InputMismatchException;
import java.util.Scanner;

class ScannerDemo {
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    static void main() {
        staticUtils.printMainSignature();
        getStringInput();
        getDoubleInput();
        getIntegerInputLoop();
    }

    private static String getStringInput() {
        staticUtils.printMethodSignature("promptStringInput");
        String inputString = null;
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));

        InputMessageUtils.promptForInput();
        InputMessageUtils.promptForValue(InputMessageUtils.getClassNullSafe(StringUtils.EMPTY));

        inputString = scanner.next();
        InputMessageUtils.printInput(inputString, InputMessageUtils.getClassNullSafe(inputString));

        String newLineLeftovers = scanner.nextLine();
        InputMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

        InputMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputString;
    }

    private static Double getDoubleInput() {
        staticUtils.printMethodSignature("promptDoubleInputLoop");
        Double inputDouble = null;
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));

        InputMessageUtils.promptForInput();
        InputMessageUtils.promptForValue(InputMessageUtils.getClassNullSafe(inputDouble));

        try {
            inputDouble = scanner.nextDouble();
        } catch (InputMismatchException e) {
            InputMessageUtils.promptInvalidInput(InputMessageUtils.getClassNullSafe(inputDouble));
        }
        InputMessageUtils.printInput(inputDouble, InputMessageUtils.getClassNullSafe(inputDouble));

        String newLineLeftovers = scanner.nextLine();
        InputMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

        InputMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputDouble;
    }

    private static Integer getIntegerInputLoop() {
        staticUtils.printMethodSignature("promptIntInputLoop");
        Integer inputInteger = null;
        boolean executeLoop = true;

        InputMessageUtils.promptForInput();
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));
        while (executeLoop) {
            InputMessageUtils.promptForValue(InputMessageUtils.getClassNullSafe(inputInteger));
            try {
                inputInteger = scanner.nextInt();
                executeLoop = false;

                String newLineLeftovers = scanner.nextLine();
                InputMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);
            } catch (InputMismatchException e) {
                InputMessageUtils.promptInvalidInput(InputMessageUtils.getClassNullSafe(inputInteger));
                executeLoop = true;

                String newLineLeftovers = scanner.nextLine();
                InputMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

                scanner.close();
                scanner = new Scanner(new DecoratorInputStream(System.in));
            }
        }
        InputMessageUtils.printInput(inputInteger, InputMessageUtils.getClassNullSafe(inputInteger));

        InputMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputInteger;
    }
}
