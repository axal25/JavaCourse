package solid.example;

import solid.example.sum.implementations.AreaFulShapesSumImplementations;
import solid.example.sum.implementations.FIAreaFulShapesSumFul;

import java.util.Random;

public class ShapeCollectionAreaCalculator {

    private final AreaFulShape[] areaFulShapes;

    public ShapeCollectionAreaCalculator(AreaFulShape... areaFulShapes) {
        this.areaFulShapes = areaFulShapes;
    }

    AreaFulShape[] getAreaFulShapes() {
        return areaFulShapes;
    }

    double getAreasSum() {
        int randomIndex = new Random().nextInt(
                AreaFulShapesSumImplementations.sumValidImplementations.length
        );
        return getAreasSum(
                AreaFulShapesSumImplementations.sumValidImplementations[randomIndex]
        );
    }

    double getAreasSum(FIAreaFulShapesSumFul sumImpl) {
        return sumImpl.getAreaSum(areaFulShapes);
    }
}
