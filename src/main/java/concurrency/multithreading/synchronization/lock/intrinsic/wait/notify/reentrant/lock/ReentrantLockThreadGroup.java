package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReentrantLockThreadGroup extends ThreadGroup {
    private static int instanceCounter = 1;

    @Getter
    private final Map<Thread, List<Throwable>> uncaughtExceptions = new HashMap<>();

    ReentrantLockThreadGroup() {
        this(
                String.format(
                        "%s-%d",
                        ReentrantLockThreadGroup.class.getSimpleName(),
                        instanceCounter++));
    }

    private ReentrantLockThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        uncaughtExceptions.putIfAbsent(thread, new ArrayList<>());
        uncaughtExceptions.get(thread).add(e);
        throw new RuntimeException(e);
    }
}
