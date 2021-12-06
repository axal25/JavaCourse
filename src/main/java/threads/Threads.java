package threads;

import threads.executors.future.MainThreadFutureSubmitExecutor;

public class Threads {

    private static final int interval = 2500;

    public static void main(String[] args) {
        System.out.println(Threads.class.getSimpleName() + "#main - start");

//        new MainThreadBasic().runThreadsDemo();
//        sleep();
//        new MainThreadSyncBasic().runThreadsDemo();
//        sleep();
//        new MainThreadSyncMethod().runThreadsDemo();
//        sleep();
//        new MainThreadSyncStaticMethod().runThreadsDemo();
//        sleep();
//        new MainThreadSyncBlock().runThreadsDemo();
//        sleep();
//        new MainThreadSingleThreadExecutor().runThreadsDemo();
//        sleep();
//        new MainThreadFixedThreadPoolExecutor().runThreadsDemo();
//        sleep();
//        new MainThreadScheduledThreadPoolExecutor().runThreadsDemo();
//        sleep();
        new MainThreadFutureSubmitExecutor().runThreadsDemo();
//        sleep();
//        new MainThreadNotify().runThreadsDemo();

        System.out.println(Threads.class.getSimpleName() + "#main - finish");
    }

    private static void sleep() {
        ThreadUtils.sleep(interval);
    }
}
