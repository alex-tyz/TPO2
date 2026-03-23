package function.log.module;

import function.log.Ln;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LnTest {
    @ParameterizedTest
    @CsvFileSource(resources ="/ln.csv", numLinesToSkip=1)
    void testLn(double x,double expected){
        Ln ln = new Ln();
        double result = ln.calculate(x,1e-4);
        assertEquals(expected,result,1e-4);
    }

}
