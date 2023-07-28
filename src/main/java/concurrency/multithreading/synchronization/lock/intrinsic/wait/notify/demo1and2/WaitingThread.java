package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo1and2;

public class WaitingThread extends Thread {
    private static int instance_counter = 1;

    public WaitingThread() {
        super(String.format("%s-%d",
                WaitingThread.class.getSimpleName(),
                instance_counter++));
    }

    @Override
    public void run() {
        doWait();
    }

    private synchronized void doWait() {
        System.out.printf("%s - waiting%n", getName());
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("%s - stopped waiting%n", getName());
    }

    public synchronized void doNotify() {
        System.out.printf("%s - notifying%n", getName());
        notify();
    }

    public synchronized void doNotifyAll() {
        System.out.printf("%s - notifying all%n", getName());
        notifyAll();
    }
}
