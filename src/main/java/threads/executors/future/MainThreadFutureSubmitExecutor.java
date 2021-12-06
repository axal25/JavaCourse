package threads.executors.future;

import threads.IHardWorker;
import threads.IMainThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainThreadFutureSubmitExecutor implements IHardWorker, IMainThread {

    @Override
    public void runThreads() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future future1 = executorService.submit(new Callably("unsynchronized #1"));
        Future future2 = executorService.submit(new Callably("unsynchronized #2"));
        Future future3 = executorService.submit(new Callably("unsynchronized #3"));

        hardWork();

        printGet(future1, "future1");

        hardWork();

        printGet(future2, "future2");
        printGet(future3, "future3");

        executorService.shutdown();
    }

    private void printGet(Future future, String futureName) {
        try {
            System.out.println(futureName + ".get(): " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}