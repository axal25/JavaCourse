package solid.example;

public class Circle implements AreaFul {

    private final double radius;

    Circle(final double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}
