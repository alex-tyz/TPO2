package function.log.module;

import function.log.BaseNLogarithm;
import function.log.Ln;
import function.log.LogSystem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class LogSystemTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/integration/logIT.csv", numLinesToSkip = 1)
    void shouldUseTableValues(double x, double lnValue, double log2Value, double log10Value) {
        Ln ln = Mockito.mock(Ln.class);
        BaseNLogarithm log2 = Mockito.mock(BaseNLogarithm.class);
        BaseNLogarithm log10 = Mockito.mock(BaseNLogarithm.class);

        when(ln.calculate(eq(x), anyDouble())).thenReturn(lnValue);
        when(log2.calculate(eq(x), anyDouble())).thenReturn(log2Value);
        when(log10.calculate(eq(x), anyDouble())).thenReturn(log10Value);

        LogSystem logSystem = new LogSystem(ln, log2, log10);

        double denominator = Math.pow(log10Value, 3);
        double part = (lnValue * log2Value) / denominator;
        double expected = Math.pow(part * log2Value, 2) + lnValue;

        assertEquals(expected, logSystem.calculate(x, 1e-4), 1e-6);
    }
}
