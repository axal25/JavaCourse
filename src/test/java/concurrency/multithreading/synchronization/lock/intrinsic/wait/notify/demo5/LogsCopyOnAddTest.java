package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.demo5;

import org.junit.jupiter.api.*;

import static com.google.common.truth.Truth.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for LogDequeCopyOnAdd")
public class LogsCopyOnAddTest {

    @Test
    void constructor_emptiesAreEqual() {
        assertThat(new LogsCopyOnAdd()).isEqualTo(new LogsCopyOnAdd());
    }

    @Test
    void staticConstructor_emptiesAreEqual() {
        assertThat(LogsCopyOnAdd.of()).isEqualTo(LogsCopyOnAdd.of());
    }

    @Test
    void staticConstructor_1ElementLogsAreEqual() {
        assertThat(LogsCopyOnAdd.of("1")).isEqualTo(LogsCopyOnAdd.of("1"));
    }

    @Test
    void staticConstructor_2NumberElementLogsAreEqual() {
        assertThat(LogsCopyOnAdd.of("1", "2")).isEqualTo(LogsCopyOnAdd.of("1", "2"));
    }

    @Test
    void staticConstructor_2TextElementLogsAreEqual() {
        assertThat(LogsCopyOnAdd.of("test1", "test 2")).isEqualTo(LogsCopyOnAdd.of("test1", "test 2"));
    }
}
