package function.trig;

import function.AbstractFunction;

public class Cos extends AbstractFunction {
    private final Sin sin;

    public Cos() {
        this(new Sin());
    }

    public Cos(Sin sin) {
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double eps) {
        if (eps <= 0) {
            return Double.NaN;
        }

        double sinValue = sin.calculate(x, eps);
        if (Double.isNaN(sinValue) || Double.isInfinite(sinValue)) {
            return Double.NaN;
        }

        double cosSquared = 1.0 - sinValue * sinValue;
        if (cosSquared < 0) {
            if (cosSquared > -eps) {
                cosSquared = 0;
            } else {
                return Double.NaN;
            }
        }

        double cosMagnitude = Math.sqrt(cosSquared);
        return determineCosSign(x) * cosMagnitude;
    }

    private double determineCosSign(double x) {
        double twoPi = 2.0 * Math.PI;
        double normalized = x % twoPi;
        if (normalized < 0) {
            normalized += twoPi;
        }
        return (normalized > Math.PI / 2 && normalized < 3 * Math.PI / 2) ? -1.0 : 1.0;
    }
}
