package interview.challanges;

import java.security.Permission;
import java.util.Scanner;

public class IntToStringAndBlockExit0 {
    public static void main(String[] args) {

        DoNotTerminate.forbidExit();

        // exit(0); // test DoNotTerminate.forbidExit();

        try {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            in.close();
            //String s=???; Complete this line below
            String s = String.valueOf(n);
            //Write your code here


            if (n == Integer.parseInt(s)) {
                System.out.println("Good job");
            } else {
                System.out.println("Wrong answer.");
            }
        } catch (DoNotTerminate.ExitTrappedException e) {
            System.out.println("Unsuccessful Termination!!");
        }
    }

    //The following class will prevent you from terminating the code using exit(0)!
    private static class DoNotTerminate {

        static class ExitTrappedException extends SecurityException {

            private static final long serialVersionUID = 1;
        }

        static void forbidExit() {
            final SecurityManager securityManager = new SecurityManager() {
                @Override
                public void checkPermission(Permission permission) {
                    if (permission.getName().contains("exitVM")) {
                        throw new ExitTrappedException();
                    }
                }
            };

            System.setSecurityManager(securityManager);
        }
    }
}

