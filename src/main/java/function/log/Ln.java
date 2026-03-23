package function.log;

import function.AbstractFunction;

public class Ln extends AbstractFunction {

    @Override
    public double calculate(double x, double eps) {
        if (x <= 0) return Double.NaN;

        double t = (x - 1) / (x + 1);
        double term = t;
        double sum = 0;
        int n = 0;

        while (Math.abs(term) > eps) {
            sum += term;
            n++;
            term *= t * t * (2*n - 1) / (2*n + 1);
        }

        return 2 * sum;
    }
}
