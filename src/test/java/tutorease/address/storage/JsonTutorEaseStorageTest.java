package tutorease.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalPersons.ALICE;
import static tutorease.address.testutil.TypicalPersons.HOON;
import static tutorease.address.testutil.TypicalPersons.IDA;
import static tutorease.address.testutil.TypicalPersons.getTypicalTutorEase;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tutorease.address.commons.exceptions.DataLoadingException;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.TutorEase;

public class JsonTutorEaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTutorEaseStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTutorEase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTutorEase(null));
    }

    private java.util.Optional<ReadOnlyTutorEase> readTutorEase(String filePath) throws Exception {
        return new JsonTutorEaseStorage(Paths.get(filePath)).readTutorEase(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTutorEase("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTutorEase("notJsonFormatTutorEase.json"));
    }

    @Test
    public void readTutorEase_invalidPersonTutorEase_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTutorEase("invalidPersonTutorEase.json"));
    }

    @Test
    public void readTutorEase_invalidAndValidPersonTutorEase_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTutorEase("invalidAndValidPersonTutorEase.json"));
    }

    @Test
    public void readAndSaveTutorEase_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTutorEase.json");
        TutorEase original = getTypicalTutorEase();
        JsonTutorEaseStorage jsonTutorEaseStorage = new JsonTutorEaseStorage(filePath);

        // Save in new file and read back
        jsonTutorEaseStorage.saveTutorEase(original, filePath);
        ReadOnlyTutorEase readBack = jsonTutorEaseStorage.readTutorEase(filePath).get();
        assertEquals(original, new TutorEase(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonTutorEaseStorage.saveTutorEase(original, filePath);
        readBack = jsonTutorEaseStorage.readTutorEase(filePath).get();
        assertEquals(original, new TutorEase(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonTutorEaseStorage.saveTutorEase(original); // file path not specified
        readBack = jsonTutorEaseStorage.readTutorEase().get(); // file path not specified
        assertEquals(original, new TutorEase(readBack));

    }

    @Test
    public void saveTutorEase_nullTutorEase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorEase(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveTutorEase(ReadOnlyTutorEase addressBook, String filePath) {
        try {
            new JsonTutorEaseStorage(Paths.get(filePath))
                    .saveTutorEase(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTutorEase_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTutorEase(new TutorEase(), null));
    }
}
