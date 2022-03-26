package solid;

import solid.example.ExampleShapeCollection;
import solid.example.ShapeCollectionAreaCalculator;
import solid.example.ShapeCollectionAreaPrinter;
import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class SolidAcronymPracticalExampleExecAndResult {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");

        new ShapeCollectionAreaPrinter(
                new ShapeCollectionAreaCalculator(
                        ExampleShapeCollection.AREA_FULS
                )
        ).printIndividualAndSumArea();
    }
}
