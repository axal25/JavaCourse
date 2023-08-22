package collections.array;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayTest {
    /**
     * Find Minimum in Rotated Sorted Array
     * Integer Array, of N length, sorted in ASCENDING order, and is rotated between 1 and N times.
     * Given the sorted array numbs of unique elements return the minimum element in the array.
     * The algorithm needs to run in O = Log(n)
     * ---------
     * Log(n) => Binary Search
     */

    @Test
    void get1() {
        SortedRotateArrayGetter sortedRotateArrayGetter = new SortedRotateArrayGetter(new int[]{3, 4, 5, 1, 2});
        assertThat(sortedRotateArrayGetter.getMin()).isEqualTo(1);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
        assertThat(sortedRotateArrayGetter.getMax()).isEqualTo(5);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
    }

    @Test
    void get2() {
        SortedRotateArrayGetter sortedRotateArrayGetter = new SortedRotateArrayGetter(new int[]{4, 5, 6, 7, 0, 1, 3, 2});
        assertThat(sortedRotateArrayGetter.getMin()).isEqualTo(0);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
        assertThat(sortedRotateArrayGetter.getMax()).isEqualTo(7);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
    }

    @Test
    void get3() {
        SortedRotateArrayGetter sortedRotateArrayGetter = new SortedRotateArrayGetter(new int[]{11, 13, 15, 17});
        assertThat(sortedRotateArrayGetter.getMin()).isEqualTo(11);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
        assertThat(sortedRotateArrayGetter.getMax()).isEqualTo(17);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
    }

    @Test
    void get4() {
        SortedRotateArrayGetter sortedRotateArrayGetter = new SortedRotateArrayGetter(new int[]{5, 6, 7, 8, 9, 10, 1, 2, 3});
        assertThat(sortedRotateArrayGetter.getMin()).isEqualTo(1);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
        assertThat(sortedRotateArrayGetter.getMax()).isEqualTo(10);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
    }

    @Test
    void get5() {
        SortedRotateArrayGetter sortedRotateArrayGetter = new SortedRotateArrayGetter(new int[]{30, 40, 50, 10, 20});
        assertThat(sortedRotateArrayGetter.getMin()).isEqualTo(10);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
        assertThat(sortedRotateArrayGetter.getMax()).isEqualTo(50);
        assertThat(sortedRotateArrayGetter.getIterations()).isEqualTo((int) Math.ceil(Math.log(sortedRotateArrayGetter.getNums().length)));
    }

    @Getter
    private static final class SortedRotateArrayGetter {
        private final int[] nums;
        private int iterations;

        public SortedRotateArrayGetter(int[] nums) {
            this.nums = nums;
        }

        public int getMin() {
            iterations = 0;
            int left = 0;
            int right = nums.length - 1;
            while (left < right) {
                iterations++;
                // avoid right == mid
                int mid = Math.floorDiv(left + right, 2);
                if (nums[mid] > nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            return nums[left];
        }

        public int getMax() {
            iterations = 0;
            int left = 0;
            int right = nums.length - 1;
            while (left < right) {
                iterations++;
                // avoid left == mid
                int mid = Math.ceilDiv(left + right, 2);
                if (nums[left] > nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid;
                }
            }

            return nums[left];
        }
    }
}
