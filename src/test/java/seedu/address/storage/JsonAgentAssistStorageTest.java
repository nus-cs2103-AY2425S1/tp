package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAgentAssist;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AgentAssist;
import seedu.address.model.ReadOnlyAgentAssist;

public class JsonAgentAssistStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAgentAssistStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAgentAssist_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAgentAssist(null));
    }

    private java.util.Optional<ReadOnlyAgentAssist> readAgentAssist(String filePath) throws Exception {
        return new JsonAgentAssistStorage(Paths.get(filePath)).readAgentAssist(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAgentAssist("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAgentAssist("notJsonFormatAgentAssist.json"));
    }

    @Test
    public void readAgentAssist_invalidPersonAgentAssist_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAgentAssist("invalidPersonAgentAssist.json"));
    }

    @Test
    public void readAgentAssist_invalidAndValidPersonAgentAssist_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAgentAssist("invalidAndValidPersonAgentAssist.json"));
    }

    @Test
    public void readAndSaveAgentAssist_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAgentAssist.json");
        AgentAssist original = getTypicalAgentAssist();
        JsonAgentAssistStorage jsonAgentAssistStorage = new JsonAgentAssistStorage(filePath);

        // Save in new file and read back
        jsonAgentAssistStorage.saveAgentAssist(original, filePath);
        ReadOnlyAgentAssist readBack = jsonAgentAssistStorage.readAgentAssist(filePath).get();
        assertEquals(original, new AgentAssist(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAgentAssistStorage.saveAgentAssist(original, filePath);
        readBack = jsonAgentAssistStorage.readAgentAssist(filePath).get();
        assertEquals(original, new AgentAssist(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAgentAssistStorage.saveAgentAssist(original); // file path not specified
        readBack = jsonAgentAssistStorage.readAgentAssist().get(); // file path not specified
        assertEquals(original, new AgentAssist(readBack));

    }

    @Test
    public void saveAgentAssist_nullAgentAssist_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAgentAssist(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAgentAssist(ReadOnlyAgentAssist addressBook, String filePath) {
        try {
            new JsonAgentAssistStorage(Paths.get(filePath))
                    .saveAgentAssist(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAgentAssist_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAgentAssist(new AgentAssist(), null));
    }
}
