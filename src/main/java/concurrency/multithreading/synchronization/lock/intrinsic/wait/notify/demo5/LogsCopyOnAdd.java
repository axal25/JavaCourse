package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogsCopyOnAdd {
    private ArrayList<String> logs = new ArrayList<>();

    public synchronized boolean add(String newLog) {
        logs = new ArrayList<>(logs);
        logs.add(newLog);
        return true;
    }

    public void reset() {
        logs = new ArrayList<>();
    }

    public static LogsCopyOnAdd of(String... logs) {
        LogsCopyOnAdd logsCopyOnAdd = new LogsCopyOnAdd();
        if (logs != null) {
            Arrays.stream(logs).forEach(logsCopyOnAdd::add);
        }
        return logsCopyOnAdd;
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
        if (!(object instanceof LogsCopyOnAdd)) return false;
        LogsCopyOnAdd other = (LogsCopyOnAdd) object;
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
