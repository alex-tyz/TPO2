package function.trig.module;

import function.trig.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SinTest {

    private static final double EPS = 1e-5;
    private static final double DELTA = 1e-3;

    private Sin sin;

    @BeforeEach
    void setUp() {
        sin = new Sin();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/sin.csv", numLinesToSkip = 1)
    void testSinFromTable(double x, double expected) {
        assertEquals(expected, sin.calculate(x, EPS), DELTA);
    }

    @ParameterizedTest
    @MethodSource("sinClasses")
    void shouldMatchMathSin(double x, double expected) {
        assertEquals(expected, sin.calculate(x, EPS), 1e-4);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void shouldReturnNaNForInvalidInputs(double input) {
        assertTrue(Double.isNaN(sin.calculate(input, EPS)));
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
