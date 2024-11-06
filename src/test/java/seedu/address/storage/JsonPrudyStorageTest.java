package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.HOON;
import static seedu.address.testutil.TypicalClients.IDA;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Prudy;
import seedu.address.model.ReadOnlyPrudy;

public class JsonPrudyStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPrudyStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPrudy_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPrudy(null));
    }

    private java.util.Optional<ReadOnlyPrudy> readPrudy(String filePath) throws Exception {
        return new JsonPrudyStorage(Paths.get(filePath)).readPrudy(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPrudy("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPrudy("notJsonFormatPrudy.json"));
    }

    @Test
    public void readPrudy_invalidClientPrudy_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPrudy("invalidClientPrudy.json"));
    }

    @Test
    public void readPrudy_invalidAndValidClientPrudy_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPrudy("invalidAndValidClientPrudy.json"));
    }

    @Test
    public void readAndSavePrudy_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPrudy.json");
        Prudy original = getTypicalPrudy();
        JsonPrudyStorage jsonPrudyStorage = new JsonPrudyStorage(filePath);

        // Save in new file and read back
        jsonPrudyStorage.savePrudy(original, filePath);
        ReadOnlyPrudy readBack = jsonPrudyStorage.readPrudy(filePath).get();
        assertEquals(original, new Prudy(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addClient(HOON);
        original.removeClient(ALICE);
        jsonPrudyStorage.savePrudy(original, filePath);
        readBack = jsonPrudyStorage.readPrudy(filePath).get();
        assertEquals(original, new Prudy(readBack));

        // Save and read without specifying file path
        original.addClient(IDA);
        jsonPrudyStorage.savePrudy(original); // file path not specified
        readBack = jsonPrudyStorage.readPrudy().get(); // file path not specified
        assertEquals(original, new Prudy(readBack));

    }

    @Test
    public void savePrudy_nullPrudy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePrudy(null, "SomeFile.json"));
    }

    /**
     * Saves {@code prudy} at the specified {@code filePath}.
     */
    private void savePrudy(ReadOnlyPrudy prudy, String filePath) {
        try {
            new JsonPrudyStorage(Paths.get(filePath))
                    .savePrudy(prudy, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePrudy_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePrudy(new Prudy(), null));
    }
}
