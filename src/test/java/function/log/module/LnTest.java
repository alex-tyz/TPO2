package function.log.module;

import function.log.Ln;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LnTest {

    private static final double EPS = 1e-4;
    private static final double DELTA = 1e-4;

    private Ln ln;

    @BeforeEach
    void setUp() {
        ln = new Ln();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ln.csv", numLinesToSkip = 1)
    void testLn(double x, double expected) {
        assertEquals(expected, ln.calculate(x, EPS), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 0.5})
    void shouldMatchMathLog(double x) {
        assertEquals(Math.log(x), ln.calculate(x, 1e-5), DELTA);
    }

    @Test
    void shouldHandleNearZeroPositive() {
        double x = 1e-4;
        assertEquals(Math.log(x), ln.calculate(x, 1e-6), 1e-3);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0})
    void shouldReturnNaNForNonPositiveArguments(double x) {
        assertTrue(Double.isNaN(ln.calculate(x, EPS)));
    }

    @Test
    void shouldReturnNaNForNaNArgument() {
        assertTrue(Double.isNaN(ln.calculate(Double.NaN, EPS)));
    }
}
