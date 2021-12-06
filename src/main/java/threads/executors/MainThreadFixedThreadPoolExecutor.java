package threads.executors;

import threads.IHardWorker;
import threads.IMainThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreadFixedThreadPoolExecutor implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new Runnably("unsynchronized #1"));
        executorService.execute(new Runnably("unsynchronized #2"));
        executorService.execute(new Runnably("unsynchronized #3"));
        hardWork();

        executorService.shutdown();
    }
}