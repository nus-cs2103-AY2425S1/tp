package seedu.ddd.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ddd.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.commons.util.JsonUtil;
//import seedu.ddd.model.AddressBook;
import seedu.ddd.model.event.exceptions.DuplicateEventException;
//import seedu.ddd.testutil.TypicalAddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve("typicalAddressBook.json");
    private static final Path NON_EXISTENT_CLIENT_FILE = TEST_DATA_FOLDER.resolve("uncreatedContactAddressBook.json");
    private static final Path INVALID_CLIENT_FILE = TEST_DATA_FOLDER.resolve("invalidClientAddressBook.json");
    private static final Path INVALID_VENDOR_FILE = TEST_DATA_FOLDER.resolve("invalidVendorAddressBook.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventAddressBook.json");
    private static final Path DUPLICATE_CLIENTS_FILE = TEST_DATA_FOLDER.resolve("duplicateClientsAddressBook.json");
    private static final Path DUPLICATE_VENDORS_FILE = TEST_DATA_FOLDER.resolve("duplicateVendorsAddressBook.json");
    private static final Path DUPLICATE_EVENTS_FILE = TEST_DATA_FOLDER.resolve("duplicateEventsAddressBook.json");
    private static final Path MISMATCH_ASSOCIATION_FILE = TEST_DATA_FOLDER.resolve("mismatchAddressBook.json");

    /*
    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalContactsAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalContactsAddressBook);
    }
    */

    @Test
    public void toModelType_nonExistentContact_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(NON_EXISTENT_CLIENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidClientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_CLIENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidVendorFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_VENDOR_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidEventFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClients_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLIENTS_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_CLIENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateVendors_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VENDORS_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_VENDOR,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEvents_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENTS_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(DuplicateEventException.class, "Operation would result in duplicate events",
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_mismatchAssociation_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(MISMATCH_ASSOCIATION_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_ASSOCIATION_MISMATCH,
                dataFromFile::toModelType);
    }
}
