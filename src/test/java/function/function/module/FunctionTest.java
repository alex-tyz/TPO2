package function.function.module;

import function.Function;
import function.log.Ln;
import function.log.LogSystem;
import function.trig.Cos;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/system.csv", numLinesToSkip = 1)
    void testFunction(double x, double expected) {
        Cos cos = new Cos();
        Ln ln = new Ln();
        LogSystem logSystem = new LogSystem(ln);
        Function function = new Function(cos,logSystem);
        double result =function.calculate(x,1e-1);
        assertEquals(expected,result,1e-1);
    }
}
