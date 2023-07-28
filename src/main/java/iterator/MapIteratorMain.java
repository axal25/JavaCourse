package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapIteratorMain {

    public static void main(String[] args) {
        Map<Integer, String> map = Map.of(
                1, "1",
                2, "2",
                3, "3");

        System.out.println("\titerator\r\n");

        List<Map.Entry<Integer, String>> entrySetIteratorResult3 = new ArrayList<>();
        for (Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Integer, String> entry3 = it.next();
            System.out.println("entry3: " + entry3);
            entrySetIteratorResult3.add(entry3);
        }
        System.out.println("entrySetIteratorResult3: " + entrySetIteratorResult3);

        List<Map.Entry<Integer, String>> entrySetIteratorResult4 = new ArrayList<>();
        for (MapIterator<Integer, String> it = new MapIterator<>(map); it.hasNext(); ) {
            Map.Entry<Integer, String> entry4 = it.next();
            System.out.println("entry4: " + entry4);
            entrySetIteratorResult4.add(entry4);
        }
        System.out.println("entrySetIteratorResult4: [\r\n" + entrySetIteratorResult4.stream().map(Object::toString).collect(Collectors.joining(",\r\n")) + "\r\n]");

        System.out.println("\r\n\tfor each\r\n");

        List<Map.Entry<Integer, String>> entrySetIteratorResult1 = new ArrayList<>();
        for (Map.Entry<Integer, String> entry1 : map.entrySet()) {
            System.out.println("entry1: " + entry1);
            entrySetIteratorResult1.add(entry1);
        }
        System.out.println("entrySetIteratorResult1: " + entrySetIteratorResult1);

        List<Map.Entry<Integer, String>> entrySetIteratorResult2 = new ArrayList<>();
        for (Map.Entry<Integer, String> entry2 : new MapIterable<>(map)) {
            System.out.println("entry2: " + entry2);
            entrySetIteratorResult2.add(entry2);
        }
        System.out.println("entrySetIteratorResult2: [\r\n" + entrySetIteratorResult2.stream().map(Object::toString).collect(Collectors.joining(",\r\n")) + "\r\n]");
    }
}
