package reverse.polish.notation;

import utils.StringUtils;

public class RPNInputExpectedActualResultsTuple {

    private String input;
    private Integer expected;
    private Integer actual;

    RPNInputExpectedActualResultsTuple() {
        this(null, null, null);
    }

    RPNInputExpectedActualResultsTuple(String input, Integer expected, Integer actual) {
        this.input = input;
        this.expected = expected;
        this.actual = actual;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getExpected() {
        return expected;
    }

    public void setExpected(Integer expected) {
        this.expected = expected;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(getInput()) && getExpected() == null && getActual() == null;
    }

    String getOutputString() {
        return isEmpty()
                ? StringUtils.NL
                : String.format(
                "Input: \"%35s\", Expected result: \"%4d\", Actual result: \"%4d\".\n",
                getInput(),
                getExpected(),
                getActual()
        );
    }
}
