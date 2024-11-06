package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.HOUGANG;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.SIMEI;
import static seedu.address.testutil.TypicalListings.getTypicalListings;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Listings;
import seedu.address.model.ReadOnlyListings;

public class JsonListingsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonListingsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readListings_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readListings(null));
    }

    private java.util.Optional<ReadOnlyListings> readListings(String filePath) throws Exception {
        return new JsonListingsStorage(Paths.get(filePath)).readListings(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readListings("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readListings("notJsonFormatListings.json"));
    }

    @Test
    public void readListings_invalidListings_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readListings("invalidListings.json"));
    }

    @Test
    public void readListings_invalidAndValidListings_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readListings("invalidAndValidListings.json"));
    }

    @Test
    public void readAndSaveListings_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempListings.json");
        Listings original = getTypicalListings();
        JsonListingsStorage jsonListingsStorage = new JsonListingsStorage(filePath);

        // Save in new file and read back
        jsonListingsStorage.saveListings(original, filePath);
        ReadOnlyListings readBack = jsonListingsStorage.readListings(filePath).get();
        assertEquals(original, new Listings(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addListing(SIMEI);
        original.removeListing(PASIR_RIS);
        jsonListingsStorage.saveListings(original, filePath);
        readBack = jsonListingsStorage.readListings(filePath).get();
        assertEquals(original, new Listings(readBack));

        // Save and read without specifying file path
        original.addListing(HOUGANG);
        jsonListingsStorage.saveListings(original); // file path not specified
        readBack = jsonListingsStorage.readListings().get(); // file path not specified
        assertEquals(original, new Listings(readBack));

    }

    @Test
    public void saveListings_nullListings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveListings(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveListings(ReadOnlyListings listings, String filePath) {
        try {
            new JsonListingsStorage(Paths.get(filePath))
                    .saveListings(listings, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveListings_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveListings(new Listings(), null));
    }

}
