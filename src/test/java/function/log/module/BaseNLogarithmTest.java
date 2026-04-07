package function.log.module;

import function.log.BaseNLogarithm;
import function.log.Ln;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class BaseNLogarithmTest {

    private static final double EPS = 1e-4;
    private static final double DELTA = 1e-3;

    @ParameterizedTest
    @CsvFileSource(resources = "/log2.csv", numLinesToSkip = 1)
    void testLogBase2(double x, double expected) {
        BaseNLogarithm log2 = new BaseNLogarithm(new Ln(), 2);
        assertEquals(expected, log2.calculate(x, EPS), DELTA);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log10.csv", numLinesToSkip = 1)
    void testLogBase10(double x, double expected) {
        BaseNLogarithm log10 = new BaseNLogarithm(new Ln(), 10);
        assertEquals(expected, log10.calculate(x, EPS), DELTA);
    }

    @ParameterizedTest
    @CsvSource({"2, 2, 1", "0.5, 2, -1", "1, 10, 0"})
    void shouldMatchExpectedValues(double x, double base, double expected) {
        BaseNLogarithm log = new BaseNLogarithm(new Ln(), base);
        assertEquals(expected, log.calculate(x, 1e-5), 1e-4);
    }

    @ParameterizedTest
    @CsvSource({"2, 1", "2, -2", "0, 2", "NaN, 2"})
    void shouldReturnNaNForInvalidArguments(double x, double base) {
        BaseNLogarithm log = new BaseNLogarithm(new Ln(), base);
        assertTrue(Double.isNaN(log.calculate(x, 1e-5)));
    }
}
