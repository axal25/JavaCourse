package strings;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;


public class StringBasicsMainTest {

    @Test
    void main_printsExpected() {
        InputStream systemInTmp = System.in;
        PrintStream systemOutTmp = System.out;

        System.setIn(new ByteArrayInputStream(
                ("hello\r\n" +
                        "world")
                        .getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream readableOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(readableOut));

        StringBasicsMain.main(new String[] {});

        assertThat(readableOut.toString())
                .isEqualTo("10\n" +
                        "No\n" +
                        "Hello World\n" +
                        "");

        System.setIn(systemInTmp);
        System.setOut(systemOutTmp);
    }

    @Test
    public void getLengthSumNullSafe() {
        assertThat(StringBasicsMain.lengthSumNullSafe(null, null))
                .isEqualTo(0);
        assertThat(StringBasicsMain.lengthSumNullSafe("", null))
                .isEqualTo(0);
        assertThat(StringBasicsMain.lengthSumNullSafe(null, ""))
                .isEqualTo(0);
        assertThat(StringBasicsMain.lengthSumNullSafe("", ""))
                .isEqualTo(0);
        assertThat(StringBasicsMain.lengthSumNullSafe("1", "1"))
                .isEqualTo(2);
        assertThat(StringBasicsMain.lengthSumNullSafe("hello", "world"))
                .isEqualTo(10);
    }

    @Test
    void compareNullSafe() {
        assertThat(StringBasicsMain.nullSafeCompare(null, null))
                .isEqualTo(0);
        assertThat(StringBasicsMain.nullSafeCompare("", null))
                .isEqualTo(1);
        assertThat(StringBasicsMain.nullSafeCompare(null, ""))
                .isEqualTo(-1);
        assertThat(StringBasicsMain.nullSafeCompare("", ""))
                .isEqualTo(0);
        assertThat(StringBasicsMain.nullSafeCompare("a", "b"))
                .isEqualTo(-1);
        assertThat(StringBasicsMain.nullSafeCompare("b", "a"))
                .isEqualTo(1);
        assertThat(StringBasicsMain.nullSafeCompare("ba", "aa"))
                .isEqualTo(1);
        assertThat(StringBasicsMain.nullSafeCompare("bb", "aa"))
                .isEqualTo(1);
        assertThat(StringBasicsMain.nullSafeCompare("c", "a"))
                .isEqualTo(2);
        assertThat(StringBasicsMain.nullSafeCompare("ca", "aa"))
                .isEqualTo(2);
        assertThat(StringBasicsMain.nullSafeCompare("cc", "aa"))
                .isEqualTo(2);
        assertThat(StringBasicsMain.nullSafeCompare("hello", "world"))
                .isEqualTo(-15);
    }

    @Test
    void getWithFirstLetterCapitalized() {
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(null))
                .isEqualTo(null);
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(""))
                .isEqualTo("");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(" "))
                .isEqualTo(" ");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(" a"))
                .isEqualTo(" A");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(" a "))
                .isEqualTo(" A ");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(" !@.-+1aa "))
                .isEqualTo(" !@.-+1Aa ");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized(" !@.-+1 "))
                .isEqualTo(" !@.-+1 ");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized("hello"))
                .isEqualTo("Hello");
        assertThat(StringBasicsMain.getWithFirstLetterCapitalized("world"))
                .isEqualTo("World");
    }
}
