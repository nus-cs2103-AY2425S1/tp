package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.BREAKFASTLEON;
import static seedu.address.testutil.TypicalReminders.GYMTRISTAN;
import static seedu.address.testutil.TypicalReminders.MEETINGJASON;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyReminderAddressBook;
import seedu.address.model.ReminderAddressBook;

public class JsonReminderAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonReminderAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readReminderAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readReminderAddressBook(null));
    }

    private java.util.Optional<ReadOnlyReminderAddressBook> readReminderAddressBook(String filePath) throws Exception {
        return new JsonReminderAddressBookStorage(Paths.get(filePath))
                   .readReminderAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readReminderAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class,
                     () -> readReminderAddressBook("notJsonFormatReminderAddressBook.json"));
    }

    @Test
    public void readReminderAddressBook_invalidReminderAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readReminderAddressBook("invalidReminderAddressBook.json"));
    }

    @Test
    public void readReminderAddressBook_invalidAndValidReminderAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class,
                     () -> readReminderAddressBook("invalidAndValidReminderAddressBook.json"));
    }

    @Test
    public void readAndSaveReminderAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempReminderAddressBook.json");
        ReminderAddressBook original = getTypicalReminderAddressBook();
        JsonReminderAddressBookStorage jsonReminderAddressBookStorage = new JsonReminderAddressBookStorage(filePath);

        // Save in new file and read back
        jsonReminderAddressBookStorage.saveReminderAddressBook(original, filePath);
        ReadOnlyReminderAddressBook readBack = jsonReminderAddressBookStorage.readReminderAddressBook(filePath).get();
        assertEquals(original, new ReminderAddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addReminder(BREAKFASTLEON);
        original.removeReminder(MEETINGJASON);
        jsonReminderAddressBookStorage.saveReminderAddressBook(original, filePath);
        readBack = jsonReminderAddressBookStorage.readReminderAddressBook(filePath).get();
        assertEquals(original, new ReminderAddressBook(readBack));

        // Save and read without specifying file path
        original.addReminder(GYMTRISTAN);
        jsonReminderAddressBookStorage.saveReminderAddressBook(original); // file path not specified
        readBack = jsonReminderAddressBookStorage.readReminderAddressBook().get(); // file path not specified
        assertEquals(original, new ReminderAddressBook(readBack));

    }

    @Test
    public void saveReminderAddressBook_nullReminderAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveReminderAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code reminderAddressBook} at the specified {@code filePath}.
     */
    private void saveReminderAddressBook(ReadOnlyReminderAddressBook reminderAddressBook, String filePath) {
        try {
            new JsonReminderAddressBookStorage(Paths.get(filePath))
                    .saveReminderAddressBook(reminderAddressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveReminderAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveReminderAddressBook(new ReminderAddressBook(), null));
    }
}
