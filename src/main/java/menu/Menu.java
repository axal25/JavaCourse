package menu;

import input.read.DecoratorInputStream;
import utils.ClassMethodUtils;
import utils.StaticUtils;
import utils.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Menu {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());
    private static final String PROMPT_FOR_STRING = "Press [Enter] to continue...";

    private Option[] options;

    private Menu(Option[] options) {
        this.options = Stream.concat(Stream.of(new ExitOption()), Arrays.stream(options)).toArray(Option[]::new);
    }

    private Integer getOptionFromInputLoop() {
        staticUtils.printMethodSignature("getOptionFromInputLoop");
        Integer inputInteger = null;
        boolean executeLoop = true;

        System.out.println(String.format("Please enter option of your choice [0-%d]:", options.length - 1));
        IntStream.range(0, options.length).forEach(i -> System.out.println(String.format("%d. %s", i, options[i].getName())));

        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));
        while (executeLoop) {
            System.out.print(String.format("Option [1-%d]: ", options.length - 1));
            try {
                inputInteger = scanner.nextInt();
                scanner.nextLine();
                executeLoop = !isInputIntegerValid(inputInteger);
                if (executeLoop) {
                    System.out.println(String.format("Input format. Please enter %s in range [0-%d].", ClassMethodUtils.getClassSimpleName(Integer.class), options.length - 1));
                }
            } catch (InputMismatchException e) {
                System.out.println(String.format("Invalid input format. Please enter %s.", ClassMethodUtils.getClassSimpleName(Integer.class)));
                executeLoop = true;
                scanner.nextLine();
                scanner.close();
                scanner = new Scanner(new DecoratorInputStream(System.in));
            }
        }
        scanner.close();

        promptForEnter(String.format("Chosen input: %d. %s.", inputInteger, options[inputInteger].getName()));

        return inputInteger;
    }

    private static void promptForEnter(String message) {
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));
        System.out.print(
                StringUtils.isBlank(message) ? Menu.PROMPT_FOR_STRING : String.format("%s %s", message, Menu.PROMPT_FOR_STRING)
        );
        scanner.nextLine();
        scanner.close();
    }

    private boolean isInputIntegerValid(Integer inputInteger) {
        return inputInteger != null && inputInteger >= 0 && inputInteger < options.length;
    }

    public void open() {
        options[getOptionFromInputLoop()].getFiOption().execWrapper(this);
    }

    public static void open(Option[] options) {
        Menu menu = new Menu(options);
        menu.open();
    }
}
