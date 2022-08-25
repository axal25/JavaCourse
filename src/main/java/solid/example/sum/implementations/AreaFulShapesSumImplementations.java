package solid.example.sum.implementations;

import solid.example.AreaFulShape;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AreaFulShapesSumImplementations {

    public static final FIAreaFulShapesSumFul[] sumValidImplementations = new FIAreaFulShapesSumFul[]{
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).map(AreaFulShape::getArea).reduce(0.0, Double::sum),
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).parallel().map(AreaFulShape::getArea).reduce(0.0, Double::sum),
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).mapToDouble(AreaFulShape::getArea).sum(),
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).map(AreaFulShape::getArea).collect(Collectors.summingDouble(d -> d)),
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).map(AreaFulShape::getArea).collect(Collectors.summingDouble(Double::doubleValue)),
    };

    public static final FIAreaFulShapesSumFul[] sumInvalidImplementations = new FIAreaFulShapesSumFul[]{
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).map(AreaFulShape::getArea).collect(Collector.of(
                            () -> 0.0,
                            Double::sum,
                            (left, right) -> left.doubleValue() + right.doubleValue()
                    )),
            (AreaFulShape... areaFulShapes) ->
                    Arrays.stream(areaFulShapes).parallel().map(AreaFulShape::getArea).collect(Collector.of(
                            () -> 0.0,
                            Double::sum,
                            (left, right) -> left.doubleValue() + right.doubleValue()
                    )),
    };

    private AreaFulShapesSumImplementations() {
    }
}
