package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo4;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Logger {
    private static List<String> logs = null;

    public static void printAndAddToLogger(String printPrefix, String log) {
        if (logs != null && !logs.isEmpty() && log != null && log.equals(logs.get(logs.size() - 1))) {
            return;
        }
        System.out.printf("%s%s%n", printPrefix, log);
        addLog(log);
    }

    private synchronized static void addLog(String log) {
        initLogsIfNull();
        logs.add(log);
    }

    private synchronized static void initLogsIfNull() {
        if (logs == null) {
            resetLogs();
        }
    }

    synchronized static void resetLogs() {
        logs = new LinkedList<>();
    }

    synchronized static List<String> getLogsCopy() {
        if (logs == null) {
            return null;
        }
        return List.copyOf(logs);
    }

    synchronized static String toPrettyString() {
        return logs.stream()
                .filter(Objects::nonNull)
                .map(log -> log.matches("^[0-9]\\. SynchronizedWaitingContainer::.*")
                        ? String.format("\t\t%s", log)
                        : log.matches("^[0-9]\\. .*")
                        ? String.format("\t%s", log)
                        : log.matches("^Notifier for .*")
                        ? String.format("\t%s", log)
                        : log.matches("^main.*") ? log
                        : null)
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\r\n"));
    }
}
