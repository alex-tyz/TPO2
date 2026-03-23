package function.trig.module;
import org.junit.jupiter.params.provider.CsvFileSource;
import function.trig.Cos;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CosTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/cos.csv",numLinesToSkip = 1)
    void testCos(double x, double expected) {
        Cos cos = new Cos();
        double result = cos.calculate(x, 1e-4);
        assertEquals(expected, result, 1e-2);
    }
}
