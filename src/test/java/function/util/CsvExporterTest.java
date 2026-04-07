package function.util;

import function.AbstractFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CsvExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldWriteValues() throws IOException {
        AbstractFunction function = mock(AbstractFunction.class);
        when(function.calculate(eq(0.0), anyDouble())).thenReturn(0.0);
        when(function.calculate(eq(1.0), anyDouble())).thenReturn(1.0);

        Path file = tempDir.resolve("test.csv");
        CsvExporter exporter = new CsvExporter();
        exporter.export(function, 0, 1, 1, 1e-4, file);

        List<String> lines = Files.readAllLines(file);
        assertEquals("x,y", lines.get(0));
        assertEquals("0,0", lines.get(1));
        assertEquals("1,1", lines.get(2));
    }

    @Test
    void shouldValidateStep() {
        CsvExporter exporter = new CsvExporter();
        assertThrows(IllegalArgumentException.class,
                () -> exporter.export(mock(AbstractFunction.class), 0, 1, 0, 1e-4, tempDir.resolve("invalid.csv")));
    }

    @Test
    void shouldHandleSinglePointRange() throws IOException {
        AbstractFunction function = mock(AbstractFunction.class);
        when(function.calculate(eq(2.0), anyDouble())).thenReturn(5.0);

        Path file = tempDir.resolve("single.csv");
        CsvExporter exporter = new CsvExporter();
        exporter.export(function, 2.0, 2.0, 0.5, 1e-4, file);

        List<String> lines = Files.readAllLines(file);
        assertEquals(2, lines.size());
        assertEquals("2,5", lines.get(1));
    }

    @Test
    void shouldFailWhenPathIsInvalid() throws IOException {
        Path blocked = tempDir.resolve("blocked");
        Files.createFile(blocked);

        CsvExporter exporter = new CsvExporter();
        assertThrows(IOException.class,
                () -> exporter.export(mock(AbstractFunction.class), 0, 1, 1, 1e-4,
                        blocked.resolve("nested").resolve("test.csv")));
    }
}
