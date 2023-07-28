package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import lombok.Getter;

@Getter
public class LogsContainer {
    private final LogsCopyOnAdd logDequeGeneral = new LogsCopyOnAdd();
    private final LogsCopyOnAdd logDequeStatic = new LogsCopyOnAdd();
    private final LogsCopyOnAdd logDequeInstance = new LogsCopyOnAdd();

    public void addStatic(String log) {
        logDequeGeneral.add(log);
        logDequeStatic.add(log);
    }

    public void addInstance(String log) {
        logDequeGeneral.add(log);
        logDequeInstance.add(log);
    }
}
