package input.read.common;

import messages.Messages;
import utils.StringUtils;

import java.util.Scanner;

public class InputCommons {
    public static void promptForEnter(String message) {
        Scanner scanner = new Scanner(new DecoratorInputStream(System.in));
        System.out.print(
                StringUtils.isBlank(message) ? Messages.PROMPT_PRESS_ENTER : String.format("%s %s", message, Messages.PROMPT_PRESS_ENTER)
        );
        scanner.nextLine();
        scanner.close();
    }
}
