package interview.challanges;

import utils.VisibleForTesting;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubstringComparison {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        int k = scan.nextInt();
        scan.close();

        System.out.println(getSmallestAndLargest(s, k));
    }

    public static String getSmallestAndLargest(String s, int k) {
        // We define the following terms:
        // Lexicographical Order, also known as alphabetic or dictionary order, orders characters as follows:
        // A < B < ... < Y < Z < a < b < ... < y < z
        // A substring of a string is a contiguous block of characters in the string.
        // For example, the substrings of abc are a, b, c, ab, bc, and abc.
        // Given a string, s, and an integer, k, complete the function so that it finds the lexicographically
        // smallest and largest substrings of length k.
        // Function Description
        // Complete the getSmallestAndLargest function in the editor below.
        // getSmallestAndLargest has the following parameters:
        // string s: a string
        // int k: the length of the substrings to find
        // Returns
        // string: the string ' + "\n" + ' where and are the two substrings
        // Input Format
        // The first line contains a string denoting s.
        // The second line contains an integer denoting k.
        // Constraints
        // 1 <= |s| <= 1000
        // s consists of English alphabetic letters only (i.e., [a-zA-Z]).
        // Sample Input 0: {
        // welcometojava
        // 3
        // }
        // Sample Output 0: {
        // ava
        // wel
        // }
        // Explanation 0: {
        // String s = "welcometojava" has the following lexicographically-ordered substrings of length k = 3:
        // ["ava", "com", "elc", "eto", "jav", "lco", "met", "oja", "ime", "toj", "wel"]
        // We then return the first (lexicographically smallest) substring and the last (lexicographically largest)
        // substring as two newline-separated values (i.e., ava\nwel).
        // The stub code in the editor then prints ava as our first line of output and wel as our second line of output.
        Comparator<List<Integer>> charIntListComparator = (l1, l2) -> {
            int minSize = Math.min(l1.size(), l2.size());
            for (int i = 0; i < minSize; i++) {
                int result = Integer.compare(l1.get(i), l2.get(i));
                if (result != 0) {
                    return result;
                }
            }
            return Integer.compare(l1.size(), l2.size());
        };
        Comparator<String> stringComparator = Comparator.comparing(
                in -> in.chars().boxed().collect(Collectors.toList()),
                charIntListComparator);
        List<String> subStrings = getSubstrings(s, k).stream()
                .sorted(stringComparator)
                .collect(Collectors.toList());

        String smallest = subStrings.get(0);
        String largest = subStrings.get(subStrings.size() == 1 ? 0 : subStrings.size() - 1);

        // Complete the function
        // 'smallest' must be the lexicographically smallest substring of length 'k'
        // 'largest' must be the lexicographically largest substring of length 'k'

        return smallest + "\n" + largest;
    }

    @VisibleForTesting
    static List<String> getSubstrings(String wholeString, int substringLength) {
        if (wholeString == null) {
            throw new NullPointerException("Input, whole string cannot be null.");
        }
        if (substringLength > wholeString.length()) {
            throw new IllegalArgumentException(
                    "Substring's desired length cannot be greater than input, whole string's length." +
                            " Whole string: " + wholeString +
                            " Whole string length: " + wholeString.length() +
                            " Substring length: " + substringLength);
        }
        if (substringLength == 0 | wholeString.isEmpty()) {
            return List.of("");
        }
        return wholeString.length() == substringLength
                ? List.of(wholeString)
                : IntStream.range(0, wholeString.length() - (substringLength - 1))
                .mapToObj(startIndex -> wholeString.substring(startIndex, startIndex + substringLength))
                .collect(Collectors.toList());
    }
}
