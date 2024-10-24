package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.AddAppointmentCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON_P;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.testutil.AppointmentBuilder;

public class AddAppointmentCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, 1));
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        AppointmentDescriptor validAppointmentDescriptor = new AppointmentBuilder().build().getAppointmentDescriptor();

        CommandResult commandResult = new AddAppointmentCommand(validAppointmentDescriptor, 1)
                .execute(modelStub);

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS,
                Messages.formatAppointment(validAppointmentDescriptor)), commandResult.getFeedbackToUser());
        assertEquals(List.of(validAppointmentDescriptor), modelStub.appointmentsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        AppointmentDescriptor validAppointmentDescriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        Appointment validAppointment = new AppointmentBuilder().build();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointmentDescriptor, 1);
        AddAppointmentCommandTest.ModelStub modelStub = new AddAppointmentCommandTest
                .ModelStubWithAppointment(validAppointment);

        assertThrows(CommandException.class,
                AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT, () -> addAppointmentCommand.execute(modelStub));
    }

    @Test
    public void execute_getPersonIdDoesNotExists_returnsCorrectMessage() {
        AppointmentDescriptor validAppointmentDescriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointmentDescriptor, 1);
        assertEquals(addAppointmentCommand.getPersonIdDoesNotExistMessage(), MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {
        AppointmentDescriptor validAppointmentDescriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        AppointmentDescriptor validAppointmentDescriptor2 = new AppointmentBuilder().withMedicine("panadol")
                .build().getAppointmentDescriptor();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointmentDescriptor, 1);
        AddAppointmentCommand addAppointmentCommand1 = new
                AddAppointmentCommand(validAppointmentDescriptor2, 1);

        assertEquals(addAppointmentCommand, addAppointmentCommand);

        assertEquals(addAppointmentCommand1, new AddAppointmentCommand(validAppointmentDescriptor2, 1));

        assertNotEquals(1, addAppointmentCommand1);

        assertNotEquals(null, addAppointmentCommand);

        assertNotEquals(addAppointmentCommand, addAppointmentCommand1);
    }

    @Test
    public void toStringMethod() {
        AppointmentDescriptor validAppointmentDescriptor = new AppointmentBuilder().build().getAppointmentDescriptor();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointmentDescriptor, 1);
        String expected = "seedu.address.logic.commands.AddAppointmentCommand"
                + "{toAdd=seedu.address.model.appointment.AppointmentDescriptor{appointment type=Health Checkup, "
                + "appointment datetime=2024-10-15T10:30, sickness=Flu, medicine=Panadol}}";
        assertEquals(expected, addAppointmentCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
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
        public Person addPerson(PersonDescriptor person) {
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
        public boolean hasPerson(PersonDescriptor person) {
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
        public Path getAppointmentBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppointmentBook getAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(AppointmentDescriptor appointmentDescriptor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> findPerson(int personId) {
            return Optional.of(BENSON_P);
        }

        @Override
        public void deleteAppointment(Appointment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Appointment addAppointment(Person person, AppointmentDescriptor appointmentDescriptor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment target, Appointment editedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

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

        @Override
        public boolean hasAppointment(AppointmentDescriptor appointmentDescriptor) {
            requireNonNull(appointmentDescriptor);
            return this.appointment.isSameAppointment(appointmentDescriptor);
        }
    }

    private class ModelStubAcceptingAppointmentAdded extends AddAppointmentCommandTest.ModelStub {
        final ArrayList<AppointmentDescriptor> appointmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return appointmentsAdded.stream().anyMatch(appointment::isSameAppointment);
        }

        @Override
        public boolean hasAppointment(AppointmentDescriptor appointmentDescriptor) {
            requireNonNull(appointmentDescriptor);
            return appointmentsAdded.stream().anyMatch(appointmentDescriptor::isSameAppointment);
        }

        @Override
        public Appointment addAppointment(Person person, AppointmentDescriptor appointment) {
            requireNonNull(appointment);
            appointmentsAdded.add(appointment);
            return new Appointment(0, BENSON_P, appointment);
        }

        @Override
        public ReadOnlyAppointmentBook getAppointmentBook() {
            return new AppointmentBook();
        }
    }
}
