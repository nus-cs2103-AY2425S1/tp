package hallpointer.address.storage;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.HOON;
import static hallpointer.address.testutil.TypicalMembers.IDA;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import hallpointer.address.commons.exceptions.DataLoadingException;
import hallpointer.address.model.HallPointer;
import hallpointer.address.model.ReadOnlyHallPointer;

public class JsonHallPointerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHallPointerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readHallPointer_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readHallPointer(null));
    }

    private java.util.Optional<ReadOnlyHallPointer> readHallPointer(String filePath) throws Exception {
        return new JsonHallPointerStorage(Paths.get(filePath)).readHallPointer(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHallPointer("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readHallPointer("notJsonFormatHallPointer.json"));
    }

    @Test
    public void readHallPointer_invalidMemberHallPointer_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHallPointer("invalidMemberHallPointer.json"));
    }

    @Test
    public void readHallPointer_invalidAndValidMemberHallPointer_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readHallPointer("invalidAndValidMemberHallPointer.json"));
    }

    @Test
    public void readAndSaveHallPointer_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempHallPointer.json");
        HallPointer original = getTypicalHallPointer();
        JsonHallPointerStorage jsonHallPointerStorage = new JsonHallPointerStorage(filePath);

        // Save in new file and read back
        jsonHallPointerStorage.saveHallPointer(original, filePath);
        ReadOnlyHallPointer readBack = jsonHallPointerStorage.readHallPointer(filePath).get();
        assertEquals(original, new HallPointer(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMember(HOON);
        original.removeMember(ALICE);
        jsonHallPointerStorage.saveHallPointer(original, filePath);
        readBack = jsonHallPointerStorage.readHallPointer(filePath).get();
        assertEquals(original, new HallPointer(readBack));

        // Save and read without specifying file path
        original.addMember(IDA);
        jsonHallPointerStorage.saveHallPointer(original); // file path not specified
        readBack = jsonHallPointerStorage.readHallPointer().get(); // file path not specified
        assertEquals(original, new HallPointer(readBack));

    }

    @Test
    public void saveHallPointer_nullHallPointer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHallPointer(null, "SomeFile.json"));
    }

    /**
     * Saves {@code hallPointer} at the specified {@code filePath}.
     */
    private void saveHallPointer(ReadOnlyHallPointer hallPointer, String filePath) {
        try {
            new JsonHallPointerStorage(Paths.get(filePath))
                    .saveHallPointer(hallPointer, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHallPointer_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveHallPointer(new HallPointer(), null));
    }
}
