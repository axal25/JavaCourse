package concurrency.multithreading.threads.properties.and.groups;

import static utils.StringUtils.NL;

public class ExampleThreadGroup extends ThreadGroup {

    public ExampleThreadGroup() {
        this(ExampleThreadGroup.class.getSimpleName());
    }

    ExampleThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        String threadDescription = thread instanceof ExampleThreadOk ? "Example thread" : "thread";
        String threadExceptionMessage = new StringBuilder()
                .append("Uncaught exception in thread \"")
                .append(thread.getName())
                .append("\"")
                .append(NL)
                .append(ThreadUtils.getAttributesPrettyStringBuilder(thread, threadDescription, true))
                .append(e.getClass().getSimpleName())
                .append("'s message: ")
                .append(e.getMessage())
                .append(NL)
                .append(e.getClass().getSimpleName())
                .append("'s stacktrace: ")
                .append(ThreadUtils.getStackTracePrettyStringBuilder(e.getStackTrace()))
                .toString();
        System.err.print(threadExceptionMessage);
        thread.interrupt();
    }
}
