package function.log.module;

import function.log.BaseNLogarithm;
import function.log.Ln;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseNLogarithmTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/log2.csv", numLinesToSkip = 1)
    void testLogBase2(double x, double expected) {
        Ln ln = new Ln();
        BaseNLogarithm log2 = new BaseNLogarithm(ln, 2);
        double result = log2.calculate(x, 1e-4);
        assertEquals(expected, result, 1e-3);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/log10.csv", numLinesToSkip = 1)
    void testLogBase10(double x, double expected) {
        Ln ln = new Ln();
        BaseNLogarithm log10 = new BaseNLogarithm(ln, 10);
        double result = log10.calculate(x, 1e-4);
        assertEquals(expected, result, 1e-3);
    }
}
