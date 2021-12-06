package threads;

import java.util.stream.IntStream;

public interface IHardWorker extends IMessager {
    public static final int interval = 100;
    public static final int iterations = 10;

    default public void hardWork() {
        hardWork(null);
    }

    default public void hardWork(String objectName) {
        hardWork(objectName, "hardWork");
    }

    default public void hardWork(final String objectName, final String methodName) {
        IntStream.range(0, iterations).forEach(i -> {
            workIteration(objectName, methodName, i);
            ThreadUtils.sleep(interval);
        });
    }

    default public void workIteration(final String objectName, final String methodName, final int i) {
        System.out.println(getMessage(this.getClass().getSimpleName(), objectName, methodName, "sleeping #" + i));
    }
}
