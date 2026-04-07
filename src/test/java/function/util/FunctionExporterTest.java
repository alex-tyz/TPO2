package function.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldExportAllFunctions() throws IOException {
        FunctionExporter exporter = new FunctionExporter(new CsvExporter());
        exporter.exportAll(tempDir, -1, 2, 1.0, 1e-3);

        assertAll(
                () -> assertTrue(Files.exists(tempDir.resolve("cos.csv"))),
                () -> assertTrue(Files.exists(tempDir.resolve("ln.csv"))),
                () -> assertTrue(Files.exists(tempDir.resolve("log2.csv"))),
                () -> assertTrue(Files.exists(tempDir.resolve("log10.csv"))),
                () -> assertTrue(Files.exists(tempDir.resolve("logSystem.csv"))),
                () -> assertTrue(Files.exists(tempDir.resolve("system.csv")))
        );
    }

    @Test
    void shouldFailWhenRangeIsInvalid() {
        FunctionExporter exporter = new FunctionExporter(new CsvExporter());
        assertThrows(IllegalArgumentException.class,
                () -> exporter.exportAll(tempDir, 2, 1, 1.0, 1e-3));
    }
}
