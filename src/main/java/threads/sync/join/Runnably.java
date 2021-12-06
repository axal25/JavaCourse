package threads.sync.join;

import threads.IHardWorker;

public class Runnably implements Runnable, IHardWorker {
    private String name;

    public Runnably(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "start"));
        hardWork(getName());
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "finish"));
    }

    @Override
    public String getName() {
        return this.name;
    }
}
