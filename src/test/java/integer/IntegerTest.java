package integer;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntegerTest {

    @Test
    void parseInt_returnsPrimitiveInt() {
        int primitiveInt = Integer.parseInt("1");
        assertThat(primitiveInt).isEqualTo(1);
    }

    @Test
    void valueOf_returnsObjectInteger() {
        Integer objectInteger = Integer.valueOf("1");
        assertThat(objectInteger).isEqualTo(1);
    }

    @Test
    void valueOf_acceptsPrimitiveIntAsParameter() {
        Integer objectInteger = Integer.valueOf(1);
        assertThat(objectInteger).isEqualTo(1);
    }

    @Test
    void parseInt_withRadix() {
        assertThat(Character.MIN_RADIX).isEqualTo(2);
        int base1 = Character.MIN_RADIX - 1;
        assertThat(base1).isEqualTo(1);
        NumberFormatException numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.parseInt("0", 1));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 1));

        int primitiveInt0base2 = Integer.parseInt("0", 2);
        assertThat(primitiveInt0base2).isEqualTo(0);

        int primitiveInt1base2 = Integer.parseInt("1", 2);
        assertThat(primitiveInt1base2).isEqualTo(1);

        int primitiveInt2base2 = Integer.parseInt("10", 2);
        assertThat(primitiveInt2base2).isEqualTo(2);

        int primitiveInt3base2 = Integer.parseInt("11", 2);
        assertThat(primitiveInt3base2).isEqualTo(3);

        int primitiveInt2base3 = Integer.parseInt("2", 3);
        assertThat(primitiveInt2base3).isEqualTo(2);

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.parseInt("0", 0));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 0));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.parseInt("1", 1));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 1));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.parseInt("2", 2));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("For input string: \"%s\" under radix %d", "2", 2));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.parseInt("3", 3));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("For input string: \"%s\" under radix %d", "3", 3));
    }

    @Test
    void valueOf_withRadix() {
        assertThat(Character.MIN_RADIX).isEqualTo(2);
        int base1 = Character.MIN_RADIX - 1;
        assertThat(base1).isEqualTo(1);
        NumberFormatException numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.valueOf("0", 1));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 1));

        Integer objectInteger0base2 = Integer.valueOf("0", 2);
        assertThat(objectInteger0base2).isEqualTo(0);

        Integer objectInteger1base2 = Integer.valueOf("1", 2);
        assertThat(objectInteger1base2).isEqualTo(1);

        Integer objectInteger2base2 = Integer.valueOf("10", 2);
        assertThat(objectInteger2base2).isEqualTo(2);

        Integer objectInteger3base2 = Integer.valueOf("11", 2);
        assertThat(objectInteger3base2).isEqualTo(3);

        Integer objectInteger2base3 = Integer.valueOf("2", 3);
        assertThat(objectInteger2base3).isEqualTo(2);

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.valueOf("0", 0));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 0));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.valueOf("1", 1));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("radix %d less than Character.MIN_RADIX", 1));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.valueOf("2", 2));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("For input string: \"%s\" under radix %d", "2", 2));

        numberFormatException =
                assertThrows(NumberFormatException.class, () -> Integer.valueOf("3", 3));
        assertThat(numberFormatException).hasMessageThat()
                .isEqualTo(String.format("For input string: \"%s\" under radix %d", "3", 3));

    }
}
