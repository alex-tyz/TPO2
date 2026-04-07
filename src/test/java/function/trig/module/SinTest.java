package function.trig.module;

import function.trig.Sin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SinTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1)
    void testSinFromTable(double x, double expected) {
        Sin sin = new Sin();
        double result = sin.calculate(x, 1e-5);
        assertEquals(expected, result, 1e-3);
    }

    @ParameterizedTest
    @MethodSource("sinClasses")
    void shouldMatchMathSin(double x, double expected) {
        Sin sin = new Sin();
        assertEquals(expected, sin.calculate(x, 1e-5), 1e-4);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void shouldReturnNaNForInvalidInputs(double input) {
        Sin sin = new Sin();
        assertTrue(Double.isNaN(sin.calculate(input, 1e-5)));
    }

    private static Stream<Arguments> sinClasses() {
        return Stream.of(
                arguments(0.0, Math.sin(0.0)),
                arguments(0.001, Math.sin(0.001)),
                arguments(Math.PI / 2, Math.sin(Math.PI / 2)),
                arguments(-1.0, Math.sin(-1.0)),
                arguments(100.0, Math.sin(100.0)),
                arguments(-0.0001, Math.sin(-0.0001))
        );
    }
}
