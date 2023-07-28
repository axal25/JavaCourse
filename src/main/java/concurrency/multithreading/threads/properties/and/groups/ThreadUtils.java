package concurrency.multithreading.threads.properties.and.groups;

import utils.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static utils.StringUtils.*;

public class ThreadUtils {

    static void printAttributes(Thread thread, String threadDescription) {
        printAttributes(thread, threadDescription, false);
    }

    static void printAttributes(Thread thread, String threadDescription, Boolean isStackTraceIncluded) {
        System.out.print(getAttributesPrettyStringBuilder(thread, threadDescription, isStackTraceIncluded));
    }

    static StringBuilder getAttributesPrettyStringBuilder(Thread thread, String threadDescription, Boolean isStackTraceIncluded) {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Thread attributes")
                .append(isBlank(threadDescription) ? EMPTY : String.format(" for %s", threadDescription))
                .append(":")
                .append(NL)
                .append(TAB).append("id: ").append(thread.getId()).append(NL)
                .append(TAB).append("name: ").append(thread.getName()).append(NL)
                .append(TAB).append("is alive: ").append(thread.isAlive()).append(NL)
                .append(TAB).append("is daemon: ").append(thread.isDaemon()).append(NL)
                .append(TAB).append("is interrupted: ").append(thread.isInterrupted()).append(NL)
                .append(TAB).append("state: ").append(thread.getState()).append(NL)
                .append(TAB).append("thread group: ").append(thread.getThreadGroup()).append(NL)
                .append(TAB).append("uncaught exception handler: ").append(thread.getUncaughtExceptionHandler()).append(NL)
                .append(TAB).append("priority: ").append(thread.getPriority()).append(NL);
        if (Boolean.TRUE.equals(isStackTraceIncluded)) {
            stringBuilder
                    .append(TAB)
                    .append("stack trace: ")
                    .append(getStackTracePrettyStringBuilder(thread.getStackTrace()));
        }
        return stringBuilder;
    }

    static StringBuilder getStackTracePrettyStringBuilder(StackTraceElement[] stackTrace) {
        String prettyStackTrace = Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(String.format(", %s", NL)));
        return new StringBuilder()
                .append("[")
                .append(StringUtils.isBlank(prettyStackTrace)
                        ? EMPTY
                        : String.format("%s%s%s", NL, prettyStackTrace, NL))
                .append("]")
                .append(StringUtils.isBlank(prettyStackTrace) ? EMPTY : NL);
    }
}
