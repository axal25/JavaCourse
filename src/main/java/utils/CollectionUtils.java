package utils;

import java.util.Map;
import java.util.stream.IntStream;

public class CollectionUtils {

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
