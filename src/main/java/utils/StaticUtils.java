package utils;

/**
 * Usage:
 * Create field in "Main" class
 * {@code
 * private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());
 * }
 * Use in {@code Main#main()} or {@code Main#main(String[] args)} methods a method:
 * {@code mainUtils.printMainSignature(Class<?> argClazzes);}
 * More info in ClassMethodUtils#getCurrentClassV7, ClassMethodUtils#getCurrentClassV6
 */
public final class StaticUtils {

    private final Class<?> mainOwner;

    public StaticUtils(Class<?> mainOwner) {
        this.mainOwner = mainOwner;
    }

    public void printMainSignature(Class<?>... argClazzes) {
        printMethodSignature("main", argClazzes);
    }

    public void printMethodSignature(String methodName, Class<?>... argClazzes) {
        System.out.println(StringUtils.getCenteredString(getMethodSignature(methodName, argClazzes)));
    }

    private String getMethodSignature(String methodName, Class<?>... argClazzes) {
        return ClassMethodUtils.getClassAndMethodAndArgs(mainOwner, methodName, argClazzes);
    }
}
