package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_FIVE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_FOUR;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.WeddingBook;

public class JsonWeddingBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonWeddingBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWeddingBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWeddingBook(null));
    }

    private java.util.Optional<ReadOnlyWeddingBook> readWeddingBook(String filePath) throws Exception {
        return new JsonWeddingBookStorage(Paths.get(filePath)).readWeddingBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWeddingBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readWeddingBook("notJsonFormatWeddingBook.json"));
    }

    @Test
    public void readWeddingBook_invalidWeddingBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readWeddingBook("invalidWeddingBook.json"));
    }

    @Test
    public void readWeddingBook_invalidAndValidWeddingBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readWeddingBook("invalidAndValidWeddingBook.json"));
    }

    @Test
    public void readAndSaveWeddingBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWeddingBook.json");
        WeddingBook original = getTypicalWeddingBook();
        JsonWeddingBookStorage jsonWeddingBookStorage = new JsonWeddingBookStorage(filePath);

        // Save in new file and read back
        jsonWeddingBookStorage.saveWeddingBook(original, filePath);
        ReadOnlyWeddingBook readBack = jsonWeddingBookStorage.readWeddingBook(filePath).get();
        assertEquals(original, new WeddingBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addWedding(WEDDING_FOUR);
        original.removeWedding(WEDDING_ONE);
        jsonWeddingBookStorage.saveWeddingBook(original, filePath);
        readBack = jsonWeddingBookStorage.readWeddingBook(filePath).get();
        assertEquals(original, new WeddingBook(readBack));

        // Save and read without specifying file path
        original.addWedding(WEDDING_FIVE);
        jsonWeddingBookStorage.saveWeddingBook(original); // file path not specified
        readBack = jsonWeddingBookStorage.readWeddingBook().get(); // file path not specified
        assertEquals(original, new WeddingBook(readBack));

    }

    @Test
    public void saveWeddingBook_nullWeddingBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWeddingBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveWeddingBook(ReadOnlyWeddingBook weddingBook, String filePath) {
        try {
            new JsonWeddingBookStorage(Paths.get(filePath))
                    .saveWeddingBook(weddingBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWeddingBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWeddingBook(new WeddingBook(), null));
    }
}
