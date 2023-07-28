package interview.challanges;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SpinWords {
    public static void main(String[] args) {
        printSpinWords("Hey fellow warriors");
        printSpinWords("This is a test");
        printSpinWords("This is another test");
    }

    private static void printSpinWords(String input) {
        System.out.println("input: \"" + input + "\"" + "\nresult: \"" + spinWords(input) + "\"");
    }

    private static String spinWords(String input) {
        Scanner scanner = new Scanner(input);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            String word = scanner.next();
            sb.append(
                    word.length() < 5
                            ? word
                            : IntStream.range(0, word.length())
                            .map(index -> word.length() - 1 - index)
                            .mapToObj(inverseIndex -> String.valueOf(word.charAt(inverseIndex)))
                            .collect(Collectors.joining(""))
            );
            if (scanner.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
