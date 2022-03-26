package input.read.demo;

import input.read.common.DecoratorInputStream;
import utils.StaticOrMainMethodUtils;
import utils.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.InputMismatchException;
import java.util.Scanner;

class ScannerDemo {
    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    static void main() {
        staticOrMainMethodUtils.printMainSignature();
        getStringInput();
        getDoubleInput();
        getIntegerInputLoop();
    }

    private static String getStringInput() {
        staticOrMainMethodUtils.printMethodSignature("promptStringInput");
        String inputString = null;
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));

        InputReadDemoMessageUtils.promptForInput();
        InputReadDemoMessageUtils.promptForValue(InputReadDemoMessageUtils.getClassNullSafe(StringUtils.EMPTY));

        inputString = scanner.next();
        InputReadDemoMessageUtils.printInput(inputString, InputReadDemoMessageUtils.getClassNullSafe(inputString));

        String newLineLeftovers = scanner.nextLine();
        InputReadDemoMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

        InputReadDemoMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputString;
    }

    private static Double getDoubleInput() {
        staticOrMainMethodUtils.printMethodSignature("promptDoubleInputLoop");
        Double inputDouble = null;
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));

        InputReadDemoMessageUtils.promptForInput();
        InputReadDemoMessageUtils.promptForValue(InputReadDemoMessageUtils.getClassNullSafe(inputDouble));

        try {
            inputDouble = scanner.nextDouble();
        } catch (InputMismatchException e) {
            InputReadDemoMessageUtils.promptInvalidInput(InputReadDemoMessageUtils.getClassNullSafe(inputDouble));
        }
        InputReadDemoMessageUtils.printInput(inputDouble, InputReadDemoMessageUtils.getClassNullSafe(inputDouble));

        String newLineLeftovers = scanner.nextLine();
        InputReadDemoMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

        InputReadDemoMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputDouble;
    }

    private static Integer getIntegerInputLoop() {
        staticOrMainMethodUtils.printMethodSignature("promptIntInputLoop");
        Integer inputInteger = null;
        boolean executeLoop = true;

        InputReadDemoMessageUtils.promptForInput();
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));
        while (executeLoop) {
            InputReadDemoMessageUtils.promptForValue(InputReadDemoMessageUtils.getClassNullSafe(inputInteger));
            try {
                inputInteger = scanner.nextInt();
                executeLoop = false;

                String newLineLeftovers = scanner.nextLine();
                InputReadDemoMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);
            } catch (InputMismatchException e) {
                InputReadDemoMessageUtils.promptInvalidInput(InputReadDemoMessageUtils.getClassNullSafe(inputInteger));
                executeLoop = true;

                String newLineLeftovers = scanner.nextLine();
                InputReadDemoMessageUtils.printChosenOptionNewLineLeftovers(newLineLeftovers);

                scanner.close();
                scanner = new Scanner(new DecoratorInputStream(System.in));
            }
        }
        InputReadDemoMessageUtils.printInput(inputInteger, InputReadDemoMessageUtils.getClassNullSafe(inputInteger));

        InputReadDemoMessageUtils.promptToContinue();
        scanner.nextLine();

        scanner.close();
        return inputInteger;
    }
}
