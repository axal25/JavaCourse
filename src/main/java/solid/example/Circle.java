package solid.example;

public class Circle implements AreaFulShape {

    private final double radius;

    Circle(final double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}
