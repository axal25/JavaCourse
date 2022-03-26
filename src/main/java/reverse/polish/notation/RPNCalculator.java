package reverse.polish.notation;

import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class RPNCalculator {

    static final Pattern whitespaces = Pattern.compile("\\p{Space}+", Pattern.UNICODE_CHARACTER_CLASS);

    private final Stack<Integer> rpnStack = new Stack<>();

    RPNCalculator(String input) {
        Scanner wholeInputScanner = new Scanner(input);
        wholeInputScanner.useDelimiter(whitespaces);
        while (wholeInputScanner.hasNext()) {
            RPNSingleInputScanner rpnSingleInputScanner = new RPNSingleInputScanner(wholeInputScanner.next().trim());
            if (rpnSingleInputScanner.getRpnSingleInput().getType() == RPNSingleInputType.OPERATOR) {
                executeOperation(rpnSingleInputScanner.getRpnSingleInput().getOperator());
            }
            if (rpnSingleInputScanner.getRpnSingleInput().getType() == RPNSingleInputType.INTEGER) {
                rpnStack.push(rpnSingleInputScanner.getRpnSingleInput().getInteger());
            }
        }
    }

    private void executeOperation(String operator) {
        if (rpnStack.size() < 2) {
            throw new UnsupportedOperationException(String.format(
                    "To execute operation 2 integers must be on stack. Stack: %s. Operator: \"%s\".",
                    rpnStack,
                    operator
            ));
        }
        Integer right = rpnStack.pop();
        Integer left = rpnStack.pop();
        switch (operator) {
            case "+":
                rpnStack.push(left + right);
                break;
            case "-":
                rpnStack.push(left - right);
                break;
            case "*":
                rpnStack.push(left * right);
                break;
            case "/":
                rpnStack.push(left / right);
                break;
            default:
                throw new UnsupportedOperationException(String.format(
                        "Only supported operations are: +, -, *, / but was given: \"%s\".",
                        operator
                ));
        }
    }

    public Integer getResult() {
        if (rpnStack.size() != 1) {
            throw new UnsupportedOperationException(
                    String.format(
                            "RPN Stack size must be equal to 1 to get the result but was %d. Stack: %s",
                            rpnStack.size(),
                            rpnStack
                    )
            );
        }
        return rpnStack.peek();
    }
}
