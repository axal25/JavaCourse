package interview.challanges;

import utils.VisibleForTesting;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Palindrome {

    public static void main(String[] args) {
        String input = null;
        try (Scanner scanner = new Scanner(System.in)) {
            input = scanner.next();
        }
        System.out.println(isPalindrome(input) ? "Yes" : "No");
    }

    @VisibleForTesting
    static boolean isPalindrome(String input) {
        if (input == null || input.trim().isBlank()) {
            return true;
        }
        List<Integer> charInts = input.chars().boxed().collect(Collectors.toList());
        double mid = Math.ceil(charInts.size() / 2.0);
        return IntStream.range(0, (int) mid)
                .allMatch(i -> charInts.get(i)
                        .equals(charInts.get(charInts.size() - 1 - i)));
    }
}
