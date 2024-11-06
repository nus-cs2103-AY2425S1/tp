package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WeddingBuilder;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;
    public static AddressBook getTypicalAddressBookForStorage() {
        Wedding aliceWedding = new WeddingBuilder()
                .withName("Alice Adam Wedding")
                .withVenue("Marina Bay Sands")
                .withDate("2024-12-12")
                .build();

        Wedding georgeWedding = new WeddingBuilder()
                .withName("George Jane Wedding")
                .withVenue("Sentosa")
                .withDate("2025-01-01")
                .build();

        Person alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withEmail("alice@example.com")
                .withPhone("94351253")
                .withRole("florist")
                .withOwnWedding(aliceWedding)
                .build();

        Person benson = new PersonBuilder()
                .withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com")
                .withPhone("98756432")
                .withRole("caterer")
                .addWeddingJob(georgeWedding)
                .build();

        Person carl = new PersonBuilder()
                .withName("Carl Kurz")
                .withPhone("95352563")
                .withEmail("heinz@example.com")
                .withAddress("wall street")
                .withRole("florist")
                .addWeddingJob(georgeWedding)
                .build();

        Person george = new PersonBuilder()
                .withName("George Best")
                .withPhone("94824422")
                .withEmail("anna@example.com")
                .withAddress("4th street")
                .withOwnWedding(georgeWedding)
                .build();

        ArrayList<Person> persons = new ArrayList<>(Arrays.asList(alice, benson, carl, george));
        ArrayList<Wedding> weddings = new ArrayList<>(Arrays.asList(aliceWedding, georgeWedding));
        AddressBook addressBook = new AddressBook();
        for (Person person : persons) {
            addressBook.addPerson(person);
        }
        for (Wedding wedding : weddings) {
            addressBook.addWedding(wedding);
        }
        return addressBook;
    }

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
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBookForStorage();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
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
