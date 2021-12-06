package threads.notify;

import threads.IHardWorker;
import threads.basic.Thready;

public class Runnably implements Runnable, IHardWorker {
    private final MainThreadNotify toNotify;
    private final String name;
    private int value;

    public Runnably(String name, MainThreadNotify toNotify) {
        this.name = name;
        this.toNotify = toNotify;
    }

    @Override
    public void run() {
        System.out.println(Thready.class.getSimpleName() + "#run - start");
        hardWork();
        System.out.println(Thready.class.getSimpleName() + "#run - finish");
    }

    @Override
    public void hardWork(String objectName, String methodName) {
        IHardWorker.super.hardWork(objectName, methodName);
        synchronized (this.toNotify) {
            System.out.println(getName() + " - notify");
            this.toNotify.notify();
        }
    }

    @Override
    public void workIteration(String objectName, String methodName, int i) {
        IHardWorker.super.workIteration(objectName, methodName, i);
        value = i;
    }

    @Override
    public String getName() {
        return this.name;
    }

    int getValue() {
        return this.value;
    }
}
