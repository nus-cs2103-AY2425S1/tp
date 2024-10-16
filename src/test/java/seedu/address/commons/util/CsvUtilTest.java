package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
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


    /**
     * A stub class to test the read and write of csv files
     */
    public static class StubClass {
        private String a;
        private String b;
        private String c;

        public StubClass() {

        }

        public StubClass(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            StubClass other = (StubClass) obj;
            return Objects.equals(a, other.a) && Objects.equals(b, other.b) && Objects.equals(c, other.c);
        }
    }

    @Test
    public void readCsvFile_noFile_emptyResult() throws DataLoadingException {
        Optional<List<String>> result = CsvUtil.readCsvFile(filePath, String.class);
        assertTrue(result.isEmpty());
    }

    @Test
    public void readCsvFile_existingFile_correctData() throws DataLoadingException, IOException {
        Files.writeString(filePath, "a,b,c\n1,2,3\n4,5,6\n");
        Optional<List<StubClass>> result = CsvUtil.readCsvFile(filePath, StubClass.class);
        assertTrue(result.isPresent());
        List<StubClass> data = result.get();
        assertEquals(2, data.size());

        List<StubClass> predictedOutput = List.of(
                new StubClass("1", "2", "3"),
                new StubClass("4", "5", "6")
        );
        assertEquals(predictedOutput, data);
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
        List<StubClass> data = List.of(new StubClass("1", "2", "3"), new StubClass("4", "5", "6"));
        CsvUtil.writeCsvFile(filePath, data);
        String predictedOutput = """
                "A","B","C"
                "1","2","3"
                "4","5","6"
                """;
        assertEquals(predictedOutput, Files.readString(filePath));
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
