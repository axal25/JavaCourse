package parallel.unit.test;

import com.google.common.truth.Correspondence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelUnitSmokeTest {
    private static final ArrayList<String> TEST_LOGS = new ArrayList<>();
    private static final int SLEEP_DURATION = 100;

    private static final Correspondence<String, String> CORRESPONDENCE = Correspondence.from(
            String::contains,
            "actual log contains expected log");

    @AfterAll
    static void afterAll() {
        List<String> expectedLogs = List.of
                (getLog("firstMethod", "start").split(":")[0],
                        getLog("secondMethod", "start").split(":")[0],
                        getLog("firstMethod", "end").split(":")[0],
                        getLog("secondMethod", "end").split(":")[0]);

        assertThat(TEST_LOGS).hasSize(expectedLogs.size());
        IntStream.range(0, TEST_LOGS.size()).forEach(i ->
                assertThat(TEST_LOGS.get(i)).contains(expectedLogs.get(i)));
        assertThat(TEST_LOGS)
                .comparingElementsUsing(CORRESPONDENCE)
                .containsExactlyElementsIn(expectedLogs);
    }

    @Test
    public void firstMethod() throws InterruptedException {
        sleepWrappedByLogs("firstMethod");
    }

    @Test
    public void secondMethod() throws InterruptedException {
        Thread.sleep(SLEEP_DURATION / 4);
        sleepWrappedByLogs("secondMethod");
    }

    private static void sleepWrappedByLogs(String methodName) throws InterruptedException {
        log(methodName, "start");

        Thread.sleep(SLEEP_DURATION);

        log(methodName, "end");
    }

    private static void log(String methodName, String startOrEnd) {
        String log = getLog(methodName, startOrEnd);
        TEST_LOGS.add(log);
        System.out.println(log);
    }

    private static String getLog(String methodName, String startOrEnd) {
        return String.format(
                "%s#%s() - %s - thread: %s",
                ParallelUnitSmokeTest.class.getSimpleName(),
                methodName,
                startOrEnd,
                Thread.currentThread().getName());
    }
}
