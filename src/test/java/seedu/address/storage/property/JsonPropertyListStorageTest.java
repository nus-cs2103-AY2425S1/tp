package seedu.address.storage.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.PropertyList;
import seedu.address.model.ReadOnlyPropertyList;

public class JsonPropertyListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPropertyListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPropertyList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPropertyList(null));
    }

    private java.util.Optional<ReadOnlyPropertyList> readPropertyList(String filePath) throws Exception {
        return new JsonPropertyListStorage(Paths.get(filePath)).readPropertyList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPropertyList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPropertyList("notJsonFormatPropertyList.json"));
    }

    @Test
    public void readBuyerList_invalidPropertyList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPropertyList("invalidPropertyList.json"));
    }

    @Test
    public void readBuyerList_invalidAndValidPropertyList_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPropertyList("invalidAndValidPropertyList.json"));
    }

    @Test
    public void readAndSavePropertyList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPropertyList.json");
        PropertyList original = getTypicalPropertyList();
        JsonPropertyListStorage jsonPropertyListStorage = new JsonPropertyListStorage(filePath);

        // Save in new file and read back
        jsonPropertyListStorage.savePropertyList(original, filePath);
        ReadOnlyPropertyList readBack = jsonPropertyListStorage.readPropertyList(filePath).get();
        assertEquals(original, new PropertyList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addProperty(NETWORKING_MEETUP);
        original.removeProperty(THIRD_MEETUP);
        jsonPropertyListStorage.savePropertyList(original, filePath);
        readBack = jsonPropertyListStorage.readPropertyList(filePath).get();
        assertEquals(original, new PropertyList(readBack));

        // Save and read without specifying file path
        original.addProperty(PITCH_MEETUP);
        jsonPropertyListStorage.savePropertyList(original); // file path not specified
        readBack = jsonPropertyListStorage.readPropertyList().get(); // file path not specified
        assertEquals(original, new PropertyList(readBack));

    }

    @Test
    public void savePropertyList_nullPropertyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePropertyList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code buyerList} at the specified {@code filePath}.
     */
    private void savePropertyList(ReadOnlyPropertyList propertyList, String filePath) {
        try {
            new JsonPropertyListStorage(Paths.get(filePath))
                    .savePropertyList(propertyList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePropertyList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePropertyList(new PropertyList(), null));
    }
}
