package solid.example;

public class Rectangle implements AreaFul {
    private final double sideA, sideB;

    Rectangle(final double sideA, final double sideB) {
        this.sideA = sideA;
        this.sideB = sideB;
    }

    @Override
    public double getArea() {
        return sideA * sideB;
    }
}
