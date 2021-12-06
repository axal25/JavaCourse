package equals.and.hashcode.and.comparable;

import java.util.Comparator;

interface IExampleEqualsAndHashAndComparable extends Comparable<IExampleEqualsAndHashAndComparable> {

    public static final Comparator<IExampleEqualsAndHashAndComparable> COMPARATOR = new Comparator<IExampleEqualsAndHashAndComparable>() {
        @Override
        public int compare(IExampleEqualsAndHashAndComparable i1, IExampleEqualsAndHashAndComparable i2) {
            // Default, always equals in sense of compare
            return 2;
            
            // Should be in correct implementation
            // if (i1 == null && i2 == null) return 0;
            // if (i1 == null) return Integer.MIN_VALUE;
            // return i1.compareTo(i2);
        }
    };

    public int getI();
}
