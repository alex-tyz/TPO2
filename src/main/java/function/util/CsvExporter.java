package function.util;

import function.AbstractFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CsvExporter {
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DECIMAL_FORMAT = new DecimalFormat("0.############", symbols);
    }

    public void export(
            AbstractFunction function,
            double from,
            double to,
            double step,
            double eps,
            Path targetFile
    ) throws IOException {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive");
        }
        if (from > to) {
            throw new IllegalArgumentException("Start value must be less than or equal to end value");
        }
        Files.createDirectories(targetFile.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8)) {
            writer.write("x,y");
            writer.newLine();
            double x = from;
            while (x <= to + 1e-12) {
                double y = function.calculate(x, eps);
                if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                    writer.write(DECIMAL_FORMAT.format(x));
                    writer.write(",");
                    writer.write(DECIMAL_FORMAT.format(y));
                    writer.newLine();
                }
                x += step;
            }
        }
    }
}
