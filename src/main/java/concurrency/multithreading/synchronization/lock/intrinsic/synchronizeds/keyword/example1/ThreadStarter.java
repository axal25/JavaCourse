package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

import java.util.List;

class ThreadStarter extends Thread {

    ThreadStarter(String toiasOrTcsas, List<Thread> threads) {
        super(
                getRunnable(threads),
                String.format("%s thread starter", toiasOrTcsas));
    }

    private static Runnable getRunnable(List<Thread> threads) {
        return () -> threads.forEach(thread -> {
            sleep();
            thread.start();
        });
    }

    private static void sleep() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
