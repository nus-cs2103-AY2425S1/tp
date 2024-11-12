package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVolunteers;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_VOLUNTEER_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_VOLUNTEER_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_VOLUNTEER_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path TYPICAL_EVENT_FILE = TEST_DATA_FOLDER.resolve("typicalEventAddressBook.json");
    private static final Path INVALID_EVENT_FILE = TEST_DATA_FOLDER.resolve("invalidEventAddressBook.json");
    private static final Path DUPLICATE_EVENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEventAddressBook.json");
    @Test
    public void toModelType_invalidVolunteerFile_returnsEmptyAddressBook() {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_VOLUNTEER_FILE,
                    JsonSerializableAddressBook.class).get();
            assertDoesNotThrow(dataFromFile::toModelType);
            AddressBook emptyFile = new AddressBook();
            assertEquals(emptyFile, dataFromFile.toModelType());
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }
    @Test
    public void toModelType_typicalVolunteerFile_success() throws Exception {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_VOLUNTEER_FILE,
                    JsonSerializableAddressBook.class).get();
            AddressBook addressBookFromFile = dataFromFile.toModelType();
            AddressBook typicalPersonsAddressBook = TypicalVolunteers.getTypicalAddressBook();
            assertEquals(addressBookFromFile, typicalPersonsAddressBook);
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }

    @Test
    public void toModelType_duplicateVolunteer_throwsIllegalValueException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_VOLUNTEER_FILE,
                    JsonSerializableAddressBook.class).get();
            assertDoesNotThrow(dataFromFile::toModelType);
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }

    @Test
    public void toModelType_invalidEventFile_returnsEmptyAddressBook() {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EVENT_FILE,
                    JsonSerializableAddressBook.class).get();
            assertDoesNotThrow(dataFromFile::toModelType);
            AddressBook emptyFile = new AddressBook();
            assertEquals(emptyFile, dataFromFile.toModelType());
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }
    @Test
    public void toModelType_typicalEventFile_success() {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EVENT_FILE,
                    JsonSerializableAddressBook.class).get();
            AddressBook addressBookFromFile = dataFromFile.toModelType();
            AddressBook typicalPersonsAddressBook = TypicalEvents.getTypicalAddressBook();
            assertEquals(addressBookFromFile, typicalPersonsAddressBook);
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }

    @Test
    public void toModelType_duplicateEvent_throwsIllegalValueException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonSerializableAddressBookStorageTest of AB3 with minor modifications
        try {
            JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EVENT_FILE,
                    JsonSerializableAddressBook.class).get();
            assertDoesNotThrow(dataFromFile::toModelType);
        } catch (DataLoadingException e) {
            throw new AssertionError("DataLoadingException should not have been thrown.");
        }
        //@@author
    }

}
