package interview.challanges;

public class EvenOrOdd {
    public static void main(String[] args) {
        int input = 214123129;
        System.out.println("input: " + input + ", result: " + getEvenOrOdd(input));
    }

    private static String getEvenOrOdd(int input) {
        return input % 2 == 0 ? "Even" : "Odd";
    }
}
