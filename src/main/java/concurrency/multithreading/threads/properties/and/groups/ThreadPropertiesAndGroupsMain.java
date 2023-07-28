package concurrency.multithreading.threads.properties.and.groups;

import java.util.Arrays;

public class ThreadPropertiesAndGroupsMain {

    public static void main(String[] args) {
        ThreadUtils.printAttributes(Thread.currentThread(), "Main thread", true);

        SleepingThread sleepingThread = new SleepingThread(1000L);
        sleepingThread.start();
        sleep(sleepingThread.getSleepDurationMillis() / 10L);
        ThreadUtils.printAttributes(sleepingThread, String.format("Sleeping (%ss) thread - during waiting", sleepingThread.getSleepDurationMillis() / 1000L));
        joinThreads(sleepingThread);

        ExampleThreadOk exampleThreadOk = new ExampleThreadOk(
                new ExampleThreadGroup("Shouldn't catch anything - because no exception should be thrown."),
                "Example thread OK.");
        exampleThreadOk.start();
        sleep(10L);
        ThreadUtils.printAttributes(exampleThreadOk, "Example thread OK - before waiting to finish");
        joinThreads(exampleThreadOk);
        ThreadUtils.printAttributes(exampleThreadOk, "Example thread OK - after finished");

        ExampleThreadThrows exampleThreadThrows = new ExampleThreadThrows(
                new ExampleThreadGroup("Should catch exception."),
                "Example thread THROWS.");
        exampleThreadThrows.start();
        ThreadUtils.printAttributes(exampleThreadThrows, "Example thread THROWS - before waiting to finish");
        joinThreads(exampleThreadThrows);
        ThreadUtils.printAttributes(exampleThreadThrows, "Example thread THROWS - after finished");

        joinThreads(sleepingThread, exampleThreadOk, exampleThreadThrows);
    }

    private static void sleep(long sleepDurationMillis) {
        try {
            Thread.sleep(sleepDurationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void joinThreads(Thread... threads) {
        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
