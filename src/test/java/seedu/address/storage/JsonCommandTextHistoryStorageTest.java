package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCommandTextHistory.getTypicalCommandTextHistory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.CommandTextHistory;

public class JsonCommandTextHistoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCommandTextHistoryStorageTest");

    @TempDir
    public Path testFolder;

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private java.util.Optional<CommandTextHistory> readCommandTextHistory(String filePath) throws Exception {
        return new JsonCommandTextHistoryStorage(Paths.get(filePath))
                .readCommandTextHistory(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Saves {@code CommandTextHistory} at the specified {@code filePath}.
     */
    private void saveCommandTextHistory(CommandTextHistory commandTextHistory, String filePath) {
        try {
            new JsonCommandTextHistoryStorage(Paths.get(filePath))
                    .saveCommandTextHistory(commandTextHistory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void readJson_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCommandTextHistory(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCommandTextHistory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readCommandTextHistory("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAndSaveCommandTextHistory_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCommandTextHistory.json");
        CommandTextHistory original = getTypicalCommandTextHistory();
        JsonCommandTextHistoryStorage jsonCommandTextHistoryStorage = new JsonCommandTextHistoryStorage(filePath);

        // Save in new file and read back
        jsonCommandTextHistoryStorage.saveCommandTextHistory(original, filePath);
        CommandTextHistory readBack = jsonCommandTextHistoryStorage.readCommandTextHistory(filePath).get();
        assertEquals(original, new CommandTextHistory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCommandText("command 1");
        original.addCommandText("command 2");
        jsonCommandTextHistoryStorage.saveCommandTextHistory(original, filePath);
        readBack = jsonCommandTextHistoryStorage.readCommandTextHistory(filePath).get();
        assertEquals(original, new CommandTextHistory(readBack));

        // Save and read without specifying file path
        original.addCommandText("command 3");
        jsonCommandTextHistoryStorage.saveCommandTextHistory(original); // file path not specified
        readBack = jsonCommandTextHistoryStorage.readCommandTextHistory().get(); // file path not specified
        assertEquals(original, new CommandTextHistory(readBack));
    }

    @Test
    public void saveCommandTextHistory_nullCommandTextHistory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCommandTextHistory(null, "SomeFile.json"));
    }

    @Test
    public void saveCommandTextHistory_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCommandTextHistory(new CommandTextHistory(), null));
    }
}
