package threads.basic;

import threads.IHardWorker;

public class Thready extends Thread implements IHardWorker {

    @Override
    public synchronized void start() {
        System.out.println(this.getClass().getSimpleName() + "#start - start");
        super.start();
        System.out.println(Thready.class.getSimpleName() + "#start - finish");
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + "#run - start");
        super.run();
        hardWork();
        System.out.println(this.getClass().getSimpleName() + "#run - finish");
    }
}
