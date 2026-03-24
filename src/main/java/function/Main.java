package function;

import function.util.CsvExporter;
import function.util.FunctionExporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final double DEFAULT_FROM = -5.0;
    private static final double DEFAULT_TO = 5.0;
    private static final double DEFAULT_STEP = 0.1;
    private static final double DEFAULT_EPS = 1e-4;

    public static void main(String[] args) {
        Path outputDir = Paths.get("plots");
        FunctionExporter exporter = new FunctionExporter(new CsvExporter());
        try {
            exporter.exportAll(outputDir, DEFAULT_FROM, DEFAULT_TO, DEFAULT_STEP, DEFAULT_EPS);
            System.out.println("CSV  in " + outputDir.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to export functions: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
