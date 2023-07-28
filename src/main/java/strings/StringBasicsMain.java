package strings;

import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StringBasicsMain {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String input1=sc.next();
        String input2=sc.next();
        /* Enter your code here. Print output to STDOUT. */
        process(input1, input2);
    }

    static void process(String input1, String input2) {
        System.out.println(lengthSumNullSafe(input1, input2));

        System.out.println(nullSafeCompare(input1, input2) > 0 ? "Yes" : "No");

        System.out.println(getWithFirstLetterCapitalized(input1) + " " + getWithFirstLetterCapitalized(input2));
    }

    static int lengthSumNullSafe(String input1, String input2) {
        return lengthNullSafe(input1) + lengthNullSafe(input2);
    }

    static int lengthNullSafe(String input) {
        return input == null ? 0 : input.length();
    }

    static int nullSafeCompare(String input1, String input2) {
        return Comparator.<String>nullsFirst(Comparator.naturalOrder()).compare(input1, input2);
    }

    static String getWithFirstLetterCapitalized(String input) {
        if(input == null) {
            return null;
        }
        if (input.isEmpty() || input.isBlank()) {
            return input;
        }
        Integer firstLetterIndex = findFirstLetter(input);
        if(firstLetterIndex == null) {
            return input;
        }
        return input.substring(0, firstLetterIndex) +
                input
                .substring(firstLetterIndex, firstLetterIndex + 1)
                .toUpperCase() +
                input.substring(firstLetterIndex + 1);
    }

    static Integer findFirstLetter(String input) {
        Pattern letterPattern = Pattern.compile("[a-zA-Z]");
        String firstLetter = input.chars().mapToObj(charCode -> (char) charCode)
                .map(String::valueOf)
                .filter(letterString -> letterPattern.matcher(letterString).matches())
                .findFirst()
                .orElse(null);
        return firstLetter == null ? null : input.indexOf(firstLetter);
    }
}
