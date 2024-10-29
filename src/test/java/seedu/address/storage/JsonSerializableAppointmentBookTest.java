package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.testutil.TypicalAppointments;

public class JsonSerializableAppointmentBookTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableAppointmentBookTest");
    private static final Path TYPICAL_APPOINTMENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalAppointmentsAppointmentBook.json");
    private static final Path INVALID_APPOINTMENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidAppointmentAppointmentBook.json");
    private static final Path DUPLICATE_APPOINTMENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateAppointmentAppointmentBook.json");
    private static final ArrayList<PersonDescriptor> typicalPersons = new ArrayList<>(
            Arrays.asList(BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
    private final ReadOnlyAddressBook addressBookStub = new AddressBookStub(typicalPersons);

    // todo: Same error as the testcase in JsonAdaptedAppointmentBookTest.java
    @Disabled("For next iteration")
    @Test
    void toModelType_typicalAppointmentsFile_success() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPOINTMENTS_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType(addressBookStub);
        AppointmentBook typicalAppointmentsAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        assertEquals(appointmentBookFromFile, typicalAppointmentsAppointmentBook);
    }

    @Test
    void toModelType_invalidAppointmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(addressBookStub));
    }


    // todo: Same error as the testcase in JsonAdaptedAppointmentBookTest.java
    @Disabled("For next iteration")
    @Test
    void toModelType_duplicateAppointments_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class,
                JsonSerializableAppointmentBook.MESSAGE_DUPLICATE_APPOINTMENT, () -> dataFromFile
                        .toModelType(addressBookStub));
    }

    @Test
    void toModelType_emptyAppointmentList_noExceptionThrown() throws IllegalValueException {
        JsonSerializableAppointmentBook jsonSerializableAppointmentBook =
                new JsonSerializableAppointmentBook(Collections.emptyList(), 0);
        AppointmentBook appointmentBook = jsonSerializableAppointmentBook.toModelType(addressBookStub);
        assertEquals(0, appointmentBook.getAppointmentList().size());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<PersonDescriptor> persons) {
            this.persons.setAll(persons.stream().map(p -> new Person(getNextPersonId(), p)).toList());
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

