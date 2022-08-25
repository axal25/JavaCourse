package solid.example;

import utils.ClassMethodUtils;

import java.util.stream.IntStream;

public class ShapeCollectionAreaPrinter {

    private final ShapeCollectionAreaCalculator shapeCollectionAreaCalculator;

    public ShapeCollectionAreaPrinter(ShapeCollectionAreaCalculator shapeCollectionAreaCalculator) {
        this.shapeCollectionAreaCalculator = shapeCollectionAreaCalculator;
    }

    public void printIndividualAndSumArea() {
        printIndividualAreas();
        printSumOfAreas();
    }

    private void printIndividualAreas() {
        final AreaFulShape[] areaFulShapes = shapeCollectionAreaCalculator.getAreaFulShapes();
        IntStream.range(0, areaFulShapes.length)
                .forEach(i ->
                        System.out.println(
                                String.format("%s's area: %f",
                                        ClassMethodUtils.getClassSimpleName(areaFulShapes[i].getClass()),
                                        areaFulShapes[i].getArea()
                                )
                        )
                );
    }

    private void printSumOfAreas() {
        try {
            System.out.println(String.format("Total area: %f", shapeCollectionAreaCalculator.getAreasSum()));
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
