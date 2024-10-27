package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVendors;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_VENDORS_FILE = TEST_DATA_FOLDER.resolve("typicalVendorsAddressBook.json");
    private static final Path INVALID_VENDOR_FILE = TEST_DATA_FOLDER.resolve("invalidVendorAddressBook.json");
    private static final Path DUPLICATE_VENDOR_FILE = TEST_DATA_FOLDER.resolve("duplicateVendorAddressBook.json");
    private static final Path TYPICAL_EVENTS_FILE = TEST_DATA_FOLDER.resolve("typicalEventsAddressBook.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventAddressBook.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventAddressBook.json");
    private static final Path TYPICAL_ADDRESS_BOOK = TEST_DATA_FOLDER.resolve("typicalAddressBook.json");
    private static final Path INVALID_ASSOCIATION_VENDOR_ID_FILE = TEST_DATA_FOLDER.resolve(
            "invalidAssociationVendorId.json");
    private static final Path INVALID_ASSOCIATION_EVENT_ID_FILE = TEST_DATA_FOLDER.resolve(
            "invalidAssociationEventId.json");

    @Test
    public void toModelType_typicalAddressBook_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ADDRESS_BOOK,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        // Verify that the loaded address book contains the expected vendors, events, and associations
        assertEquals(2, addressBookFromFile.getVendorList().size());
        assertEquals(2, addressBookFromFile.getEventList().size());
        assertEquals(2, addressBookFromFile.getAssociationList().size());
    }

    @Test
    public void toModelType_invalidAssociationVendorId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ASSOCIATION_VENDOR_ID_FILE,
                JsonSerializableAddressBook.class).get();

        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_NON_EXISTENT_VENDOR, () -> dataFromFile.toModelType());
    }

    @Test
    public void toModelType_invalidAssociationEventId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ASSOCIATION_EVENT_ID_FILE,
                JsonSerializableAddressBook.class).get();

        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_NON_EXISTENT_EVENT, () -> dataFromFile.toModelType());
    }

    @Test
    public void toModelType_typicalVendorsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_VENDORS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalVendorsAddressBook = TypicalVendors.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalVendorsAddressBook);
    }

    @Test
    public void toModelType_invalidVendorFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_VENDOR_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateVendors_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VENDOR_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_VENDOR,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalEventsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalEventsAddressBook = TypicalEvents.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalEventsAddressBook);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EVENT,
                dataFromFile::toModelType);
    }

}
