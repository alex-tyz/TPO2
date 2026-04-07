package function.trig.module;

import function.trig.Cos;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CosTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/cos.csv",numLinesToSkip = 1)
    void testCos(double x, double expected) {
        Cos cos = new Cos();
        double result = cos.calculate(x, 1e-4);
        assertEquals(expected, result, 1e-2);
    }

    @ParameterizedTest
    @MethodSource("cosClasses")
    void shouldMatchMathCos(double x, double expected) {
        Cos cos = new Cos();
        assertEquals(expected, cos.calculate(x, 1e-5), 1e-3);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void shouldReturnNaNForInvalidInputs(double input) {
        Cos cos = new Cos();
        assertTrue(Double.isNaN(cos.calculate(input, 1e-5)));
    }

    private static Stream<Arguments> cosClasses() {
        return Stream.of(
                arguments(0.0, Math.cos(0.0)),
                arguments(0.001, Math.cos(0.001)),
                arguments(Math.PI / 2, Math.cos(Math.PI / 2)),
                arguments(-1.0, Math.cos(-1.0)),
                arguments(100.0, Math.cos(100.0)),
                arguments(-0.0001, Math.cos(-0.0001))
        );
    }
}
