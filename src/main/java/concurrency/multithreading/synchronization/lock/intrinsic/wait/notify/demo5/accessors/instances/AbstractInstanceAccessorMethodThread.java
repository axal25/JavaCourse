package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.instances;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors.AbstractAccessorMethodThread;
import lombok.Getter;

abstract class AbstractInstanceAccessorMethodThread extends AbstractAccessorMethodThread {

    @Getter
    private final SynchronizedWaitingContainer synchronizedWaitingContainer;

    AbstractInstanceAccessorMethodThread(String name, LogsContainer logsContainer, SynchronizedWaitingContainer synchronizedWaitingContainer) {
        super(name, logsContainer);
        this.synchronizedWaitingContainer = synchronizedWaitingContainer;
    }

    @Override
    public boolean isStatic() {
        return false;
    }
}
