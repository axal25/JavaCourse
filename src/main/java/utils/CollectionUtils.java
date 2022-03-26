package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionUtils {

    public static <T extends Object> List<T> toList(T[] array) {
        return Arrays.stream(array).collect(Collectors.toList());
    }

    public static List<Integer> toList(int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    public static void printAsSemiJson(Object[] table, String tableName) {
        System.out.println(tableName + ": [");
        IntStream.range(0, table.length).forEach(i -> System.out.println("\t (" + i + ".) \"" + table[i].toString() + "\""));
        System.out.println("];");
    }

    static void printAsSemiJson(Map<?, ?> map, String mapName) {
        System.out.println(mapName + ": [");
        map.entrySet().forEach(entry -> System.out.println("\t (" + entry + ".) { " + entry.getKey().toString() + ": \"" + entry.getValue().toString() + "\""));
        System.out.println("];");
    }
}
