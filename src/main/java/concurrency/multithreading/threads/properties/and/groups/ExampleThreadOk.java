package concurrency.multithreading.threads.properties.and.groups;

import java.util.stream.IntStream;

class ExampleThreadOk extends ExampleThread {

    private static final Runnable runnable = () -> {
        System.out.printf("%s running", ExampleThreadOk.class.getSimpleName());
        IntStream.range(0, 1000)
                .forEach(i -> System.out.print("."));
        System.out.printf("%n%s finished%n", ExampleThreadOk.class.getSimpleName());
    };

    ExampleThreadOk(ThreadGroup group, String name) {
        super(group, runnable, name);
    }
}
