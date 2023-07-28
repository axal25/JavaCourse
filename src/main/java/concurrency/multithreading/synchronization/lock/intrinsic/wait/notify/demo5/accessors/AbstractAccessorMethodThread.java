package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.accessors;

import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4.SynchronizedWaitingContainer;
import concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5.LogsContainer;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class AbstractAccessorMethodThread extends Thread {

    @Getter
    @AllArgsConstructor
    public enum StartingOrFinishing {
        STARTING("starting"), FINISHING("finishing");
        private String value;
    }

    @Getter
    protected final LogsContainer logsContainer;

    public AbstractAccessorMethodThread(String name, LogsContainer logsContainer) {
        super(name);
        this.logsContainer = logsContainer;
    }

    @Override
    public abstract void run();

    public abstract String getAccessedMethodName();

    public abstract boolean isStatic();

    public void log(StartingOrFinishing startingOrFinishing) {
        if (isStatic()) {
            logsContainer.addStatic(getLog(startingOrFinishing));
        } else {
            logsContainer.addInstance(getLog(startingOrFinishing));
        }
    }

    public String getLog(StartingOrFinishing startingOrFinishing) {
        return String.format(
                "%s - %s accessing %s %s::%s",
                getName(),
                startingOrFinishing.getValue(),
                isStatic() ? "(Class/Static)" : "(Object/Instance)",
                SynchronizedWaitingContainer.class.getSimpleName(),
                getAccessedMethodName());
    }
}
