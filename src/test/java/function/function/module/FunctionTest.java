package function.function.module;

import function.Function;
import function.log.Ln;
import function.log.LogSystem;
import function.trig.Cos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionTest {
    private static final double EPS = 1e-5;

    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv", numLinesToSkip = 1)
    void testFunction(double x, double expected) {
        Function function = createFunction();
        double result = function.calculate(x, EPS);
        assertEquals(expected, result, 1e-2);
    }

    @Test
    void shouldUseCosForNegativeX() {
        Function function = createFunction();
        Cos cos = new Cos();
        double x = -1.0;
        assertEquals(cos.calculate(x, EPS), function.calculate(x, EPS), 1e-4);
    }

    @Test
    void shouldUseCosAtZero() {
        Function function = createFunction();
        assertEquals(1.0, function.calculate(0.0, EPS), 1e-4);
    }

    @Test
    void shouldUseLogSystemForPositiveX() {
        Function function = createFunction();
        double x = 2.0;
        double expected = new LogSystem(new Ln()).calculate(x, EPS);
        assertEquals(expected, function.calculate(x, EPS), 1e-4);
    }

    @Test
    void shouldUseLogSystemForPositiveNearZero() {
        Function function = createFunction();
        double x = 1e-4;
        double expected = new LogSystem(new Ln()).calculate(x, EPS);
        assertEquals(expected, function.calculate(x, EPS), 1e-3);
    }

    @Test
    void shouldReturnNaNForNaNArgument() {
        Function function = createFunction();
        assertTrue(Double.isNaN(function.calculate(Double.NaN, EPS)));
    }

    private Function createFunction() {
        Ln ln = new Ln();
        Cos cos = new Cos();
        LogSystem logSystem = new LogSystem(ln);
        return new Function(cos, logSystem);
    }
}
