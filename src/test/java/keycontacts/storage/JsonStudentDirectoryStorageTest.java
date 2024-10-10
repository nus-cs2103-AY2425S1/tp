package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.HOON;
import static keycontacts.testutil.TypicalStudents.IDA;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import keycontacts.commons.exceptions.DataLoadingException;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.StudentDirectory;

public class JsonStudentDirectoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonStudentDirectoryStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readStudentDirectory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudentDirectory(null));
    }

    private java.util.Optional<ReadOnlyStudentDirectory> readStudentDirectory(String filePath) throws Exception {
        return new JsonStudentDirectoryStorage(
                Paths.get(filePath)).readStudentDirectory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStudentDirectory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readStudentDirectory("notJsonFormatStudentDirectory.json"));
    }

    @Test
    public void readStudentDirectory_invalidStudentStudentDirectory_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readStudentDirectory("invalidStudentStudentDirectory.json"));
    }

    @Test
    public void readStudentDirectory_invalidAndValidStudentStudentDirectory_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readStudentDirectory(
                "invalidAndValidStudentStudentDirectory.json"));
    }

    @Test
    public void readAndSaveStudentDirectory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempStudentDirectory.json");
        StudentDirectory original = getTypicalStudentDirectory();
        JsonStudentDirectoryStorage jsonStudentDirectoryStorage = new JsonStudentDirectoryStorage(filePath);

        // Save in new file and read back
        jsonStudentDirectoryStorage.saveStudentDirectory(original, filePath);
        ReadOnlyStudentDirectory readBack = jsonStudentDirectoryStorage.readStudentDirectory(filePath).get();
        assertEquals(original, new StudentDirectory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudent(HOON);
        original.removeStudent(ALICE);
        jsonStudentDirectoryStorage.saveStudentDirectory(original, filePath);
        readBack = jsonStudentDirectoryStorage.readStudentDirectory(filePath).get();
        assertEquals(original, new StudentDirectory(readBack));

        // Save and read without specifying file path
        original.addStudent(IDA);
        jsonStudentDirectoryStorage.saveStudentDirectory(original); // file path not specified
        readBack = jsonStudentDirectoryStorage.readStudentDirectory().get(); // file path not specified
        assertEquals(original, new StudentDirectory(readBack));

    }

    @Test
    public void saveStudentDirectory_nullStudentDirectory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentDirectory(null, "SomeFile.json"));
    }

    /**
     * Saves {@code studentDirectory} at the specified {@code filePath}.
     */
    private void saveStudentDirectory(ReadOnlyStudentDirectory studentDirectory, String filePath) {
        try {
            new JsonStudentDirectoryStorage(Paths.get(filePath))
                    .saveStudentDirectory(studentDirectory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStudentDirectory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudentDirectory(new StudentDirectory(), null));
    }
}
