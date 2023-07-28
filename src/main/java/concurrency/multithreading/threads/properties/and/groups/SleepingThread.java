package concurrency.multithreading.threads.properties.and.groups;

public class SleepingThread extends Thread {

    private final long sleepDurationMillis;

    SleepingThread(long sleepDurationMillis) {
        this.sleepDurationMillis = sleepDurationMillis;
    }

    @Override
    public void run() {
        System.out.printf("%s running%n", getClass().getSimpleName());
        System.out.printf("%s sleeping...%n", getClass().getSimpleName());
        try {
            sleep(sleepDurationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s sleeping finished%n", getClass().getSimpleName());
    }

    long getSleepDurationMillis() {
        return sleepDurationMillis;
    }
}
