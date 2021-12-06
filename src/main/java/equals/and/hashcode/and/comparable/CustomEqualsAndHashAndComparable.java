package equals.and.hashcode.and.comparable;

import java.util.Comparator;
import java.util.Objects;

public class CustomEqualsAndHashAndComparable implements IExampleEqualsAndHashAndComparable {

    static final Comparator<CustomEqualsAndHashAndComparable> COMPARATOR = new Comparator<CustomEqualsAndHashAndComparable>() {
        @Override
        public int compare(CustomEqualsAndHashAndComparable custom1, CustomEqualsAndHashAndComparable custom2) {
            if (custom1 == null && custom2 == null) return 0;
            if (custom1 == null) return Integer.MIN_VALUE;
            return custom1.compareTo(custom2);
        }
    };

    private final int i;

    CustomEqualsAndHashAndComparable(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.format("%s{ i=%d }", super.toString(), i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomEqualsAndHashAndComparable)) return false;
        CustomEqualsAndHashAndComparable that = (CustomEqualsAndHashAndComparable) o;
        return i == that.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }

    @Override
    public int getI() {
        return i;
    }

    @Override
    public int compareTo(IExampleEqualsAndHashAndComparable iExampleEqualsAndHashAndComparable) {
        return i - iExampleEqualsAndHashAndComparable.getI();
    }
}
