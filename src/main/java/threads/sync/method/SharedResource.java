package threads.sync.method;

import threads.IHardWorker;

public class SharedResource implements IHardWorker {
    private String name;

    SharedResource(String name) {
        this.name = name;
    }

    synchronized void syncHardWork(String usingObjectName) {
        hardWork(getName(), "[ " + usingObjectName + " ] syncHardWork > hardWork");
    }

    @Override
    public String getName() {
        return this.name;
    }
}