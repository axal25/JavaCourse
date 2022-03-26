package reverse.polish.notation;

import utils.StaticOrMainMethodUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

public class ReversePolishNotationAkaPostfixNotationDemo {
    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    private static List<RPNInputExpectedActualResultsTuple> rpnInputExpectedActualResultsTuples = null;

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticOrMainMethodUtils.printMainSignature();
        System.out.println(getOutputString(getRpnInputExpectedActualResultsTuples()));
    }

    static List<RPNInputExpectedActualResultsTuple> getRpnInputExpectedActualResultsTuples() {
        if (rpnInputExpectedActualResultsTuples == null) {
            populateRpnInputExpectedActualResultsTuples();
        }
        return rpnInputExpectedActualResultsTuples;
    }

    private static void populateRpnInputExpectedActualResultsTuples() {
        List<RPNInputExpectedActualResultsTuple> tmpRpnInputExpectedActualResultsTuples = new LinkedList<>();
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("2", 2, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 2 +", 5, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 4 +", 7, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("12 4 /", 3, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 1 -", 2, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("12 4 / 1 -", 2, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("4 1 -", 3, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("12 3 /", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("12 4 1 - /", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 4 -", -1, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("-1 5 +", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 4 - 5 +", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("4 5 +", 9, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 9 -", -6, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 4 5 + -", -6, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("1 1 +", 2, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("7 2 -", 5, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("7 1 1 + -", 5, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("15 5 /", 3, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("15 7 1 1 + - /", 3, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("3 3 *", 9, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("15 7 1 1 + - / 3 *", 9, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("1 1 +", 2, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("2 2 +", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("2 1 1 + +", 4, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple());
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("9 4 -", 5, null));
        tmpRpnInputExpectedActualResultsTuples.add(new RPNInputExpectedActualResultsTuple("15 7 1 1 + - / 3 * 2 1 1 + + -", 5, null));
        rpnInputExpectedActualResultsTuples = Collections.unmodifiableList(tmpRpnInputExpectedActualResultsTuples);
        fillActualResults(rpnInputExpectedActualResultsTuples);
    }

    private static void fillActualResults(List<RPNInputExpectedActualResultsTuple> rpnInputExpectedActualResultsTuples) {
        rpnInputExpectedActualResultsTuples.forEach(elem -> {
            if (!elem.isEmpty()) {
                elem.setActual(new RPNCalculator(elem.getInput()).getResult());
            }
        });
    }

    private static String getOutputString(List<RPNInputExpectedActualResultsTuple> rpnInputExpectedActualResultsTuples) {
        return rpnInputExpectedActualResultsTuples.stream().sequential().collect(
                Collector.of(
                        StringBuilder::new,
                        (sb, elem) -> sb.append(elem.getOutputString()),
                        UnsupportedCollectorParallelLeftFoldOperation
                                .getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                        StringBuilder::toString
                )
        );
    }
}
