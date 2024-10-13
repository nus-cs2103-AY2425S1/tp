package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ClientHub;
import seedu.address.model.ReadOnlyClientHub;

public class JsonClientHubStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonClientHubStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readClientHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readClientHub(null));
    }

    private java.util.Optional<ReadOnlyClientHub> readClientHub(String filePath) throws Exception {
        return new JsonClientHubStorage(Paths.get(filePath)).readClientHub(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readClientHub("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readClientHub("notJsonFormatClientHub.json"));
    }

    @Test
    public void readClientHub_invalidPersonClientHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClientHub("invalidPersonClientHub.json"));
    }

    @Test
    public void readClientHub_invalidAndValidPersonClientHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readClientHub("invalidAndValidPersonClientHub.json"));
    }

    @Test
    public void readAndSaveClientHub_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempClientHub.json");
        ClientHub original = getTypicalClientHub();
        JsonClientHubStorage jsonClientHubStorage = new JsonClientHubStorage(filePath);

        // Save in new file and read back
        jsonClientHubStorage.saveClientHub(original, filePath);
        ReadOnlyClientHub readBack = jsonClientHubStorage.readClientHub(filePath).get();
        assertEquals(original, new ClientHub(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonClientHubStorage.saveClientHub(original, filePath);
        readBack = jsonClientHubStorage.readClientHub(filePath).get();
        assertEquals(original, new ClientHub(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonClientHubStorage.saveClientHub(original); // file path not specified
        readBack = jsonClientHubStorage.readClientHub().get(); // file path not specified
        assertEquals(original, new ClientHub(readBack));

    }

    @Test
    public void saveClientHub_nullClientHub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClientHub(null, "SomeFile.json"));
    }

    /**
     * Saves {@code clientHub} at the specified {@code filePath}.
     */
    private void saveClientHub(ReadOnlyClientHub clientHub, String filePath) {
        try {
            new JsonClientHubStorage(Paths.get(filePath))
                    .saveClientHub(clientHub, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveClientHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveClientHub(new ClientHub(), null));
    }
}
