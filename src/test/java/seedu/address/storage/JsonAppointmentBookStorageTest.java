package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

public class JsonAppointmentBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAppointmentBookStorageTest");

    @TempDir
    public Path testFolder;
    private final ReadOnlyAddressBook addressBookStub = new AddressBookStub(new ArrayList<>(){});

    @Test
    public void readAppointmentBook_nullFilePath_throwsNullPointerException() {
        // EP: Null file path
        assertThrows(NullPointerException.class, () -> readAppointmentBook(null));
    }

    private Optional<ReadOnlyAppointmentBook> readAppointmentBook(String filePath) throws Exception {
        return new JsonAppointmentBookStorage(Paths.get(filePath))
                .readAppointmentBook(addToTestDataPathIfNotNull(filePath), addressBookStub);
    }

    private Path addToTestDataPathIfNotNull(String filePath) {
        return filePath != null
                ? TEST_DATA_FOLDER.resolve(filePath)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        // EP: Missing file, expect empty result
        assertFalse(readAppointmentBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        // EP: Incorrect format, expect DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("notJsonFormatAppointmentBook.json"));
    }

    @Test
    public void readAppointmentBook_invalidAppointmentBook_throwDataLoadingException() {
        // EP: Invalid data in JSON, expect DataLoadingException
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("invalidAppointmentBook.json"));
    }

    @Test
    public void readAppointmentBook_invalidAndValidAppointmentBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAppointmentBook("invalidAndValidAppointmentBook.json"));
    }

    @Test
    public void saveAppointmentBook_nullAppointmentBook_throwsNullPointerException() {
        // EP: Null appointment book input
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code appointmentBook} at the specified {@code filePath}.
     */
    private void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, String filePath) {
        // EP: Null file path
        try {
            new JsonAppointmentBookStorage(Paths.get(filePath))
                    .saveAppointmentBook(appointmentBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(new AppointmentBook(), null));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<PersonDescriptor> persons) {
            this.persons.setAll(persons.stream().map(p -> new Person(0, p)).toList());
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public Optional<Person> findPerson(int personId) {
            return persons.stream()
                          .filter(person -> person.getPersonId() == personId)
                          .findFirst();
        }

        @Override
        public int getNextPersonId() {
            return persons.size();
        }
    }
}
