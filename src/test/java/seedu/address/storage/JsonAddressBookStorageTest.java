package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_A;
import static seedu.address.testutil.TypicalEvents.EVENT_G;
import static seedu.address.testutil.TypicalEvents.EVENT_H;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.GRACE;
import static seedu.address.testutil.TypicalVolunteers.HENRY;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVolunteers;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidVolunteerAddressBook_throwDataLoadingException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        assertDoesNotThrow(() -> readAddressBook("oneInvalidVolunteerAddressBook.json"));
        //@@author
    }


    @Test
    public void readAddressBook_invalidAndValidVolunteerAddressBook_throwDataLoadingException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        assertDoesNotThrow(() -> readAddressBook("invalidAndValidVolunteerAddressBook.json"));
        //@@author
    }


    @Test
    public void readAddressBook_invalidEventAddressBook_throwDataLoadingException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        assertDoesNotThrow(() -> readAddressBook("oneInvalidEventAddressBook.json"));
        //@@author
    }

    @Test
    public void readAddressBook_invalidAndValidEventAddressBook_throwDataLoadingException() {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        assertDoesNotThrow(() -> readAddressBook("invalidAndValidEventAddressBook.json"));
        //@@author
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    @Test
    public void readAndSaveVolunteersAddressBook_allInOrder_success() throws Exception {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        Path filePath = testFolder.resolve("TempVolunteerAddressBook.json");
        AddressBook original = TypicalVolunteers.getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addVolunteer(GRACE);
        original.removeVolunteer(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addVolunteer(HENRY);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));
        //@@author
    }

    @Test
    public void readAndSaveEventsAddressBook_allInOrder_success() throws Exception {
        //@@author {Fluffykan}-reused
        // Reused from JsonAddressBookStorageTest of AB3 with minor modifications
        Path filePath = testFolder.resolve("TempEventAddressBook.json");
        AddressBook original = TypicalEvents.getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back

        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEvent(EVENT_G);
        original.removeEvent(EVENT_A);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addEvent(EVENT_H);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));
        //@@author
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
