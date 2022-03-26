package reverse.polish.notation;

import utils.ClassMethodUtils;

public class RPNSingleInput {
    private RPNSingleInputType type;
    private String operator;
    private Integer integer;

    RPNSingleInput(String singleInput, RPNSingleInputType rpnSingleInputType) {
        if (rpnSingleInputType == null) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s cannot be null",
                            ClassMethodUtils.getClassSimpleName(RPNSingleInputType.class)
                    )
            );
        }
        this.type = rpnSingleInputType;
        switch (rpnSingleInputType) {
            case OPERATOR:
                if (singleInput == null) {
                    throw new NullPointerException("Operator cannot be null but was null.");
                }
                if (singleInput.length() != 1) {
                    throw new IllegalArgumentException(String.format(
                            "Operator length should be 1 but was: %d. Operator: \"%s\".",
                            operator.length(),
                            operator
                    ));
                }
                operator = singleInput;
                break;
            case INTEGER:
                if (singleInput == null) {
                    throw new NullPointerException("Integer cannot be null but was null.");
                }
                integer = Integer.valueOf(singleInput);
                break;
            default:
                throw new UnsupportedOperationException(String.format(
                        "%s unsupported value: \"%s\" (%d).",
                        ClassMethodUtils.getClassSimpleName(RPNSingleInputType.class),
                        rpnSingleInputType.name(),
                        rpnSingleInputType.ordinal()
                ));
        }
    }

    public RPNSingleInputType getType() {
        return type;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getInteger() {
        return integer;
    }
}
