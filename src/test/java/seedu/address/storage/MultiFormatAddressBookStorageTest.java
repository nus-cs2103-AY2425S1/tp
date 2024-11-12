package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class MultiFormatAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "MultiFormatAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String saveFilePath) throws Exception {
        return new MultiFormatAddressBookStorage(Paths.get(saveFilePath), Paths.get(saveFilePath))
                .readAddressBook(addToTestDataPathIfNotNull(saveFilePath));
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
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path saveFilePath = testFolder.resolve("TempAddressBook.json");
        Path exportFilePath = testFolder.resolve("TempAddressBook.csv");
        AddressBook original = getTypicalAddressBook();
        MultiFormatAddressBookStorage multiFormatAddressBookStorage = new MultiFormatAddressBookStorage(saveFilePath,
                exportFilePath);

        // Save in new file and read back
        multiFormatAddressBookStorage.saveAddressBook(original, saveFilePath);
        ReadOnlyAddressBook readBack = multiFormatAddressBookStorage.readAddressBook(saveFilePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        multiFormatAddressBookStorage.saveAddressBook(original, saveFilePath);
        readBack = multiFormatAddressBookStorage.readAddressBook(saveFilePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        multiFormatAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = multiFormatAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json",
                "SomeFile.csv"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code saveFilePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String saveFilePath, String exportFilePath) {
        try {
            new MultiFormatAddressBookStorage(Paths.get(saveFilePath), Paths.get(exportFilePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(saveFilePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null,
                null));
    }
}
