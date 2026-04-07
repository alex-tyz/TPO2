package function.log.module;

import function.log.Ln;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LnTest {
    @ParameterizedTest
    @CsvFileSource(resources ="/ln.csv", numLinesToSkip=1)
    void testLn(double x,double expected){
        Ln ln = new Ln();
        double result = ln.calculate(x,1e-4);
        assertEquals(expected,result,1e-4);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 0.5})
    void shouldMatchMathLog(double x) {
        Ln ln = new Ln();
        assertEquals(Math.log(x), ln.calculate(x, 1e-5), 1e-4);
    }

    @Test
    void shouldHandleNearZeroPositive() {
        Ln ln = new Ln();
        double x = 1e-4;
        assertEquals(Math.log(x), ln.calculate(x, 1e-6), 1e-3);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0})
    void shouldReturnNaNForNonPositiveArguments(double x) {
        Ln ln = new Ln();
        assertTrue(Double.isNaN(ln.calculate(x, 1e-4)));
    }

    @Test
    void shouldReturnNaNForNaNArgument() {
        Ln ln = new Ln();
        assertTrue(Double.isNaN(ln.calculate(Double.NaN, 1e-4)));
    }
}
