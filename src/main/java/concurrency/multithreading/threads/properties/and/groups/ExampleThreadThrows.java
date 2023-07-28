package concurrency.multithreading.threads.properties.and.groups;

import java.util.stream.IntStream;

class ExampleThreadThrows extends ExampleThread {

    private static final Runnable runnable = () -> {
        System.out.printf("%s running", ExampleThreadThrows.class.getSimpleName());
        IntStream.range(0, 1000)
                .forEach(i -> System.out.print("."));
        System.out.printf("%n%s throwing now%n", ExampleThreadOk.class.getSimpleName());
        throw new RuntimeException("test exception");
    };

    ExampleThreadThrows(ThreadGroup group, String name) {
        super(group, runnable, name);
    }
}
