package seedu.academyassist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.academyassist.testutil.Assert.assertThrows;
import static seedu.academyassist.testutil.TypicalPersons.ALICE;
import static seedu.academyassist.testutil.TypicalPersons.HOON;
import static seedu.academyassist.testutil.TypicalPersons.IDA;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.academyassist.commons.exceptions.DataLoadingException;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.ReadOnlyAcademyAssist;

public class JsonAcademyAssistStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonAcademyAssistStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAcademyAssist_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAcademyAssist(null));
    }

    private java.util.Optional<ReadOnlyAcademyAssist> readAcademyAssist(String filePath) throws Exception {
        return new JsonAcademyAssistStorage(
                Paths.get(filePath)).readAcademyAssist(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAcademyAssist("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAcademyAssist("notJsonFormatAcademyAssist.json"));
    }

    @Test
    public void readAcademyAssist_invalidPersonAcademyAssist_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAcademyAssist("invalidPersonAcademyAssist.json"));
    }

    @Test
    public void readAcademyAssist_invalidAndValidPersonAcademyAssist_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAcademyAssist("invalidAndValidPersonAcademyAssist.json"));
    }

    @Test
    public void readAndSaveAcademyAssist_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAcademyAssist.json");
        AcademyAssist original = getTypicalAcademyAssist();
        JsonAcademyAssistStorage jsonAcademyAssistStorage = new JsonAcademyAssistStorage(filePath);

        // Save in new file and read back
        jsonAcademyAssistStorage.saveAcademyAssist(original, filePath);
        ReadOnlyAcademyAssist readBack = jsonAcademyAssistStorage.readAcademyAssist(filePath).get();
        assertEquals(original, new AcademyAssist(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAcademyAssistStorage.saveAcademyAssist(original, filePath);
        readBack = jsonAcademyAssistStorage.readAcademyAssist(filePath).get();
        assertEquals(original, new AcademyAssist(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAcademyAssistStorage.saveAcademyAssist(original); // file path not specified
        readBack = jsonAcademyAssistStorage.readAcademyAssist().get(); // file path not specified
        assertEquals(original, new AcademyAssist(readBack));

    }

    @Test
    public void saveAcademyAssist_nullAcademyAssist_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAcademyAssist(null, "SomeFile.json"));
    }

    /**
     * Saves {@code academyAssist} at the specified {@code filePath}.
     */
    private void saveAcademyAssist(ReadOnlyAcademyAssist academyAssist, String filePath) {
        try {
            new JsonAcademyAssistStorage(Paths.get(filePath))
                    .saveAcademyAssist(academyAssist, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAcademyAssist_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAcademyAssist(new AcademyAssist(), null));
    }
}
