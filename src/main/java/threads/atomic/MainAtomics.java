package threads.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MainAtomics {
    // TODO: https://www.baeldung.com/java-atomic-variables

    public static void main(String[] args) {
        // TODO: Move this
        AtomicInteger atomicInteger = new AtomicInteger();

        atomicInteger.set(-1);
        System.out.println(String.format("atomicInteger: %s", atomicInteger.get()));

        IntStream.range(0, 10).parallel().forEach(i -> {
            Thread thread = new Thread(new Runnable() {
                private final AtomicInteger innerAtomicInteger = atomicInteger;

                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean threadLost = true;
                    while (threadLost) {
                        System.out.println(String.format("%d", i));
                        threadLost = !innerAtomicInteger.compareAndSet(innerAtomicInteger.get(), i);
                    }
                }
            });
            thread.start();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("atomicInteger: %s", atomicInteger.get()));
    }
}
