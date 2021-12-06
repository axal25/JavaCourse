package threads.notify;

import threads.IHardWorker;
import threads.IMainThread;
import threads.ThreadUtils;

public class MainThreadNotify implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        Thready t1 = new Thready(new Runnably("t1 (not waited on)", this));
        Thready t2 = new Thready(new Runnably("t2 (waited on)", this));
        Thready t3 = new Thready(new Runnably("t3", this));

        t1.start();
        t2.start();
        t3.start();

        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", t1.getRunnably().getName() + " getValue(): " + t1.getValue()));
        ThreadUtils.wait(t2, t2.getRunnably().getName());
        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", t2.getRunnably().getName() + " getValue(): " + t2.getValue()));
        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", t3.getRunnably().getName() + " getValue(): " + t3.getValue()));
    }
}
