package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;


/**
 * Tests Csv Read and Write
 */
public class CsvUtilTest {

    @TempDir
    public Path testFolder;

    private Path filePath;

    @BeforeEach
    public void setUp() {
        filePath = testFolder.resolve("test.csv");
    }

    @Test
    public void readCsvFile_noFile_emptyResult() throws DataLoadingException {
        Optional<List<String>> result = CsvUtil.readCsvFile(filePath, String.class);
        assertTrue(result.isEmpty());
    }

    @Test
    public void readCsvFile_existingFile_correctData() throws DataLoadingException, IOException {
        // TODO implement this test
    }

    @Test
    public void readCsvFile_invalidFile_throwException() throws IOException {
        Files.writeString(filePath, "a,b,c\n1,2\n4,5,6\n");
        assertThrows(DataLoadingException.class, () -> CsvUtil.readCsvFile(filePath, String.class));
    }

    @Test
    public void readCsvFile_missingData_exceptionThrown() throws IOException {
        Files.writeString(filePath, "a,b,c\n1,2,3\n4,5\n");
        assertThrows(DataLoadingException.class, () -> CsvUtil.readCsvFile(filePath, String.class));
    }

    @Test
    public void readCsvFile_missingField_exceptionThrown() throws IOException {
        Files.writeString(filePath, "a,b,c\n1,2,3\n4,5,6,7\n");
        assertThrows(DataLoadingException.class, () -> CsvUtil.readCsvFile(filePath, String.class));
    }

    @Test
    public void writeCsvFile_validData_correctData() throws IOException {
        // TODO implement this test
    }

    @Test
    public void writeCsvFile_emptyList_emptyFile() throws IOException {
        CsvUtil.writeCsvFile(filePath, List.of());
        assertTrue(Files.readString(filePath).isEmpty());
    }

    @Test
    public void writeCsvFile_nullList_throwException() {
        assertThrows(NullPointerException.class, () -> CsvUtil.writeCsvFile(filePath, null));
    }

    @Test
    public void writeCsvFile_nullFilePath_throwException() {
        assertThrows(NullPointerException.class, () -> CsvUtil.writeCsvFile(null, List.of()));
    }
}
