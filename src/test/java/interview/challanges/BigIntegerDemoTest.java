package interview.challanges;

import org.junit.jupiter.api.*;

import java.io.StringBufferInputStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for BigIntegerDemo")
public class BigIntegerDemoTest {

    @Test
    @Order(1)
    public void getDate() {
        BigIntegerDemo.scanAndPrintAdditionMultiplication(
                new StringBufferInputStream("1000012312000000001239324234098745344444444445093475304958340830457304957304670459680456846569456456\n" +
                        "2342348230021111111111499999912342492342304523957349573497604580234823094723975394857349573945734555")
        );
    }
}
