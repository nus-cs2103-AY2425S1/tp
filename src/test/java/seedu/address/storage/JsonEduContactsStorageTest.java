package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalEduContacts;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.EduContacts;
import seedu.address.model.ReadOnlyEduContacts;

public class JsonEduContactsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonEduContactsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readEduContacts_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readEduContacts(null));
    }

    private java.util.Optional<ReadOnlyEduContacts> readEduContacts(String filePath) throws Exception {
        return new JsonEduContactsStorage(Paths.get(filePath)).readEduContacts(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEduContacts("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readEduContacts("notJsonFormatEduContacts.json"));
    }

    @Test
    public void readEduContacts_invalidPersonEduContacts_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduContacts("invalidPersonEduContacts.json"));
    }

    @Test
    public void readEduContacts_invalidAndValidPersonEduContacts_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readEduContacts("invalidAndValidPersonEduContacts.json"));
    }

    @Test
    public void readAndSaveEduContacts_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempEduContacts.json");
        EduContacts original = getTypicalEduContacts();
        JsonEduContactsStorage jsonEduContactsStorage = new JsonEduContactsStorage(filePath);

        // Save in new file and read back
        jsonEduContactsStorage.saveEduContacts(original, filePath);
        ReadOnlyEduContacts readBack = jsonEduContactsStorage.readEduContacts(filePath).get();
        assertEquals(original, new EduContacts(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonEduContactsStorage.saveEduContacts(original, filePath);
        readBack = jsonEduContactsStorage.readEduContacts(filePath).get();
        assertEquals(original, new EduContacts(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonEduContactsStorage.saveEduContacts(original); // file path not specified
        readBack = jsonEduContactsStorage.readEduContacts().get(); // file path not specified
        assertEquals(original, new EduContacts(readBack));

    }

    @Test
    public void saveEduContacts_nullEduContacts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduContacts(null, "SomeFile.json"));
    }

    /**
     * Saves {@code eduContacts} at the specified {@code filePath}.
     */
    private void saveEduContacts(ReadOnlyEduContacts eduContacts, String filePath) {
        try {
            new JsonEduContactsStorage(Paths.get(filePath))
                    .saveEduContacts(eduContacts, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEduContacts_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveEduContacts(new EduContacts(), null));
    }
}
