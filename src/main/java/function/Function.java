package function;
import function.log.LogSystem;
import function.trig.Cos;
public class Function extends AbstractFunction{
    private final Cos cos;
    private final LogSystem logSystem;

    public Function(Cos cos, LogSystem logSystem) {
        this.cos = cos;
        this.logSystem = logSystem;
    }
    @Override
    public double calculate(double x,double eps) {
        return (x<=0)
                ? cos.calculate(x,eps)
                : logSystem.calculate(x,eps);
    }
}
