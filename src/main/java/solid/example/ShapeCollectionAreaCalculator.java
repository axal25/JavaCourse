package solid.example;

import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShapeCollectionAreaCalculator {

    private final AreaFul[] areaFuls;

    public ShapeCollectionAreaCalculator(AreaFul... areaFuls) {
        this.areaFuls = areaFuls;
    }

    public AreaFul[] getShapes() {
        return areaFuls;
    }

    double getAreasSum() {
        double[] areas = new double[]{
                getArea1(),
                getArea2(),
                getArea3(),
                getArea4(),
                getArea5(),
                getArea6(),
                getArea7()
        };

        IntStream.range(0, areas.length - 1)
                .parallel()
                .forEach(i -> {
                    if (areas[i] != areas[i + 1]) {
                        throw new RuntimeException(
                                (String) IntStream.range(0, areas.length).boxed().collect(Collectors.collectingAndThen(
                                                Collector.of(
                                                        StringBuilder::new,
                                                        (sb, j) -> sb
                                                                .append(StringUtils.NL)
                                                                .append(StringUtils.TAB)
                                                                .append(j + 1)
                                                                .append(".")
                                                                .append(i + 1 == j ? "!!!" : StringUtils.EMPTY)
                                                                .append(areas[j])
                                                                .append(i + 1 == j ? "!!!" : StringUtils.EMPTY),
                                                        UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                                        StringBuilder::toString
                                                ),
                                                areasString -> new StringBuilder().append("Programming logic error.").append(areasString).toString()
                                        )
                                )
                        );
                    }
                });

        return areas[0];
    }

    private double getArea1() {
        return Arrays.stream(areaFuls).map(AreaFul::getArea).reduce(0.0, Double::sum);
    }

    private double getArea2() {
        return Arrays.stream(areaFuls).parallel().map(AreaFul::getArea).reduce(0.0, Double::sum);
    }

    private double getArea3() {
        return Arrays.stream(areaFuls).mapToDouble(AreaFul::getArea).sum();
    }

    private double getArea4() {
        return Arrays.stream(areaFuls).map(AreaFul::getArea).collect(Collectors.summingDouble(d -> d));
    }

    private double getArea5() {
        return Arrays.stream(areaFuls).map(AreaFul::getArea).collect(Collectors.summingDouble(Double::doubleValue));
    }

    private double getArea6() {
        return Arrays.stream(areaFuls).map(AreaFul::getArea).collect(Collector.of(
                () -> 0.0,
                Double::sum,
                (left, right) -> left.doubleValue() + right.doubleValue()
        ));
    }

    private double getArea7() {
        return Arrays.stream(areaFuls).parallel().map(AreaFul::getArea).collect(Collector.of(
                () -> 0.0,
                Double::sum,
                (left, right) -> left.doubleValue() + right.doubleValue()
        ));
    }
}
