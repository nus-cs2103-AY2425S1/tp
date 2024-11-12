package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;


/**
 * Unit tests for {@code AddAppointmentCommand}.
 */
public class AddAppointmentCommandTest {

    @Test
    public void constructor_nullAppointmentFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, null,
                null, null));
    }

    @Test
    public void equals() {
        Appointment appointmentOne = new AppointmentBuilder().withDate("12-12-2023").build();
        Appointment appointmentTwo = new AppointmentBuilder().withDate("13-12-2023").build();
        AddAppointmentCommand addAppointmentOneCommand =
                new AddAppointmentCommand(appointmentOne.getPatient().getName(),
                        appointmentOne.getDoctor().getName(), appointmentOne.getDate(), appointmentOne.getTime());
        AddAppointmentCommand addAppointmentTwoCommand =
                new AddAppointmentCommand(appointmentTwo.getPatient().getName(),
                        appointmentTwo.getDoctor().getName(), appointmentTwo.getDate(), appointmentTwo.getTime());

        // same object -> returns true
        assertTrue(addAppointmentOneCommand.equals(addAppointmentOneCommand));

        // same values -> returns true
        AddAppointmentCommand addAppointmentOneCommandCopy =
                new AddAppointmentCommand(appointmentOne.getPatient().getName(),
                        appointmentOne.getDoctor().getName(), appointmentOne.getDate(), appointmentOne.getTime());
        assertTrue(addAppointmentOneCommand.equals(addAppointmentOneCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentOneCommand.equals(1));

        // null -> returns false
        assertFalse(addAppointmentOneCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addAppointmentOneCommand.equals(addAppointmentTwoCommand));
    }

    @Test
    public void checkForClashingAppointments_clashingDate_throwsException() {
        Appointment appointmentOne = new AppointmentBuilder().withDate("12-12-2023").withTime("1400").build();
        Appointment appointmentTwo = new AppointmentBuilder().withDate("12-12-2023").withTime("1400").build();

        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.addAppointment(appointmentOne);

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(
                appointmentTwo.getPatient().getName(),
                appointmentTwo.getDoctor().getName(),
                appointmentTwo.getDate(),
                appointmentTwo.getTime()
        );

        assertThrows(CommandException.class, () -> addAppointmentCommand.execute(modelStub));
    }



    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single appointment.
     */
    private class ModelStubWithAppointment extends AddAppointmentCommandTest.ModelStub {
        private final Appointment appointment;

        ModelStubWithAppointment(Appointment appointment) {
            requireNonNull(appointment);
            this.appointment = appointment;
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return this.appointment.isSameAppointment(appointment);
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends AddAppointmentCommandTest.ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return appointmentsAdded.stream().anyMatch(appointment::isSameAppointment);
        }

        @Override
        public void addAppointment(Appointment appointment) {
            requireNonNull(appointment);
            appointmentsAdded.add(appointment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
