package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;

public class JsonSerializableAddressBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve(
            "invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve(
            "duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_CONCERT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateConcertAddressBook.json");
    private static final Path DUPLICATE_CONCERT_COONTACT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateConcertContactAddressBook.json");
    private static final Path INVALID_CONCERT_CONTACT_FILE_CONCERT = TEST_DATA_FOLDER.resolve(
            "invalidConcertContactConcertAddressBook.json");
    private static final Path INVALID_CONCERT_CONTACT_FILE_PERSON = TEST_DATA_FOLDER.resolve(
            "invalidConcertContactPersonAddressBook.json");
    private static final Path INVALID_CONCERT_FILE_DATE = TEST_DATA_FOLDER.resolve(
            "invalidConcertDateAddressBook.json");
    private static final Path INVALID_CONCERT_FILE_NAME = TEST_DATA_FOLDER.resolve(
            "invalidConcertDateAddressBook.json");
    private static final Path INVALID_CONCERT_FILE_ADDRESS = TEST_DATA_FOLDER.resolve(
            "invalidConcertAddressAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateConcerts_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONCERT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_CONCERT, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateConcertContact_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_CONCERT_COONTACT_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_CONCERT_CONTACT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidConcertContactFileMissingConcert_throwsIllegalValueException()
            throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_CONCERT_CONTACT_FILE_CONCERT, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_INVALID_CONCERT_CONTACT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidConcertContactFileMissingPerson_throwsIllegalValueException()
            throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_CONCERT_CONTACT_FILE_PERSON, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_INVALID_CONCERT_CONTACT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidConcert_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_CONCERT_FILE_DATE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);

        dataFromFile = JsonUtil.readJsonFile(INVALID_CONCERT_FILE_NAME,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);

        dataFromFile = JsonUtil.readJsonFile(INVALID_CONCERT_FILE_ADDRESS,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
