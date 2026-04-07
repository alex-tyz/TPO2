package function.log.module;

import function.log.BaseNLogarithm;
import function.log.Ln;
import function.log.LogSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LogSystemTest {

    private static final double EPS = 1e-4;
    private static final double DELTA = 1e-6;

    @ParameterizedTest
    @CsvFileSource(resources = "/integration/logIT.csv", numLinesToSkip = 1)
    void shouldUseTableValues(double x, double lnValue, double log2Value, double log10Value) {
        Ln ln = mock(Ln.class);
        BaseNLogarithm log2 = mock(BaseNLogarithm.class);
        BaseNLogarithm log10 = mock(BaseNLogarithm.class);

        when(ln.calculate(eq(x), anyDouble())).thenReturn(lnValue);
        when(log2.calculate(eq(x), anyDouble())).thenReturn(log2Value);
        when(log10.calculate(eq(x), anyDouble())).thenReturn(log10Value);

        LogSystem logSystem = new LogSystem(ln, log2, log10);
        double expected = expectedValue(lnValue, log2Value, log10Value);

        assertAll(
                () -> assertEquals(expected, logSystem.calculate(x, EPS), DELTA),
                () -> verify(ln).calculate(eq(x), anyDouble()),
                () -> verify(log2).calculate(eq(x), anyDouble()),
                () -> verify(log10).calculate(eq(x), anyDouble())
        );
    }

    @Test
    void shouldWorkWhenOnlyLnIsMocked() {
        double x = 2.0;
        Ln lnMock = mock(Ln.class);
        double lnValue = 0.7;
        when(lnMock.calculate(eq(x), anyDouble())).thenReturn(lnValue);

        BaseNLogarithm log2 = new BaseNLogarithm(new Ln(), 2);
        BaseNLogarithm log10 = new BaseNLogarithm(new Ln(), 10);
        LogSystem logSystem = new LogSystem(lnMock, log2, log10);

        double expected = expectedValue(lnValue,
                log2.calculate(x, 1e-5),
                log10.calculate(x, 1e-5));

        assertAll(
                () -> assertEquals(expected, logSystem.calculate(x, 1e-5), DELTA),
                () -> verify(lnMock).calculate(eq(x), anyDouble())
        );
    }

    @Test
    void shouldWorkWhenOnlyLog2IsMocked() {
        double x = 2.0;
        Ln ln = new Ln();
        double lnValue = ln.calculate(x, 1e-5);

        BaseNLogarithm log2 = mock(BaseNLogarithm.class);
        double log2Value = 3.0;
        when(log2.calculate(eq(x), anyDouble())).thenReturn(log2Value);

        BaseNLogarithm log10 = new BaseNLogarithm(new Ln(), 10);
        LogSystem logSystem = new LogSystem(ln, log2, log10);

        double expected = expectedValue(lnValue,
                log2Value,
                log10.calculate(x, 1e-5));

        assertAll(
                () -> assertEquals(expected, logSystem.calculate(x, 1e-5), DELTA),
                () -> verify(log2).calculate(eq(x), anyDouble())
        );
    }

    @Test
    void shouldWorkWhenOnlyLog10IsMocked() {
        double x = 2.0;
        Ln ln = new Ln();
        double lnValue = ln.calculate(x, 1e-5);

        BaseNLogarithm log2 = new BaseNLogarithm(new Ln(), 2);

        BaseNLogarithm log10 = mock(BaseNLogarithm.class);
        double log10Value = 0.3;
        when(log10.calculate(eq(x), anyDouble())).thenReturn(log10Value);

        LogSystem logSystem = new LogSystem(ln, log2, log10);

        double expected = expectedValue(lnValue,
                log2.calculate(x, 1e-5),
                log10Value);

        assertAll(
                () -> assertEquals(expected, logSystem.calculate(x, 1e-5), DELTA),
                () -> verify(log10).calculate(eq(x), anyDouble())
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.0, 1.01, 0.0001})
    void shouldMatchRealCalculations(double x) {
        LogSystem logSystem = new LogSystem(new Ln());
        double expected = expectedValue(Math.log(x),
                Math.log(x) / Math.log(2),
                Math.log(x) / Math.log(10));
        assertEquals(expected, logSystem.calculate(x, 1e-5), 1e-2);
    }

    @Test
    void shouldReturnNaNAtSingularity() {
        LogSystem logSystem = new LogSystem(new Ln());
        assertTrue(Double.isNaN(logSystem.calculate(1.0, 1e-5)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0})
    void shouldReturnNaNForInvalidArguments(double x) {
        LogSystem logSystem = new LogSystem(new Ln());
        assertTrue(Double.isNaN(logSystem.calculate(x, 1e-5)));
    }

    private double expectedValue(double lnValue, double log2Value, double log10Value) {
        double denominator = Math.pow(log10Value, 3);
        double part = (lnValue * log2Value) / denominator;
        double squared = part * log2Value;
        return squared * squared + lnValue;
    }
}
