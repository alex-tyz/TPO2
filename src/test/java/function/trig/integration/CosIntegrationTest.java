package function.trig.integration;

import function.trig.Cos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CosIntegrationTest {

    @Test
    void simpleCheck() {
        Cos cos = new Cos();
        assertEquals(1, cos.calculate(0, 1e-4), 1e-2);
    }
}