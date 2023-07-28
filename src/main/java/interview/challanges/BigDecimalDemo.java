package interview.challanges;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class BigDecimalDemo {
    private static void mail(String[] args) {

        //Input
        Scanner sc = new Scanner(System.in);
        int elementsAmount = sc.nextInt();
        String[] elements = new String[elementsAmount + 2];
        for (int i = 0; i < elementsAmount; i++) {
            elements[i] = sc.next();
        }
        sc.close();

        //Write your code here
        elements = getSortedElements(elementsAmount, elements);

        //Output
        for (int i = 0; i < elementsAmount; i++) {
            System.out.println(elements[i]);
        }
    }

    static String[] getSortedElements(
            int elementsAmount,
            String[] elements) {
        validate(elementsAmount);

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String realNumber1, String realNumber2) {
                return new BigDecimal(realNumber1).compareTo(new BigDecimal(realNumber2));
            }
        };

        return Arrays.stream(elements)
                .sorted(comparator.reversed())
                .toArray(String[]::new);
    }

    private static void validate(int elementsAmount) {
        if (elementsAmount < 1 || elementsAmount > 200) {
            throw new IllegalArgumentException("elementsAmount must be <1;200>");
        }
    }

    // Java's BigDecimal class can handle arbitrary-precision signed decimal numbers. Let's test your knowledge of them!
    //
    //Given an array, s, of n real number strings, sort them in descending order — but wait, there's more! Each number must be printed in the exact same format as it was read from stdin, meaning that .1 is printed as .1, and 0.1 is printed as 0.1. If two numbers represent numerically equivalent values (e.g., .1 == 0.1), then they must be listed in the same order as they were received as input).
    //
    // Complete the code in the unlocked section of the editor below. You must rearrange array s's elements according to the instructions above.
    //
    //Input Format
    //
    //The first line consists of a single integer, n, denoting the number of integer strings.
    //Each line of the subsequent lines contains a real number denoting the value of s_i.
    //
    // Constraints
    // 1 <= n <= 200
    // Each s_i has at most 300 digits.
    //
    //Output Format
    //
    //Locked stub code in the editor will print the contents of array
    //to stdout. You are only responsible for reordering the array's elements.
}
