package interview.challanges;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class BigDecimalDemoTest {

    @Test
    void getSortedElements() {
        assertThat(
                BigDecimalDemo.getSortedElements(
                        9,
                        new String[]{
                                "-100",
                                "50",
                                "0",
                                "56.6",
                                "90",
                                "0.12",
                                ".12",
                                "02.34",
                                "000.000"
                        }))
                .isEqualTo(new String[]{
                        "90",
                        "56.6",
                        "50",
                        "02.34",
                        "0.12",
                        ".12",
                        "0",
                        "000.000",
                        "-100"
                });
    }
}
