package function.function.integration;

import function.Function;
import function.log.BaseNLogarithm;
import function.log.Ln;
import function.log.LogSystem;
import function.trig.Cos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FunctionIntegrationTest {

    private static final double EPS = 1e-4;

    @Test
    void testBranching() {
        Cos cos = mock(Cos.class);
        LogSystem logSystem = mock(LogSystem.class);
        Function function = new Function(cos, logSystem);

        function.calculate(-1, EPS);

        assertAll(
                () -> verify(cos, times(1)).calculate(anyDouble(), anyDouble()),
                () -> verify(logSystem, never()).calculate(anyDouble(), anyDouble())
        );

        function.calculate(2, EPS);

        verify(logSystem, times(1)).calculate(anyDouble(), anyDouble());
    }

    @Test
    void shouldReturnCosBranchValueAndSkipLogs() {
        Cos cos = mock(Cos.class);
        when(cos.calculate(eq(-1.0), anyDouble())).thenReturn(0.5);

        Ln ln = mock(Ln.class);
        BaseNLogarithm log2 = mock(BaseNLogarithm.class);
        BaseNLogarithm log10 = mock(BaseNLogarithm.class);
        LogSystem logSystem = new LogSystem(ln, log2, log10);

        Function function = new Function(cos, logSystem);
        double result = function.calculate(-1.0, EPS);

        assertAll(
                () -> assertEquals(0.5, result, 1e-9),
                () -> verify(cos).calculate(eq(-1.0), anyDouble()),
                () -> verifyNoInteractions(ln, log2, log10)
        );
    }

    @Test
    void shouldReturnLogBranchValueAndSkipCos() {
        double x = 2.0;
        double lnValue = 0.7;
        double log2Value = 1.2;
        double log10Value = 0.3;

        Cos cos = mock(Cos.class);
        Ln ln = mock(Ln.class);
        BaseNLogarithm log2 = mock(BaseNLogarithm.class);
        BaseNLogarithm log10 = mock(BaseNLogarithm.class);

        when(ln.calculate(eq(x), anyDouble())).thenReturn(lnValue);
        when(log2.calculate(eq(x), anyDouble())).thenReturn(log2Value);
        when(log10.calculate(eq(x), anyDouble())).thenReturn(log10Value);

        LogSystem logSystem = new LogSystem(ln, log2, log10);
        Function function = new Function(cos, logSystem);

        double denominator = Math.pow(log10Value, 3);
        double part = (lnValue * log2Value) / denominator;
        double expected = Math.pow(part * log2Value, 2) + lnValue;

        double result = function.calculate(x, EPS);

        assertAll(
                () -> assertEquals(expected, result, 1e-9),
                () -> verify(ln).calculate(eq(x), anyDouble()),
                () -> verify(log2).calculate(eq(x), anyDouble()),
                () -> verify(log10).calculate(eq(x), anyDouble()),
                () -> verifyNoInteractions(cos)
        );
    }
}
