package function.log;

import function.AbstractFunction;

public class LogSystem extends AbstractFunction {
    private final Ln ln;
    private final BaseNLogarithm log2;
    private final BaseNLogarithm log10;

    public LogSystem(Ln ln){
        this(ln, new BaseNLogarithm(ln, 2), new BaseNLogarithm(ln, 10));
    }

    public LogSystem(Ln ln, BaseNLogarithm log2, BaseNLogarithm log10){
        this.ln = ln;
        this.log2 = log2;
        this.log10 = log10;
    }
    @Override
    public double calculate(double x,double eps){
        double lnX= ln.calculate(x,eps);
        double log2X= log2.calculate(x,eps);
        double log10X= log10.calculate(x,eps);
        if (Double.isNaN(lnX) || Double.isNaN(log2X) || Double.isNaN(log10X) || log10X == 0.0) {
            return Double.NaN;
        }
        double denominator = log10X*log10X*log10X;
        double part =(lnX*log2X)/denominator;
        double squared =(part * log2X)*(part * log2X);
        return squared + lnX;
    }
}
