package input.read;

import utils.ClassMethodUtils;

class InputMessageUtils {

    static void promptForInput() {
        System.out.println("Please enter you your input");
    }

    static Class<?> getClassNullSafe(String chosenInput) {
        return String.class;
    }

    static Class<?> getClassNullSafe(Integer chosenInput) {
        return Integer.class;
    }

    static Class<?> getClassNullSafe(Double chosenInput) {
        return Double.class;
    }

    static void promptForValue(Class<?> chosenInputClazz) {
        System.out.print(String.format("%s: ", ClassMethodUtils.getClassSimpleName(chosenInputClazz)));
    }

    static void printInput(Object chosenInput, Class<?> chosenInputClazz) {
        System.out.printf("Chosen (%s) input: %s\n", ClassMethodUtils.getClassSimpleName(chosenInputClazz), chosenInput);
    }

    static void printChosenOptionNewLineLeftovers(String newLineLeftovers) {
        System.out.println(String.format("Chosen input's new line leftovers: \"%s\"", newLineLeftovers));
    }

    static void promptInvalidInput(Class<?> chosenInputClazz) {
        System.out.println(String.format("Invalid input format. Please enter %s.", ClassMethodUtils.getClassSimpleName(chosenInputClazz)));
    }

    static void promptToContinue() {
        System.out.print("Press [Enter] to continue...");
    }
}
