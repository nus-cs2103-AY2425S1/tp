package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Listings;
import seedu.address.testutil.TypicalListings;


public class JsonSerializableListingsTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableListingsTest");
    private static final Path TYPICAL_LISTINGS_FILE = TEST_DATA_FOLDER.resolve("typicalListings.json");
    private static final Path INVALID_LISTINGS_FILE = TEST_DATA_FOLDER.resolve("invalidListings.json");
    private static final Path DUPLICATE_LISTINGS_FILE = TEST_DATA_FOLDER.resolve("duplicateListings.json");

    @Test
    public void toModelType_typicalListingsFile_success() throws Exception {
        JsonSerializableListings dataFromFile = JsonUtil.readJsonFile(TYPICAL_LISTINGS_FILE,
                JsonSerializableListings.class).get();
        Listings listingsFromFile = dataFromFile.toModelType();
        Listings typicalListings = TypicalListings.getTypicalListings();
        assertEquals(listingsFromFile, typicalListings);
    }


    @Test
    public void toModelType_invalidListingFile_throwsIllegalValueException() throws Exception {
        JsonSerializableListings dataFromFile = JsonUtil.readJsonFile(INVALID_LISTINGS_FILE,
                JsonSerializableListings.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateListings_throwsIllegalValueException() throws Exception {
        // Load the test data from the JSON file that contains duplicate listings
        JsonSerializableListings dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LISTINGS_FILE,
                JsonSerializableListings.class).get();

        // Ensure the test correctly throws IllegalValueException due to duplicate listings in the data
        assertThrows(IllegalValueException.class, JsonSerializableListings.MESSAGE_DUPLICATE_LISTING,
                dataFromFile::toModelType);
    }
}
