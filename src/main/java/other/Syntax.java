package other;

import java.util.HashMap;
import java.util.stream.IntStream;

public class Syntax {
    private static int i = 5;

    public static void main() {
        increment();
        var somemap = new HashMap<String, String>();
        IntStream.range(1, 5).forEach(System.out::println);
        Integer[] integers = new Integer[10];
        Number[] numbers = integers;
//        numbers[0] = 1L;
        System.out.println(numbers[0]);
//        int reduce3d = java.util.stream.IntStream.of(1).reduce()
//        other.P.C c = new other.P.C();
        TestThread.main(new String[]{"a"});
        System.out.println("Integer.valueOf(23) == Integer.valueOf(23): " + (Integer.valueOf(23) == Integer.valueOf(23)));
        System.out.println("new Integer(23) == new Integer(23): " + (new Integer(23) == new Integer(23)));
        try {

        } finally {

        }
    }

    private static void increment() {
        int i = 7;
        System.out.println("i: " + i);
        System.out.println("i++: " + i++);
        System.out.println("i: " + i);
        System.out.println("++i: " + ++i);
        System.out.println("++i + i++: " + (++i + i++));
    }
}
