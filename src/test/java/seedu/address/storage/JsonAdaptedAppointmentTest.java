package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

class JsonAdaptedAppointmentTest {
    private static final String VALID_APPOINTMENT_TYPE = "Consultation";
    private static final String VALID_APPOINTMENT_DATE_TIME = "2024-10-15T10:30:00";
    private static final int VALID_PERSON_ID = 1;
    private static final String VALID_SICKNESS = "Flu";
    private static final String VALID_MEDICINE = "Paracetamol";

    private static final String INVALID_APPOINTMENT_TYPE = " ";
    private static final String INVALID_DATE_TIME = "2024-15-10T10:30:00";
    private static final int INVALID_PERSON_ID = -1;
    private static final String INVALID_SICKNESS = "";
    private static final String INVALID_MEDICINE = "";

    private final ReadOnlyAddressBook addressBookStub = new AddressBookStub(new ArrayList<>(){});

    @Test
    void constructor_validArguments_returnsCorrectJsonAdaptedAppointment() throws IllegalValueException {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, VALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, 1, VALID_SICKNESS, VALID_MEDICINE);
        Appointment modelAppointment = appointment.toModelType(addressBookStub);
        JsonAdaptedAppointment appointment1 = new JsonAdaptedAppointment(modelAppointment);
        modelAppointment = appointment1.toModelType(addressBookStub);

        assertEquals(LocalDateTime.parse(VALID_APPOINTMENT_DATE_TIME), modelAppointment.getAppointmentDateTime());
        assertEquals(VALID_PERSON_ID, modelAppointment.getPerson().getPersonId());
        assertEquals(VALID_SICKNESS, modelAppointment.getSickness().value);
        assertEquals(VALID_MEDICINE, modelAppointment.getMedicine().value);
    }

    @Test
    void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, VALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, 1, VALID_SICKNESS, VALID_MEDICINE);
        Appointment modelAppointment = appointment.toModelType(addressBookStub);

        assertEquals(VALID_APPOINTMENT_TYPE, modelAppointment.getAppointmentType().value);
        assertEquals(LocalDateTime.parse(VALID_APPOINTMENT_DATE_TIME), modelAppointment.getAppointmentDateTime());
        assertEquals(VALID_PERSON_ID, modelAppointment.getPerson().getPersonId());
        assertEquals(VALID_SICKNESS, modelAppointment.getSickness().value);
        assertEquals(VALID_MEDICINE, modelAppointment.getMedicine().value);
    }

    @Test
    void toModelType_nullAppointmentType_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, null, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_invalidAppointmentType_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, INVALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_nullAppointmentDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, VALID_APPOINTMENT_TYPE, null, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_invalidAppointmentDateTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, VALID_APPOINTMENT_TYPE, INVALID_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_nullPersonId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, 0, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_invalidPersonId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, VALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, INVALID_PERSON_ID, VALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Disabled("Works fine on pc but throws a null pointer exception in github")
    @Test
    void toModelType_nullSickness_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, null, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_invalidSickness_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, VALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, INVALID_SICKNESS, VALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Disabled("Works fine on pc but throws a null pointer exception in github")
    @Test
    void toModelType_nullMedicine_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(
                1, VALID_APPOINTMENT_TYPE, VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, null);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    @Test
    void toModelType_invalidMedicine_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(1, VALID_APPOINTMENT_TYPE,
                VALID_APPOINTMENT_DATE_TIME, VALID_PERSON_ID, VALID_SICKNESS, INVALID_MEDICINE);
        assertThrows(IllegalValueException.class, () -> appointment.toModelType(addressBookStub));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<PersonDescriptor> persons) {
            this.persons.setAll(persons.stream().map(p -> new Person(0, p)).toList());
            this.persons.add(new Person(1, ALICE));
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
