package function.function.integration;

import function.Function;
import function.log.LogSystem;
import function.trig.Cos;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class FunctionIntegrationTest {

    @Test
    void testBranching() {
        Cos cos = mock(Cos.class);
        LogSystem logSystem = mock(LogSystem.class);

        Function function = new Function(cos, logSystem);

        function.calculate(-1, 1e-4);

        verify(cos, times(1)).calculate(anyDouble(), anyDouble());
        verify(logSystem, never()).calculate(anyDouble(), anyDouble());

        function.calculate(2, 1e-4);

        verify(logSystem, times(1)).calculate(anyDouble(), anyDouble());
    }
}