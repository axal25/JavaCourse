package solid.example;

public class ExampleShapeCollection {
    public static final AreaFulShape[] AREA_FULS = new AreaFulShape[]{
            new Rectangle(2, 3),
            new Square(4),
            new Circle(5),
            new AreaFulShape() {
                @Override
                public double getArea() {
                    return 15;
                }
            }
    };

    static final double EXPECTED_AREAS_SUM = (2 * 3) + (4 * 4) + (Math.PI * 5 * 5) + 15;
}
