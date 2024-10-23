package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalPawPatrol;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PawPatrol;
import seedu.address.model.ReadOnlyPawPatrol;

public class JsonPawPatrolStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPawPatrolStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPawPatrol_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPawPatrol(null));
    }

    private java.util.Optional<ReadOnlyPawPatrol> readPawPatrol(String filePath) throws Exception {
        return new JsonPawPatrolStorage(Paths.get(filePath)).readPawPatrol(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPawPatrol("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPawPatrol("notJsonFormatPawPatrol.json"));
    }

    @Test
    public void readPawPatrol_invalidPersonPawPatrol_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPawPatrol("invalidPersonPawPatrol.json"));
    }

    @Test
    public void readPawPatrol_invalidAndValidPersonPawPatrol_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPawPatrol("invalidAndValidPersonPawPatrol.json"));
    }

    @Test
    public void readAndSavePawPatrol_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPawPatrol.json");
        PawPatrol original = getTypicalPawPatrol();
        JsonPawPatrolStorage jsonPawPatrolStorage = new JsonPawPatrolStorage(filePath);

        // Save in new file and read back
        jsonPawPatrolStorage.savePawPatrol(original, filePath);
        ReadOnlyPawPatrol readBack = jsonPawPatrolStorage.readPawPatrol(filePath).get();
        assertEquals(original, new PawPatrol(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonPawPatrolStorage.savePawPatrol(original, filePath);
        readBack = jsonPawPatrolStorage.readPawPatrol(filePath).get();
        assertEquals(original, new PawPatrol(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonPawPatrolStorage.savePawPatrol(original); // file path not specified
        readBack = jsonPawPatrolStorage.readPawPatrol().get(); // file path not specified
        assertEquals(original, new PawPatrol(readBack));

    }

    @Test
    public void savePawPatrol_nullPawPatrol_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePawPatrol(null, "SomeFile.json"));
    }

    /**
     * Saves {@code pawPatrol} at the specified {@code filePath}.
     */
    private void savePawPatrol(ReadOnlyPawPatrol pawPatrol, String filePath) {
        try {
            new JsonPawPatrolStorage(Paths.get(filePath))
                    .savePawPatrol(pawPatrol, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePawPatrol_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePawPatrol(new PawPatrol(), null));
    }
}
