package function.log.integration;

import function.log.Ln;
import function.log.LogSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class LogSystemIntegrationTest {

    @Test
    void testWithMockLn() {
        Ln ln = mock(Ln.class);

        when(ln.calculate(anyDouble(), anyDouble())).thenReturn(1.0);

        LogSystem logSystem = new LogSystem(ln);

        double result = logSystem.calculate(2, 1e-4);

        assertFalse(Double.isNaN(result));

        verify(ln, atLeastOnce()).calculate(anyDouble(), anyDouble());
    }
}