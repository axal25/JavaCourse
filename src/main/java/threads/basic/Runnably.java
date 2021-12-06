package threads.basic;

import threads.IHardWorker;

public class Runnably implements Runnable, IHardWorker {

    @Override
    public void run() {
        System.out.println(Thready.class.getSimpleName() + "#run - start");
        hardWork();
        System.out.println(Thready.class.getSimpleName() + "#run - finish");
    }
}
