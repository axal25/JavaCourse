package concurrency.multithreading.synchronization.lock.intrinsic.synchronizeds.keyword.example1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Intrinsic = Build-in
 */
public class IntrinsicMain1 {

    public static void main(String[] args) throws InterruptedException {
        SynchronizedContainer synchronizedContainer = new SynchronizedContainer();

        List<Thread> toias =
                IntStream.range(0, 10)
                        .mapToObj(i -> new ThreadObjectInstanceAccessor(String.format("toia %d", i), synchronizedContainer))
                        .collect(Collectors.toUnmodifiableList());
        List<Thread> tcsas =
                IntStream.range(0, 10)
                        .mapToObj(i -> new ThreadClassStaticAccessor(String.format("tcsas %d", i)))
                        .collect(Collectors.toUnmodifiableList());
        List<Thread> tboias =
                IntStream.range(0, 10)
                        .mapToObj(i -> new ThreadBlockObjectInstanceAccessor(String.format("tcsas %d", i), synchronizedContainer))
                        .collect(Collectors.toUnmodifiableList());

        Thread toiasStarter = new ThreadStarter("toias", toias);
        Thread tcsasStarter = new ThreadStarter("tcsas", tcsas);
        Thread tboiasStarter = new ThreadStarter("tboias", tboias);
        toiasStarter.start();
        sleep(2L);
        tcsasStarter.start();
        sleep(2L);
        tboiasStarter.start();

        IntStream.range(0, 100)
                .forEach(i -> {
                    printSynchronizedContainer(synchronizedContainer);
                    sleep(15L);
                });

        join(toias, tcsas, tboias);
        join(toiasStarter, tcsasStarter, tboiasStarter);
        printSynchronizedContainer(synchronizedContainer);
    }

    private static void printSynchronizedContainer(SynchronizedContainer synchronizedContainer) {
        System.out.printf("%s { stringVar: %s, staticStringVar: %s, blockStringVar: %s } %n",
                SynchronizedContainer.class.getSimpleName(),
                synchronizedContainer.getStringVar(),
                SynchronizedContainer.getStaticStringVar(),
                synchronizedContainer.getBlockStringVar());
    }

    private static void sleep(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void join(Thread... threads) {
        join(Arrays.asList(threads));
    }

    private static void join(List<Thread>... threads) {
        Arrays.stream(threads).forEach(IntrinsicMain1::join);
    }

    private static void join(List<Thread> threads) {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
