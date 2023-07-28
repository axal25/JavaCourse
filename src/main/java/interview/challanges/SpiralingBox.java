package interview.challanges;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SpiralingBox {
    public static void main(String[] args) {
        printBox(6, 6,
                "[" +
                        "[1, 1, 1, 1, 1, 1],\n" +
                        " [1, 2, 2, 2, 2, 1],\n" +
                        " [1, 2, 3, 3, 2, 1],\n" +
                        " [1, 2, 3, 3, 2, 1],\n" +
                        " [1, 2, 2, 2, 2, 1],\n" +
                        " [1, 1, 1, 1, 1, 1]" +
                        "]");

        printBox(5, 8,
                "[[1, 1, 1, 1, 1],\n" +
                        " [1, 2, 2, 2, 1],\n" +
                        " [1, 2, 3, 2, 1],\n" +
                        " [1, 2, 3, 2, 1],\n" +
                        " [1, 2, 3, 2, 1],\n" +
                        " [1, 2, 3, 2, 1],\n" +
                        " [1, 2, 2, 2, 1],\n" +
                        " [1, 1, 1, 1, 1]]");

        printBox(10, 9,
                "[[1, 1, 1, 1, 1, 1, 1, 1, 1, 1],\n" +
                        " [1, 2, 2, 2, 2, 2, 2, 2, 2, 1],\n" +
                        " [1, 2, 3, 3, 3, 3, 3, 3, 2, 1],\n" +
                        " [1, 2, 3, 4, 4, 4, 4, 3, 2, 1],\n" +
                        " [1, 2, 3, 4, 5, 5, 4, 3, 2, 1],\n" +
                        " [1, 2, 3, 4, 4, 4, 4, 3, 2, 1],\n" +
                        " [1, 2, 3, 3, 3, 3, 3, 3, 2, 1],\n" +
                        " [1, 2, 2, 2, 2, 2, 2, 2, 2, 1],\n" +
                        " [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]]");
    }

    private static void printBox(int width, int height, String expected) {
        System.out.println("input: (width: " + width + ", height: " + height + ")");
        System.out.println("result 1: \n" + createBoxString(createBox1(width, height)));
        System.out.println("result 2: \n" + createBoxString(createBox2(width, height)));
        System.out.println("expected: \n" + expected);
    }

    private static String createBoxString(Integer[][] box) {
        return "[" +
                Arrays.stream(box)
                        .map(array -> "[" +
                                Arrays.stream(array)
                                        .map(String::valueOf)
                                        .collect(Collectors.joining(", ")) +
                                "]")
                        .collect(Collectors.joining(",\n ")) +
                "]";
    }

    private static Integer[][] createBox1(int width, int height) {
        // 1. 1st and last row and column == 1
        // 2. 2nd and second-to-last row and column == 2
        // 3. 3rd and third-to-last row and column == 3
        // ...
        // n. n-th and (last-n)-th row and column == n
        Integer[][] box = new Integer[height][width];
        int hMaxIndex = height - 1;
        int wMaxIndex = width - 1;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int hEdgeDistance = Math.min(h, (hMaxIndex - h));
                int wEdgeDistance = Math.min(w, (wMaxIndex - w));
                box[h][w] = Math.min(hEdgeDistance, wEdgeDistance) + 1;
            }
        }
        return box;
    }

    private static Integer[][] createBox2(int width, int height) {
        Integer[][] box = new Integer[height][width];

        double hCenter = height / 2.0;
        double wCenter = width / 2.0;

        int hPositiveStart = (int) Math.ceil(hCenter);
        int hNegativeStart = (int) Math.floor(hCenter);

        int wPositiveStart = (int) Math.ceil(wCenter);
        int wNegativeStart = (int) Math.floor(wCenter);

        int hMaxValue = Math.floorDiv(height, 2);
        int wMaxValue = Math.floorDiv(width, 2);
        int maxValue = Math.min(hMaxValue, wMaxValue);

        for (int hP = hPositiveStart; hP < height; hP++) {
            for (int wP = wPositiveStart; wP < width; wP++) {
                int hCenterDistance = Math.abs(hPositiveStart - hP);
                int wCenterDistance = Math.abs(wPositiveStart - wP);
                int minCenterDistance = Math.max(hCenterDistance, wCenterDistance);
                box[hP][wP] = 1 + maxValue - minCenterDistance;
            }
        }

        for (int hN = hNegativeStart; hN >= 0; hN--) {
            for (int wN = wNegativeStart; wN >= 0; wN--) {
                int hCenterDistance = Math.abs(Math.abs(hNegativeStart - hN));
                int wCenterDistance = Math.abs(Math.abs(wNegativeStart - wN));
                int minCenterDistance = Math.max(hCenterDistance, wCenterDistance);
                box[hN][wN] = 1 + maxValue - minCenterDistance;
            }
        }

//        for (int hP = hPositiveStart,
//             hN = hNegativeStart;
//             hP < height && hN >= 0;
//             hP++, hN--) {
//
//            for (int wP = wPositiveStart,
//                 wN = wNegativeStart;
//                 wP < width && wN >= 0;
//                 wP++, wN--) {
//
//                box[hP][wP] = Math.max(hMaxValue - hPositiveStart - hP, wMaxValue - wPositiveStart - wP);
//                box[hN][wP] = Math.max(hMaxValue - hNegativeStart - hN, wMaxValue - wPositiveStart - wP);
//                box[hP][wN] = Math.max(hMaxValue - hPositiveStart - hP, wMaxValue - wNegativeStart - wN);
//                box[hN][wN] = Math.max(hMaxValue - hNegativeStart - hN, wMaxValue - wNegativeStart - wN);
//            }
//        }
//
//        for (int hP = hPositiveStart,
//             hN = hNegativeStart;
//             hP < height && hN >= 0;
//             hP++, hN--) {
//
//            for (int wP = wPositiveStart,
//                 wN = wNegativeStart;
//                 wP < width && wN >= 0;
//                 wP++, wN--) {
//
//                box[hP][wP] = Math.max(hMaxValue - hPositiveStart - hP, wMaxValue - wPositiveStart - wP);
//                box[hN][wP] = Math.max(hMaxValue - hNegativeStart - hN, wMaxValue - wPositiveStart - wP);
//                box[hP][wN] = Math.max(hMaxValue - hPositiveStart - hP, wMaxValue - wNegativeStart - wN);
//                box[hN][wN] = Math.max(hMaxValue - hNegativeStart - hN, wMaxValue - wNegativeStart - wN);
//            }
//        }

        return box;
    }

}
