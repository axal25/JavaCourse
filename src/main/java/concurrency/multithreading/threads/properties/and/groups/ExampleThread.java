package concurrency.multithreading.threads.properties.and.groups;

class ExampleThread extends Thread {

    ExampleThread(ThreadGroup group, Runnable runnable, String name) {
        this(group, runnable, name, true, Thread.MAX_PRIORITY);
    }

    private ExampleThread(ThreadGroup group, Runnable runnable, String name, boolean isDaemon, int priority) {
        super(group, runnable, name);
        setDaemon(isDaemon);
        setPriority(priority);
    }
}
