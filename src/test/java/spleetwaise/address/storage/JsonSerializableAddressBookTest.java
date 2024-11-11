package spleetwaise.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBook;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.util.JsonUtil;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_ID_FILE = TEST_DATA_FOLDER.resolve("duplicateIdAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class
        ).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class
        ).get();
        AddressBook ab = dataFromFile.toModelType();
        assertEquals(0, ab.getPersonList().size());
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class
        ).get();
        AddressBook ab = dataFromFile.toModelType();
        assertEquals(1, ab.getPersonList().size());
    }

    @Test
    public void toModelType_duplicateIds_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_ID_FILE,
                JsonSerializableAddressBook.class
        ).get();
        AddressBook ab = dataFromFile.toModelType();
        assertEquals(1, ab.getPersonList().size());
    }
}
