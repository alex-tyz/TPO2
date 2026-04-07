package function.log;

import function.AbstractFunction;

public class Ln extends AbstractFunction {

    @Override
    public double calculate(double x, double eps) {
        if (Double.isNaN(x) || Double.isNaN(eps) || !Double.isFinite(x) || eps <= 0 || x <= 0) {
            return Double.NaN;
        }

        double t = (x - 1) / (x + 1);
        double term = t;
        double sum = 0;
        int n = 0;
        double threshold = eps / 10.0;
        if (threshold <= 0) {
            threshold = Double.MIN_NORMAL;
        }

        while (Math.abs(term) > threshold && n < 100000) {
            sum += term;
            n++;
            term *= t * t * (2*n - 1) / (2*n + 1);
        }

        return 2 * sum;
    }
}
