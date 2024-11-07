package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.CsvImport.HEADER_COUNT;
import static seedu.address.storage.CsvImport.INCORRECT_HEADERS;
import static seedu.address.storage.CsvImport.INCORRECT_ROWS;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.ImproperFormatException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CsvImportTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvImportTest");
    private final Model model = new ModelManager();

    @Test
    public void readCsv_typicalPersonsCsv_success() throws Exception {
        Path filePath = TEST_DATA_FOLDER.resolve("typicalPersonsCsv.csv");
        CsvImport importer = new CsvImport(filePath.toString());
        assertEquals(1, importer.readCsv(model));
    }

    @Test
    public void readCsv_incorrectHeader_throwsInvalidFormatException() {
        Path missingHeader = TEST_DATA_FOLDER.resolve("missingHeadersCsv.csv");
        CsvImport missingHeaderImporter = new CsvImport(missingHeader.toString());
        assertThrows(ImproperFormatException.class, INCORRECT_HEADERS, () -> missingHeaderImporter.readCsv(model));

        Path extraHeader = TEST_DATA_FOLDER.resolve("extraHeadersCsv.csv");
        CsvImport extraHeaderImporter = new CsvImport(extraHeader.toString());
        assertThrows(ImproperFormatException.class, INCORRECT_HEADERS, () -> extraHeaderImporter.readCsv(model));
    }

    @Test
    public void readCsv_incorrectRows_throwsInvalidFormatException() {
        Path missingRows = TEST_DATA_FOLDER.resolve("missingRowsCsv.csv");
        CsvImport missingRowsImporter = new CsvImport(missingRows.toString());
        assertThrows(ImproperFormatException.class,
                String.format(INCORRECT_ROWS, HEADER_COUNT, "[1]"), () -> missingRowsImporter.readCsv(model));

        Path extraRows = TEST_DATA_FOLDER.resolve("extraRowsCsv.csv");
        CsvImport extraRowsImporter = new CsvImport(extraRows.toString());
        assertThrows(ImproperFormatException.class,
                String.format(INCORRECT_ROWS, HEADER_COUNT, "[1]"), () -> extraRowsImporter.readCsv(model));
    }

    @Test
    public void readCsv_invalidFile_throwsFileNotFoundException() {
        Path invalidFile = TEST_DATA_FOLDER.resolve("invalidFileCsv.csv");
        CsvImport invalidFileImporter = new CsvImport(invalidFile.toString());
        assertThrows(FileNotFoundException.class, () -> invalidFileImporter.readCsv(model));
    }

    @Test
    public void readCsv_emptyHeaders_throwsImproperFormatException() {
        Path emptyHeaders = TEST_DATA_FOLDER.resolve("missingHeadersCsv.csv");
        CsvImport emptyHeadersImporter = new CsvImport(emptyHeaders.toString());
        assertThrows(ImproperFormatException.class, INCORRECT_HEADERS, () -> emptyHeadersImporter.readCsv(model));
    }

    @Test
    public void readCsv_emptyRows_throwsImproperFormatException() {
        Path emptyRows = TEST_DATA_FOLDER.resolve("missingRowsCsv.csv");
        CsvImport emptyRowsImporter = new CsvImport(emptyRows.toString());
        assertThrows(ImproperFormatException.class,
                String.format(INCORRECT_ROWS, HEADER_COUNT, "[1]"), () -> emptyRowsImporter.readCsv(model));
    }
}
