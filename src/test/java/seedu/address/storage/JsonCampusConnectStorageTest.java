package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.CampusConnect;
import seedu.address.model.ReadOnlyCampusConnect;

public class JsonCampusConnectStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCampusConnectStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCampusConnect_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCampusConnect(null));
    }

    private java.util.Optional<ReadOnlyCampusConnect> readCampusConnect(String filePath) throws Exception {
        return new JsonCampusConnectStorage(Paths.get(filePath))
                .readCampusConnect(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCampusConnect("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCampusConnect("notJsonFormatCampusConnect.json"));
    }

    @Test
    public void readCampusConnect_invalidPersonCampusConnect_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCampusConnect("invalidPersonCampusConnect.json"));
    }

    @Test
    public void readCampusConnect_invalidAndValidPersonCampusConnect_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readCampusConnect("invalidAndValidPersonCampusConnect.json"));
    }

    @Test
    public void readAndSaveCampusConnect_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCampusConnect.json");
        CampusConnect original = getTypicalCampusConnect();
        JsonCampusConnectStorage jsonCampusConnectStorage = new JsonCampusConnectStorage(filePath);

        // Save in new file and read back
        jsonCampusConnectStorage.saveCampusConnect(original, filePath);
        ReadOnlyCampusConnect readBack = jsonCampusConnectStorage.readCampusConnect(filePath).get();
        assertEquals(original, new CampusConnect(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonCampusConnectStorage.saveCampusConnect(original, filePath);
        readBack = jsonCampusConnectStorage.readCampusConnect(filePath).get();
        assertEquals(original, new CampusConnect(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonCampusConnectStorage.saveCampusConnect(original); // file path not specified
        readBack = jsonCampusConnectStorage.readCampusConnect().get(); // file path not specified
        assertEquals(original, new CampusConnect(readBack));

    }

    @Test
    public void saveCampusConnect_nullCampusConnect_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCampusConnect(null, "SomeFile.json"));
    }

    /**
     * Saves {@code campusConnect} at the specified {@code filePath}.
     */
    private void saveCampusConnect(ReadOnlyCampusConnect campusConnect, String filePath) {
        try {
            new JsonCampusConnectStorage(Paths.get(filePath))
                    .saveCampusConnect(campusConnect, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCampusConnect_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCampusConnect(new CampusConnect(), null));
    }
}
