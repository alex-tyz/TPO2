package function.log;

import function.AbstractFunction;

public class LogSystem extends AbstractFunction {
    private final Ln ln;
    private final BaseNLogarithm log2;
    private final BaseNLogarithm log10;
    public LogSystem(Ln ln){
        this.ln = ln;
        this.log2 = new BaseNLogarithm(ln,2);
        this.log10 = new BaseNLogarithm(ln,10);
    }
    @Override
    public double calculate(double x,double eps){
        double lnX= ln.calculate(x,eps);
        double log2X= log2.calculate(x,eps);
        double log10X= log10.calculate(x,eps);
        double denominator = log10X*log10X*log10X;
        double part =(lnX*log2X)/denominator;
        double squared =(part * log2X)*(part * log2X);
        return squared + lnX;
    }
}
