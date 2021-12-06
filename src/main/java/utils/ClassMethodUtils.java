package utils;

import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collector;

public class ClassMethodUtils {

    /**
     * Since Java 7
     * Credit: https://stackoverflow.com/questions/936684/getting-the-class-name-from-a-static-method-in-java
     * Artyom Krivolapov
     **/
    private static Class<?> getCurrentClassV7() {
        return MethodHandles.lookup().lookupClass();
    }

    /**
     * Since Java 6
     * Credit: https://stackoverflow.com/questions/936684/getting-the-class-name-from-a-static-method-in-java
     * Artyom Krivolapov
     * Drawbacks: Creates anonymous class on each use
     **/
    private static Class<?> getCurrentClassV6() {
        return new Object() {
        }.getClass().getEnclosingClass();
    }

    public static String getClassAndMethodAndArgs(Class<?> clazzMethodOwner, String methodName, Class<?>... argClazzes) {
        return new StringBuilder()
                .append(getClassSimpleName(clazzMethodOwner))
                .append("#")
                .append(getMethodAndArgs(methodName, argClazzes))
                .toString();
    }

    private static String getMethodAndArgs(String methodName, Class<?>... argClazzes) {
        return new StringBuilder()
                .append(methodName)
                .append("(")
                .append(getMethodArgs(argClazzes))
                .append(")")
                .toString();
    }

    private static String getMethodArgs(Class<?>... argClazzes) {
        return argClazzes == null || argClazzes.length == 0 || argClazzes[0] == null
                ? StringUtils.EMPTY
                : Arrays.stream(argClazzes)
                .map(clazz -> getMethodArgAsClassAndName(clazz))
                .collect(Collector.of(
                        StringBuilder::new,
                        (stringBuilder, stringElem) -> stringBuilder =
                                stringBuilder.toString().equals("")
                                        ? stringBuilder.append(stringElem)
                                        : stringBuilder.append(", ").append(stringElem),
                        UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                        StringBuilder::toString
                ));
    }

    private static String getMethodArgAsClassAndName(Class<?> argClazz) {
        return new StringBuilder()
                .append(getClassSimpleName(argClazz))
                .append(" ")
                .append(getClassNameToArgName(argClazz))
                .toString();
    }

    private static String getClassNameToArgName(Class<?> argClazz) {
        Pattern letterPattern = Pattern.compile("[a-zA-Z0-9]");
        return StreamUtils.stream(getLowerCaseFirstLetter(getClassSimpleName(argClazz)).toCharArray())
                .map(String::valueOf)
                .filter(character -> letterPattern.matcher(character).matches())
                .collect(
                        Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                StringBuilder::toString
                        )
                );
    }

    /**
     * https://stackoverflow.com/questions/45221686/how-to-get-the-original-class-from-anonymous-class-java
     */
    public static String getClassSimpleName(Class<?> argClazz) {
        return !StringUtils.isBlank(argClazz.getSimpleName())
                ? argClazz.getSimpleName()
                : isInterface(argClazz)
                ? getClassSimpleName(argClazz.getAnnotatedInterfaces()[0].getType().getTypeName())
                : getClassSimpleName(argClazz.getAnnotatedSuperclass().getType().getTypeName());
    }

    private static boolean isInterface(Class<?> argClazz) {
        return 0 < argClazz.getAnnotatedInterfaces().length;
    }

    private static String getClassSimpleName(String fullClassName) {
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }

    private static String getLowerCaseFirstLetter(String classSimpleName) {
        return new StringBuilder()
                .append(classSimpleName.substring(0, 1).toLowerCase())
                .append(classSimpleName.substring(1))
                .toString();
    }
}
