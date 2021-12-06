package utils;

public class ArgUtils {

    public static void printArgs(String[] args) {
        CollectionUtils.printAsSemiJson(args, "args");
    }

}
