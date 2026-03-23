package function.trig;

import function.AbstractFunction;

public class Cos extends AbstractFunction {
    @Override
    public double calculate(double x, double eps) {
        double sum =1;
        double term =1;
        int n =1;
        while (Math.abs(term) > eps) {
            term *= -x*x/((2*n-1)*(2*n));
            sum += term;
            n++;
        }
        return sum;
    }
}
