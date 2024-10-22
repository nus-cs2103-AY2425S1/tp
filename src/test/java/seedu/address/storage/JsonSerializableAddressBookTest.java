package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER
            .resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER
            .resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(TYPICAL_PERSONS_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_discardInvalidPersonOnly() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(INVALID_PERSON_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        System.out.println(addressBookFromFile.getPersonList());
        Person validPerson = TypicalPersons.ALICE;
        assertEquals(1, addressBookFromFile.getPersonList().size());
        assertEquals(validPerson, addressBookFromFile.getPersonList().get(0));
    }

    @Test
    public void toModelType_duplicatePersons_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        assertEquals(2, addressBookFromFile.getPersonList().size());
    }

    @Test
    public void toModelType_duplicatePersons_keepFirst() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        Person firstPerson = addressBookFromFile.getPersonList().get(0);
        assertEquals(TypicalPersons.ALICE, firstPerson);
        Person secondPerson = addressBookFromFile.getPersonList().get(1);
        assertEquals(TypicalPersons.BENSON, secondPerson);
    }

}
