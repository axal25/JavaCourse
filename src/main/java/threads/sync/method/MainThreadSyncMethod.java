package threads.sync.method;

import threads.IHardWorker;
import threads.IMainThread;

public class MainThreadSyncMethod implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        System.out.println("\n" + getMessage(this.getClass().getSimpleName(), getName(), "runThreads", "synchronized"));
        SharedResource sharedResource = new SharedResource("sharedResource #1");
        new Thread(new Runnably("synchronized #1", sharedResource)).start();
        new Thread(new Runnably("synchronized #2", sharedResource)).start();
        new Thread(new Runnably("synchronized #3", new SharedResource("sharedResource #2"))).start();
        hardWork();
    }
}
