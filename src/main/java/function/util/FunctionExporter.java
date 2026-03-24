package function.util;

import function.Function;
import function.log.BaseNLogarithm;
import function.log.Ln;
import function.log.LogSystem;
import function.trig.Cos;

import java.io.IOException;
import java.nio.file.Path;

public class FunctionExporter {
    private final CsvExporter csvExporter;

    public FunctionExporter(CsvExporter csvExporter) {
        this.csvExporter = csvExporter;
    }

    public void exportAll(Path directory,
                          double from,
                          double to,
                          double step,
                          double eps) throws IOException {
        Ln ln = new Ln();
        Cos cos = new Cos();
        BaseNLogarithm log2 = new BaseNLogarithm(ln, 2);
        BaseNLogarithm log10 = new BaseNLogarithm(ln, 10);
        LogSystem logSystem = new LogSystem(ln, log2, log10);
        Function function = new Function(cos, logSystem);

        csvExporter.export(cos, from, to, step, eps, directory.resolve("cos.csv"));
        csvExporter.export(ln, Math.max(from, 1e-9), to, step, eps, directory.resolve("ln.csv"));
        csvExporter.export(log2, Math.max(from, 1e-9), to, step, eps, directory.resolve("log2.csv"));
        csvExporter.export(log10, Math.max(from, 1e-9), to, step, eps, directory.resolve("log10.csv"));
        csvExporter.export(logSystem, Math.max(from, 1e-9), to, step, eps, directory.resolve("logSystem.csv"));
        csvExporter.export(function, from, to, step, eps, directory.resolve("system.csv"));
    }
}
