package utils;

public class EnvUtils {

    public static void printEnvVars() {
        CollectionUtils.printAsSemiJson(System.getenv(), "args");
    }
}
