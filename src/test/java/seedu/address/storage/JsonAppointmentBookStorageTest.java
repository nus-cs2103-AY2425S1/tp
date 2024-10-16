package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;

public class JsonAppointmentBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAppointmentBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAppointmentBook(null));
    }

    private Optional<ReadOnlyAppointmentBook> readAppointmentBook(String filePath) throws Exception {
        return new JsonAppointmentBookStorage(Paths.get(filePath))
                .readAppointmentBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null
                ? TEST_DATA_FOLDER.resolve(filePath)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAppointmentBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("notJsonFormatAppointmentBook.json"));
    }

    @Test
    public void readAppointmentBook_invalidAppointmentBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("invalidAppointmentBook.json"));
    }

    @Test
    public void readAppointmentBook_invalidAndValidAppointmentBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("invalidAndValidAppointmentBook.json"));
    }

    @Test
    public void saveAppointmentBook_nullAppointmentBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code appointmentBook} at the specified {@code filePath}.
     */
    private void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, String filePath) {
        try {
            new JsonAppointmentBookStorage(Paths.get(filePath))
                    .saveAppointmentBook(appointmentBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(new AppointmentBook(), null));
    }
}
