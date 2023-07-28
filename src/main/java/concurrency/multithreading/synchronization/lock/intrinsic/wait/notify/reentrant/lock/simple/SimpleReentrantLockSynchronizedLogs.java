package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock.simple;

import utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleReentrantLockSynchronizedLogs {
    private final ArrayList<String> logs = new ArrayList<>();

    public synchronized boolean add(String newLog) {
        return logs.add(newLog);
    }

    public static SimpleReentrantLockSynchronizedLogs of(String... logsArray) {
        SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
        if (logsArray != null) {
            Arrays.stream(logsArray)
                    .forEach(logs::add);
        }
        return logs;
    }

    static SimpleReentrantLockSynchronizedLogs flatMap(SimpleReentrantLockSynchronizedLogs... logsArray) {
        SimpleReentrantLockSynchronizedLogs logs = new SimpleReentrantLockSynchronizedLogs();
        if (logsArray != null) {
            Arrays.stream(logsArray)
                    .forEach(logsContainer ->
                            logsContainer.logs
                                    .forEach(logs::add));
        }
        return logs;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getClass().getSimpleName())
                .append("{")
                .append(StringUtils.NL)
                .append(StringUtils.TAB)
                .append("logs=")
                .append(logsToString())
                .append(StringUtils.NL)
                .append("}")
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof SimpleReentrantLockSynchronizedLogs)) return false;
        SimpleReentrantLockSynchronizedLogs other = (SimpleReentrantLockSynchronizedLogs) object;
        if (logs == other.logs) return true;
        if (logs.size() != other.logs.size()) return false;
        return IntStream.range(0, logs.size())
                .allMatch(i -> Objects.equals(logs.get(i), other.logs.get(i)));
    }

    @Override
    public int hashCode() {
        return logs.stream().map(Objects::hash).reduce(97, Integer::sum);
    }

    private String logsToString() {
        if (logs == null) {
            return "null";
        }
        return String.format(
                "[%s%s%s]",
                logs.isEmpty() || logs.size() == 1
                        ? StringUtils.EMPTY
                        : StringUtils.NL,
                logs.stream()
                        .map(log -> String.format("\"%s\"", log))
                        .collect(Collectors.joining(String.format(", %s", StringUtils.NL))),
                logs.isEmpty() || logs.size() == 1
                        ? StringUtils.EMPTY
                        : StringUtils.NL
        );
    }
}
