package threads.executors.future;

import threads.IHardWorker;

import java.util.concurrent.Callable;

public class Callably implements Callable, IHardWorker {
    private String name;
    private int value;

    Callably(String name) {
        this.name = name;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "start"));
        hardWork(getName());
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "finish"));
        return value == 9 ? "success: " + value : value > 0 ? "in-progress: " + value : "not-started: " + value;
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
}
