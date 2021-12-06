package threads.basic;

import threads.IHardWorker;
import threads.IMainThread;

public class MainThreadBasic implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        Thready thready = new Thready();

        Runnably runnably = new Runnably();
        Thread threadRunnably = new Thread(runnably);

        Runnable runnable = new Runnably();
        Thread threadRunnable = new Thread(runnable);

        thready.start();
        hardWork();

        System.out.println("\tRunnably run using \"run\" method will NOT allow " + this.getClass().getSimpleName() + " to progress");
        runnably.run();
        System.out.println("\tOnly after Runnably's \"run\" method finished " + this.getClass().getSimpleName() + " can progress");
        hardWork();

        System.out.println("\tRunnably using wrapping \"Thread\" and its \"start\" method WILL allow " + this.getClass().getSimpleName() + " to progress");
        threadRunnable.start();
        threadRunnably.start();
        hardWork();
    }
}
