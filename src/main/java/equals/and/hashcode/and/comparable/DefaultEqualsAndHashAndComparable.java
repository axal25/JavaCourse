package equals.and.hashcode.and.comparable;

import java.util.Comparator;

public class DefaultEqualsAndHashAndComparable implements IExampleEqualsAndHashAndComparable {

    static final Comparator<DefaultEqualsAndHashAndComparable> COMPARATOR = new Comparator<DefaultEqualsAndHashAndComparable>() {
        @Override
        public int compare(DefaultEqualsAndHashAndComparable default1, DefaultEqualsAndHashAndComparable default2) {
            if (default1 == null && default2 == null) return 0;
            if (default1 == null) return Integer.MIN_VALUE;
            return default1.compareTo(default2);
        }
    };

    private final int i;

    DefaultEqualsAndHashAndComparable(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.format("%s{ i=%d }", super.toString(), i);
    }

    @Override
    public int getI() {
        return i;
    }

    @Override
    public int compareTo(IExampleEqualsAndHashAndComparable iExampleEqualsAndHashAndComparable) {
        // return 0; // Default, always is equal in compareTo sense
        // For the sake of TreeMap
        return 1; // Never is equal in compareTo sense
    }
}
