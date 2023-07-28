package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.statics;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread;

abstract class AbstractStaticAccessorMethodThread extends AbstractAccessorMethodThread {

    AbstractStaticAccessorMethodThread(String name, LogsContainer logsContainer) {
        super(name, logsContainer);
    }

    @Override
    public boolean isStatic() {
        return true;
    }
}
