package threads;

public class ThreadUtils {

    public static void wait(Thread thread, String threadName) {
        System.out.println("Wait on thread: " + threadName);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public static void join(Thread thread, String threadName) {
        System.out.println("Join on thread: " + threadName);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    static void sleep(int interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
