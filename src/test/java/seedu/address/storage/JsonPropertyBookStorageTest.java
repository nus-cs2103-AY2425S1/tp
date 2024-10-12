package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyPropertyBook;

public class JsonPropertyBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPropertyBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPropertyBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPropertyBook(null));
    }

    private java.util.Optional<ReadOnlyPropertyBook> readPropertyBook(String filePath) throws Exception {
        return new JsonPropertyBookStorage(Paths.get(filePath)).readPropertyBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPropertyBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPropertyBook("notJsonFormatPropertyBook.json"));
    }

    /**
     * Saves {@code propertyBook} at the specified {@code filePath}.
     */
    private void savePropertyBook(ReadOnlyPropertyBook addressBook, String filePath) {
        try {
            new JsonPropertyBookStorage(Paths.get(filePath))
                    .savePropertyBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePropertyBook(new PropertyBook(), null));
    }
}
