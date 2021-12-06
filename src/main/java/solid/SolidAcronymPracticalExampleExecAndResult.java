package solid;

import solid.example.ExampleShapeCollection;
import solid.example.ShapeCollectionAreaCalculator;
import solid.example.ShapeCollectionAreaPrinter;
import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronymPracticalExampleExecAndResult {

    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticUtils.printMethodSignature("main");

        new ShapeCollectionAreaPrinter(
                new ShapeCollectionAreaCalculator(
                        ExampleShapeCollection.AREA_FULS
                )
        ).printIndividualAndSumArea();
    }
}
