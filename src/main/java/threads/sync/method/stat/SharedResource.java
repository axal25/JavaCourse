package threads.sync.method.stat;

import threads.IHardWorker;

public class SharedResource implements IHardWorker {
    private String name;

    SharedResource(String name) {
        this.name = name;
    }

    synchronized static void syncHardWork(SharedResource sharedResource, String usingObjectName) {
        sharedResource.hardWork(sharedResource.getName(), "[ " + usingObjectName + " ] syncHardWork > hardWork");
    }

    @Override
    public String getName() {
        return this.name;
    }
}