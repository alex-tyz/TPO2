package function.trig.integration;

import function.trig.Cos;
import function.trig.Sin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CosIntegrationTest {

    private static final double EPS = 1e-4;

    @Test
    void simpleCheck() {
        Cos cos = new Cos();
        assertEquals(1, cos.calculate(0, EPS), 1e-2);
    }

    @Test
    void shouldDelegateToSin() {
        Sin sin = mock(Sin.class);
        when(sin.calculate(eq(0.5), anyDouble())).thenReturn(0.5);

        Cos cos = new Cos(sin);
        double result = cos.calculate(0.5, EPS);
        double expected = Math.sqrt(1 - 0.25);

        assertAll(
                () -> assertEquals(expected, result, 1e-6),
                () -> verify(sin).calculate(eq(0.5), anyDouble())
        );
    }
}
