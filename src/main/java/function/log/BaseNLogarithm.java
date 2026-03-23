package function.log;

import function.AbstractFunction;

public class BaseNLogarithm extends AbstractFunction {
    private final Ln ln;
    private final double base;
    public BaseNLogarithm(Ln ln, double base) {
        this.ln = ln;
        this.base = base;
    }
    @Override
    public double calculate(double x, double eps) {
        if (x<=0|| base<=0|| base ==1){
            return Double.NaN;
        }
        return ln.calculate(x, base)/ ln.calculate(base,x);
    }
}
