package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalRestaurants;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_RESTAURANTS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalRestaurantsAddressBook.json");
    private static final Path INVALID_RESTAURANT_FILE = TEST_DATA_FOLDER.resolve(
            "invalidRestaurantAddressBook.json");
    private static final Path DUPLICATE_RESTAURANT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateRestaurantAddressBook.json");

    @Test
    public void toModelType_typicalRestaurantsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESTAURANTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalRestaurantsAddressBook = TypicalRestaurants.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalRestaurantsAddressBook);
    }

    @Test
    public void toModelType_invalidRestaurantFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_RESTAURANT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateRestaurants_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RESTAURANT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_RESTAURANT,
                dataFromFile::toModelType);
    }

}
