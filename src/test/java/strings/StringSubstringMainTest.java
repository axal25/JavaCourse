package strings;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;

public class StringSubstringMainTest {

    @Test
    void main_printsExpected() {
        InputStream systemInTmp = System.in;
        PrintStream systemOutTmp = System.out;

        System.setIn(new ByteArrayInputStream(
                ("Helloworld\r\n" +
                        "3 7")
                        .getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream readableOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(readableOut));

        StringSubstringMain.main(new String[] {});

        assertThat(readableOut.toString())
                .isEqualTo("lowo\n" +
                        "");

        System.setIn(systemInTmp);
        System.setOut(systemOutTmp);
    }
}
