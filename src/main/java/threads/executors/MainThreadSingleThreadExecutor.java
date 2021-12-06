package threads.executors;

import threads.IHardWorker;
import threads.IMainThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreadSingleThreadExecutor implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnably("synchronized #1"));
        executorService.execute(new Runnably("synchronized #2"));
        executorService.execute(new Runnably("synchronized #3"));
        hardWork();

        executorService.shutdown();
    }
}
