package threads.sync.block;

import threads.IHardWorker;

public class Runnably implements Runnable, IHardWorker {
    private String name;
    private SharedResource sharedResource;

    public Runnably(String name, SharedResource sharedResource) {
        this.name = name;
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "start"));
        synchronized (sharedResource) {
            sharedResource.syncHardWork(getName());
        }
        System.out.println(getMessage(this.getClass().getSimpleName(), getName(), "run", "finish"));
    }

    @Override
    public String getName() {
        return this.name;
    }
}
