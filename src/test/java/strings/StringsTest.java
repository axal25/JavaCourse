package strings;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;import static com.google.common.truth.Truth.assertThat;

public class StringsTest {
    private static final int interations = 100_000;
    @Test
    void stringLiteral() {
        String literal0 = "literal";
        IntStream.range(0, interations).forEach(unused -> {
            String literal1 = "literal";
            String literal2 = "literal";

            assertThat(literal0).isEqualTo(literal1);
            assertThat(literal1).isEqualTo(literal2);

            assertThat(literal0 == literal1).isTrue();
            assertThat(literal1 == literal2).isTrue();

            assertThat(literal0.hashCode() == literal1.hashCode()).isTrue();
            assertThat(literal1.hashCode() == literal2.hashCode()).isTrue();
        });
    }

    @Test
    void stringObject() {
        String object0 = new String("aString");
        IntStream.range(0, interations).forEach(unused -> {
            String object1 = new String("aString");
            assertThat(object0).isEqualTo(object1);

            assertThat(object0.hashCode() == object1.hashCode()).isTrue();

            assertThat(object0 == object1).isFalse();
        });
    }

    @Test
    void stringObjectVsLiteral() {
        String literal0 = "aString";
        IntStream.range(0, interations).forEach(unused -> {
            String object1 = new String("aString");

            assertThat(literal0).isEqualTo(object1);

            assertThat(literal0.hashCode() == object1.hashCode()).isTrue();

            assertThat(literal0 == object1).isFalse();
        });
    }

    @Test
    void stringLiteralVsObject() {
        String object0 = new String("8902sda890gdda243hjk0-23sadasda4k;dfkl;!@#&^#$");
        IntStream.range(0, interations).forEach(unused -> {
            String literal1 = "8902sda890gdda243hjk0-23sadasda4k;dfkl;!@#&^#$";

            assertThat(object0).isEqualTo(literal1);

            assertThat(object0.hashCode() == literal1.hashCode()).isTrue();

            assertThat(object0 == literal1).isFalse();
        });
    }

    @Test
    void stringLiteralVsObjectVsIntern() {
        String literal0 = "aString";
        IntStream.range(0, interations).forEach(unused -> {
            String object1 = new String("aString");
            String intern1 = object1.intern();

            assertThat(literal0).isEqualTo(object1);
            assertThat(literal0).isEqualTo(intern1);
            assertThat(object1).isEqualTo(intern1);

            assertThat(literal0.hashCode() == object1.hashCode()).isTrue();
            assertThat(literal0.hashCode() == intern1.hashCode()).isTrue();
            assertThat(object1.hashCode() == intern1.hashCode()).isTrue();

            assertThat(literal0 == object1).isFalse();
            assertThat(literal0 == intern1).isTrue();
            assertThat(object1 == intern1).isFalse();
        });
    }
}