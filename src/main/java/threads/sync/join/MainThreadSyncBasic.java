package threads.sync.join;

import threads.IHardWorker;
import threads.IMainThread;
import threads.ThreadUtils;

public class MainThreadSyncBasic implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", "unsynchronized"));
        Thread unsynchronized1 = new Thread(new Runnably("unsynchronised #1"));
        Thread unsynchronized2 = new Thread(new Runnably("unsynchronised #2"));

        unsynchronized1.setPriority(10);
        unsynchronized2.setPriority(1);

        unsynchronized1.start();
        unsynchronized2.start();
        hardWork();

        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", "synchronized"));

        Thread synchronized1 = new Thread(new Runnably("synchronised #1"));
        Thread synchronized2 = new Thread(new Runnably("synchronised #2"));

        synchronized1.start();
        ThreadUtils.join(synchronized1, "synchronized #1");

        synchronized2.start();
        ThreadUtils.join(synchronized2, "synchronized #2");

        hardWork();
    }
}
