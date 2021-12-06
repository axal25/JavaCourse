package other;

public class TestThread extends Thread {
    public static void main(String args[]) {
        TestThread testThread = new TestThread();
        testThread.start();
    }

    @Override
    public void run() {
        System.out.println("run - test");
    }
}