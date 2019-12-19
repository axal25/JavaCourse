import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("main");
        int i = 0;
        for(String arg : args) {
            i++;
            System.out.println(new StringBuilder().append(i).append(". arg").toString());
        }
        Solution.main(args);
    }
}
