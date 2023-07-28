package interview.challanges;

import java.util.Scanner;

public class StaticInitiationFromStdIn {

    private static final int B, H;
    private static final boolean flag;

    static {
        try (Scanner scanner = new Scanner(System.in)) {
            B = scanner.nextInt();
            scanner.nextLine();
            H = scanner.nextInt();
            scanner.nextLine();
        }
        flag = B > 0 && H > 0;
        if (!flag) {
            try {
                throw new Exception("Breadth and height must be positive");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        if (flag) {
            int area = B * H;
            System.out.print(area);
        }

    }//end of main

}//end of class
