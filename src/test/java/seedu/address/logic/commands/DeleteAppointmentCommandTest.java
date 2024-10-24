package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;

/**
 * Unit tests for {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {
    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAppointmentCommand(null));
    }

    @Test
    public void execute_appointmentAcceptedByModel_deleteSuccessful() throws Exception {
        ModelStubAcceptingAppointmentDeleted modelStub = new ModelStubAcceptingAppointmentDeleted();
        Appointment validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = new DeleteAppointmentCommand(validAppointment).execute(modelStub);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                validAppointment.getPatient().getName().toString(),
                validAppointment.getDoctor().getName().toString(),
                validAppointment.getDate().toString(),
                validAppointment.getTime().toString());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        modelStub.deleteAppointment(validAppointment);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsDeleted);
    }

    @Test
    public void execute_nonExistingAppointment_throwsCommandException() {
        Appointment validAppointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(validAppointment);
        ModelStub modelStub = new ModelStubWithNoAppointment();

        String expectedMessage = DeleteAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_ID;
        System.out.println("Expected Error Message: " + expectedMessage);

        try {
            deleteAppointmentCommand.execute(modelStub);
        } catch (CommandException e) {
            String actualMessage = e.getMessage();
            System.out.println("Actual Error Message: " + actualMessage);

            assertEquals(expectedMessage, actualMessage, "The actual message does not match the expected one.");
        }
    }

    @Test
    public void equals() {
        // Create two different appointments for comparison
        Appointment appointment1 = new AppointmentBuilder().withDate("12-12-2023").build();
        Appointment appointment2 = new AppointmentBuilder().withDate("13-12-2023").build();

        // Create DeleteAppointmentCommand for both appointments
        DeleteAppointmentCommand deleteAppointment1Command = new DeleteAppointmentCommand(appointment1);
        DeleteAppointmentCommand deleteAppointment2Command = new DeleteAppointmentCommand(appointment2);

        // same object -> returns true
        assertTrue(deleteAppointment1Command.equals(deleteAppointment1Command));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointment1CommandCopy = new DeleteAppointmentCommand(appointment1);
        assertTrue(deleteAppointment1Command.equals(deleteAppointment1CommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointment1Command.equals(1));

        // null -> returns false
        assertFalse(deleteAppointment1Command.equals(null));

        // different appointment -> returns false
        assertFalse(deleteAppointment1Command.equals(deleteAppointment2Command));
    }

    /**
     * A default model stub that has all methods failing.
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
        public void setAddressBook(ReadOnlyAddressBook addressBook) {

        }

        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        public void deleteAppointment(Appointment appointment) {
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
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {

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
    }

    /**
     * A Model stub that contains no appointment.
     */
    private class ModelStubWithNoAppointment extends DeleteAppointmentCommandTest.ModelStub {

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return false; // No appointment exists in this stub
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the appointment being deleted.
     */
    private class ModelStubAcceptingAppointmentDeleted extends ModelStub {
        final ArrayList<Appointment> appointmentsDeleted = new ArrayList<>();

        @Override
        public boolean hasAppointment(Appointment appointment) {
            requireNonNull(appointment);
            return appointmentsDeleted.stream().anyMatch(appt -> appt.isSameAppointment(appointment));
        }

        @Override
        public void deleteAppointment(Appointment appointment) {
            requireNonNull(appointment);
            appointmentsDeleted.add(appointment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
