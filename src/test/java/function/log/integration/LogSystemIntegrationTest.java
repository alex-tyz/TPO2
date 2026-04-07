package function.log.integration;

import function.log.Ln;
import function.log.LogSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogSystemIntegrationTest {

    private static final double EPS = 1e-4;

    @Test
    void testWithMockLn() {
        Ln ln = mock(Ln.class);
        when(ln.calculate(anyDouble(), anyDouble())).thenReturn(1.0);

        LogSystem logSystem = new LogSystem(ln);
        double result = logSystem.calculate(2, EPS);

        assertAll(
                () -> assertFalse(Double.isNaN(result)),
                () -> verify(ln, atLeastOnce()).calculate(anyDouble(), anyDouble())
        );
    }
}
