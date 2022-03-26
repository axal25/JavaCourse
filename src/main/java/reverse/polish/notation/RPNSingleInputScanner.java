package reverse.polish.notation;

import java.util.regex.Pattern;

public class RPNSingleInputScanner {

    private static final Pattern operatorPattern = Pattern.compile("[+-/*]");
    private static final Pattern integerPattern = Pattern.compile("[+-]?\\d+");
    private static final int horizon = 0;

    private String singleInput;
    private RPNSingleInput rpnSingleInput;

    RPNSingleInputScanner(String singleInput) {
        if (RPNCalculator.whitespaces.matcher(singleInput).find()) {
            throw new IllegalArgumentException("Single input type should not contain white spaces");
        }
        this.singleInput = singleInput;
        this.rpnSingleInput = getRPNSingleInput(singleInput);
    }

    
    private static RPNSingleInput getRPNSingleInput(String singleInput) {
        if (operatorPattern.matcher(singleInput).matches()) {
            return new RPNSingleInput(singleInput, RPNSingleInputType.OPERATOR);
        }
        if (integerPattern.matcher(singleInput).matches()) {
            return new RPNSingleInput(singleInput, RPNSingleInputType.INTEGER);
        }
        throw new UnsupportedOperationException(
                String.format(
                        "Could not find neither %s nor %s for single input \"%s\".",
                        RPNSingleInputType.OPERATOR,
                        RPNSingleInputType.INTEGER,
                        singleInput
                )
        );
    }

    public String getSingleInput() {
        return singleInput;
    }

    RPNSingleInput getRpnSingleInput() {
        return rpnSingleInput;
    }
}
