package concurrency.multithreading.threads.properties.and.groups;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for ThreadPropertiesAndGroups")
public class ThreadPropertiesAndGroupsMainTest {

    @Test
    @Order(1)
    public void mainThread() {
        assertThat(String.valueOf(Thread.currentThread().getId()), matchesPattern("[0-9]+"));
        assertThat(Thread.currentThread().getName(), is(equalTo("main")));
        assertThat(Thread.currentThread().isAlive(), is(equalTo(true)));
        assertThat(Thread.currentThread().isDaemon(), is(equalTo(false)));
        assertThat(Thread.currentThread().isInterrupted(), is(equalTo(false)));
        assertThat(Thread.currentThread().getState(), is(equalTo(Thread.State.RUNNABLE)));
        assertThat(Thread.currentThread().getPriority(), is(equalTo(Thread.NORM_PRIORITY)));

        assertThat(Thread.currentThread().getThreadGroup().getClass(), is(equalTo(ThreadGroup.class)));
        assertThat(Thread.currentThread().getThreadGroup().getName(), is(equalTo("main")));
        assertThat(Thread.currentThread().getThreadGroup().getMaxPriority(), is(equalTo(Thread.MAX_PRIORITY)));

        assertThat(
                Thread.currentThread().getUncaughtExceptionHandler(),
                is(equalTo(Thread.currentThread().getThreadGroup())));
    }

    @Test
    @Order(2)
    public void sleepingThread() {
        SleepingThread sleepingThread = new SleepingThread(1000L);
        sleepingThread.start();
        try {
            Thread.sleep(sleepingThread.getSleepDurationMillis() / 10L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(String.valueOf(sleepingThread.getId()), matchesPattern("[0-9]+"));
        assertThat(sleepingThread.getName(), matchesPattern("Thread-[0-9]+"));
        assertThat(sleepingThread.getState(), is(equalTo(Thread.State.TIMED_WAITING)));

        assertThat(sleepingThread.isAlive(), is(equalTo(Thread.currentThread().isAlive())));
        assertThat(sleepingThread.isDaemon(), is(equalTo(Thread.currentThread().isDaemon())));
        assertThat(sleepingThread.isInterrupted(), is(equalTo(Thread.currentThread().isInterrupted())));
        assertThat(sleepingThread.getPriority(), is(equalTo(Thread.currentThread().getPriority())));

        assertThat(
                sleepingThread.getThreadGroup(),
                is(equalTo(Thread.currentThread().getThreadGroup())));

        assertThat(
                sleepingThread.getUncaughtExceptionHandler(),
                is(equalTo(Thread.currentThread().getUncaughtExceptionHandler())));

        try {
            sleepingThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    public void exampleThreadOk() {
        String exampleThreadGroupOkName = "example thread group ok name";
        ExampleThreadGroup exampleThreadGroupOk = new ExampleThreadGroup(exampleThreadGroupOkName);
        String exampleThreadOkName = "example thread ok name";
        ExampleThreadOk exampleThreadOk = new ExampleThreadOk(
                exampleThreadGroupOk,
                exampleThreadOkName);
        exampleThreadOk.start();

        assertThat(String.valueOf(exampleThreadOk.getId()), matchesPattern("[0-9]+"));
        assertThat(exampleThreadOk.getName(), is(equalTo(exampleThreadOkName)));
        assertThat(exampleThreadOk.getPriority(), is(equalTo(Thread.MAX_PRIORITY)));

        assertThat(exampleThreadOk.getState(), is(equalTo(Thread.State.RUNNABLE)));
        assertThat(exampleThreadOk.isAlive(), is(equalTo(true)));
        assertThat(exampleThreadOk.isDaemon(), is(equalTo(true)));
        assertThat(exampleThreadOk.isInterrupted(), is(equalTo(false)));

        assertThat(
                exampleThreadOk.getThreadGroup(),
                is(equalTo(exampleThreadGroupOk)));
        assertThat(
                exampleThreadOk.getThreadGroup().getName(),
                is(equalTo(exampleThreadGroupOkName)));
        assertThat(
                exampleThreadOk.getThreadGroup().getMaxPriority(),
                is(equalTo(Thread.currentThread().getThreadGroup().getMaxPriority())));

        assertThat(
                exampleThreadOk.getUncaughtExceptionHandler(),
                is(equalTo(exampleThreadOk.getThreadGroup())));

        try {
            exampleThreadOk.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(exampleThreadOk.getState(), is(equalTo(Thread.State.TERMINATED)));
        assertThat(exampleThreadOk.isAlive(), is(equalTo(false)));
        assertThat(exampleThreadOk.isDaemon(), is(equalTo(true)));
        assertThat(exampleThreadOk.isInterrupted(), is(equalTo(false)));
        assertThat(
                exampleThreadOk.getThreadGroup(),
                is(equalTo(null)));

        assertThat(
                exampleThreadOk.getUncaughtExceptionHandler(),
                is(equalTo(null)));
    }

    @Test
    @Order(4)
    public void exampleThreadThrows() {
        String exampleThreadGroupThrowsName = "example thread group throws name";
        ExampleThreadGroup exampleThreadGroupThrows = new ExampleThreadGroup(exampleThreadGroupThrowsName);
        String exampleThreadThrowsName = "example thread throws name";
        ExampleThreadThrows exampleThreadThrows = new ExampleThreadThrows(
                exampleThreadGroupThrows,
                exampleThreadThrowsName);
        exampleThreadThrows.start();

        assertThat(String.valueOf(exampleThreadThrows.getId()), matchesPattern("[0-9]+"));
        assertThat(exampleThreadThrows.getName(), is(equalTo(exampleThreadThrowsName)));
        assertThat(exampleThreadThrows.getPriority(), is(equalTo(Thread.MAX_PRIORITY)));

        assertThat(exampleThreadThrows.getState(), is(equalTo(Thread.State.RUNNABLE)));
        assertThat(exampleThreadThrows.isAlive(), is(equalTo(true)));
        assertThat(exampleThreadThrows.isDaemon(), is(equalTo(true)));
        assertThat(exampleThreadThrows.isInterrupted(), is(equalTo(false)));

        assertThat(
                exampleThreadThrows.getThreadGroup(),
                is(equalTo(exampleThreadGroupThrows)));
        assertThat(
                exampleThreadThrows.getThreadGroup().getName(),
                is(equalTo(exampleThreadGroupThrowsName)));
        assertThat(
                exampleThreadThrows.getThreadGroup().getMaxPriority(),
                is(equalTo(Thread.currentThread().getThreadGroup().getMaxPriority())));

        assertThat(
                exampleThreadThrows.getUncaughtExceptionHandler(),
                is(equalTo(exampleThreadThrows.getThreadGroup())));

        try {
            exampleThreadThrows.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(exampleThreadThrows.getState(), is(equalTo(Thread.State.TERMINATED)));
        assertThat(exampleThreadThrows.isAlive(), is(equalTo(false)));
        assertThat(exampleThreadThrows.isDaemon(), is(equalTo(true)));
        assertThat(exampleThreadThrows.isInterrupted(), is(equalTo(false)));

        assertThat(
                exampleThreadThrows.getThreadGroup(),
                is(equalTo(null)));

        assertThat(
                exampleThreadThrows.getUncaughtExceptionHandler(),
                is(equalTo(null)));
    }
}
