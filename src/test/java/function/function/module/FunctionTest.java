package function.function.module;

import function.Function;
import function.log.Ln;
import function.log.LogSystem;
import function.trig.Cos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionTest {

    private static final double EPS = 1e-5;
    private static final double DELTA = 1e-2;

    private Function function;

    @BeforeEach
    void setUp() {
        Ln ln = new Ln();
        Cos cos = new Cos();
        LogSystem logSystem = new LogSystem(ln);
        function = new Function(cos, logSystem);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv", numLinesToSkip = 1)
    void testFunction(double x, double expected) {
        assertEquals(expected, function.calculate(x, EPS), DELTA);
    }

    @Test
    void shouldUseCosForNegativeX() {
        Cos cos = new Cos();
        double x = -1.0;
        assertEquals(cos.calculate(x, EPS), function.calculate(x, EPS), 1e-4);
    }

    @Test
    void shouldUseCosAtZero() {
        assertEquals(1.0, function.calculate(0.0, EPS), 1e-4);
    }

    @Test
    void shouldUseLogSystemForPositiveX() {
        double x = 2.0;
        double expected = new LogSystem(new Ln()).calculate(x, EPS);
        assertEquals(expected, function.calculate(x, EPS), 1e-4);
    }

    @Test
    void shouldUseLogSystemForPositiveNearZero() {
        double x = 1e-4;
        double expected = new LogSystem(new Ln()).calculate(x, EPS);
        assertEquals(expected, function.calculate(x, EPS), 1e-3);
    }

    @Test
    void shouldReturnNaNForNaNArgument() {
        assertTrue(Double.isNaN(function.calculate(Double.NaN, EPS)));
    }
}
