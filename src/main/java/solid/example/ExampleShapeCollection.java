package solid.example;

public class ExampleShapeCollection {
    public static final AreaFul[] AREA_FULS = new AreaFul[]{
            new Rectangle(2, 3),
            new Square(4),
            new Circle(5),
            new AreaFul() {
                @Override
                public double getArea() {
                    return 15;
                }
            }
    };
}
