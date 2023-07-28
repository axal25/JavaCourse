package interview.challanges;

import java.util.List;
import java.util.stream.Collectors;

public class FilterStringOutOfIntStringList {

    public static void main(String[] args) {
        List<Object> input = List.of(1, 2, "a", "b", "aasf", "1", "123", 231);
        System.out.println("input: " + input);
        System.out.println("result: " + filterList(input));
        System.out.println("expected: " + List.of(1, 2, 231));
    }

    private static List<Integer> filterList(List<Object> list) {
        return list.stream()
                .filter(Integer.class::isInstance)
                .map(object -> (Integer) object)
                .collect(Collectors.toList());
    }
}
