package seedu.hiredfiredpro.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.ALICE;
import static seedu.hiredfiredpro.testutil.TypicalPersons.HOON;
import static seedu.hiredfiredpro.testutil.TypicalPersons.IDA;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.hiredfiredpro.commons.exceptions.DataLoadingException;
import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;

public class JsonHiredFiredProStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHiredFiredProStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHiredFiredPro_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHiredFiredPro(null));
    }

    private java.util.Optional<ReadOnlyHiredFiredPro> readHiredFiredPro(String filePath) throws Exception {
        return new JsonHiredFiredProStorage(Paths.get(filePath))
                .readHiredFiredPro(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHiredFiredPro("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readHiredFiredPro("notJsonFormatHiredFiredPro.json"));
    }

    @Test
    public void readHiredFiredPro_invalidPersonHiredFiredPro_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHiredFiredPro("invalidPersonHiredFiredPro.json"));
    }

    @Test
    public void readHiredFiredPro_invalidAndValidPersonHiredFiredPro_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHiredFiredPro("invalidAndValidPersonHiredFiredPro.json"));
    }

    @Test
    public void readAndSaveHiredFiredPro_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHiredFiredPro.json");
        HiredFiredPro original = getTypicalHiredFiredPro();
        JsonHiredFiredProStorage jsonHiredFiredProStorage = new JsonHiredFiredProStorage(filePath);

        // Save in new file and read back
        jsonHiredFiredProStorage.saveHiredFiredPro(original, filePath);
        ReadOnlyHiredFiredPro readBack = jsonHiredFiredProStorage.readHiredFiredPro(filePath).get();
        assertEquals(original, new HiredFiredPro(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonHiredFiredProStorage.saveHiredFiredPro(original, filePath);
        readBack = jsonHiredFiredProStorage.readHiredFiredPro(filePath).get();
        assertEquals(original, new HiredFiredPro(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonHiredFiredProStorage.saveHiredFiredPro(original); // file path not specified
        readBack = jsonHiredFiredProStorage.readHiredFiredPro().get(); // file path not specified
        assertEquals(original, new HiredFiredPro(readBack));

    }

    @Test
    public void saveHiredFiredPro_nullHiredFiredPro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHiredFiredPro(null, "SomeFile.json"));
    }

    /**
     * Saves {@code hiredFiredPro} at the specified {@code filePath}.
     */
    private void saveHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro, String filePath) {
        try {
            new JsonHiredFiredProStorage(Paths.get(filePath))
                    .saveHiredFiredPro(hiredFiredPro, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHiredFiredPro_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHiredFiredPro(new HiredFiredPro(), null));
    }
}
