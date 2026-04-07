package function.trig;

import function.AbstractFunction;

public class Sin extends AbstractFunction {

    @Override
    public double calculate(double x, double eps) {
        if (Double.isNaN(x) || Double.isNaN(eps) || !Double.isFinite(x) || !Double.isFinite(eps) || eps <= 0) {
            return Double.NaN;
        }

        double normalizedX = normalize(x);

        double term = normalizedX;
        double sum = term;
        int n = 1;

        while (Math.abs(term) > eps) {
            term *= -normalizedX * normalizedX / ((2.0 * n) * (2.0 * n + 1));
            sum += term;
            n++;
        }

        return sum;
    }

    private double normalize(double x) {
        double twoPi = 2.0 * Math.PI;
        double result = x % twoPi;
        if (result > Math.PI) {
            result -= twoPi;
        } else if (result < -Math.PI) {
            result += twoPi;
        }
        return result;
    }
}
