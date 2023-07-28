package interview.challanges;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class BigIntegerDemo {

    public static void main(String[] args) {
        scanAndPrintAdditionMultiplication(System.in);
    }

    static void scanAndPrintAdditionMultiplication(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        BigInteger a = new BigInteger(scanner.next());
        scanner.nextLine();
        BigInteger b = new BigInteger(scanner.next());
        try {
            scanner.nextLine();
        } catch (Exception e) {

        } finally {
            scanner.close();
        }
        System.out.println(a.add(b));
        System.out.println(a.multiply(b));
    }
}
