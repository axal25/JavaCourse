package solid.example;

import org.junit.jupiter.api.*;
import solid.example.sum.implementations.AreaFulShapesSumImplementations;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test ShapeCollectionAreaCalculator")
public class ShapeCollectionAreaCalculatorTest {

    private ShapeCollectionAreaCalculator shapeCollectionAreaCalculator;

    @BeforeEach
    void beforeEach() {
        shapeCollectionAreaCalculator = new ShapeCollectionAreaCalculator(
                ExampleShapeCollection.AREA_FULS
        );
    }

    @Test
    @Order(0)
    public void assert_getAreaFulShapes_returnsExpectedShapes() {
        assertEquals(ExampleShapeCollection.AREA_FULS, shapeCollectionAreaCalculator.getAreaFulShapes());
    }

    @Test
    @Order(1)
    public void assert_getAreasSum_randomValidImplementation_returnsExpectedAreasSum() {
        assertEquals(ExampleShapeCollection.EXPECTED_AREAS_SUM, shapeCollectionAreaCalculator.getAreasSum());
    }

    @Test
    @Order(2)
    public void assert_getAreasSum_allValidImplementations_returnExpectedAreasSum() {
        for (int i = 0; i < AreaFulShapesSumImplementations.sumValidImplementations.length; i++) {
            assertEquals(
                    ExampleShapeCollection.EXPECTED_AREAS_SUM,
                    shapeCollectionAreaCalculator.getAreasSum(
                            AreaFulShapesSumImplementations.sumValidImplementations[i]
                    ),
                    String.format("Implementation with index %d failed.", i)
            );
        }
    }

    @Test
    @Order(2)
    public void assert_getAreasSum_allInalidImplementations_returnExpectedAreasSum() {
        for (int i = 0; i < AreaFulShapesSumImplementations.sumInvalidImplementations.length; i++) {
            assertEquals(
                    0,
                    shapeCollectionAreaCalculator.getAreasSum(
                            AreaFulShapesSumImplementations.sumInvalidImplementations[i]
                    ),
                    String.format("Implementation with index %d failed.", i)
            );
        }
    }
}
