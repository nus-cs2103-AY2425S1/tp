package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDoctors.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;
import seedu.address.testutil.DoctorBuilder;

public class AddDoctorCommandTest {

    @Test
    public void constructor_nullDoctor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDoctorCommand(null));
    }

    @Test
    public void execute_doctorAcceptedByModel_addSuccessful() throws Exception {
        AddDoctorCommandTest.ModelStubAcceptingDoctorAdded modelStub =
                new AddDoctorCommandTest.ModelStubAcceptingDoctorAdded();
        Doctor validDoctor = new DoctorBuilder().build();

        CommandResult commandResult = new AddDoctorCommand(validDoctor).execute(modelStub);

        assertEquals(String.format(AddDoctorCommand.MESSAGE_SUCCESS, Messages.format(validDoctor)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDoctor), modelStub.doctorsAdded);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor validDoctor = new DoctorBuilder().build();
        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(validDoctor);
        AddDoctorCommandTest.ModelStub modelStub = new AddDoctorCommandTest.ModelStubWithDoctor(validDoctor);

        assertThrows(CommandException.class, AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR, () ->
                addDoctorCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Doctor alice = new DoctorBuilder().withName("Alice").build();
        Doctor bob = new DoctorBuilder().withName("Bob").build();
        AddDoctorCommand addDoctorAliceCommand = new AddDoctorCommand(alice);
        AddDoctorCommand addDoctorBobCommand = new AddDoctorCommand(bob);

        // same object -> returns true
        assertTrue(addDoctorAliceCommand.equals(addDoctorAliceCommand));

        // same values -> returns true
        AddDoctorCommand addDoctorAliceCommandCopy = new AddDoctorCommand(alice);
        assertTrue(addDoctorAliceCommand.equals(addDoctorAliceCommandCopy));

        // different types -> returns false
        assertFalse(addDoctorAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addDoctorAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addDoctorAliceCommand.equals(addDoctorBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddDoctorCommand addDoctorCommand = new AddDoctorCommand(ALICE);
        String expected = AddDoctorCommand.class.getCanonicalName() + "{doctorToAdd=" + ALICE + "}";
        assertEquals(expected, addDoctorCommand.toString());
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
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
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
     * A Model stub that contains a single doctor.
     */
    private class ModelStubWithDoctor extends AddDoctorCommandTest.ModelStub {
        private final Doctor doctor;

        ModelStubWithDoctor(Doctor doctor) {
            requireNonNull(doctor);
            this.doctor = doctor;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.doctor.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the doctor being added.
     */
    private class ModelStubAcceptingDoctorAdded extends AddDoctorCommandTest.ModelStub {
        final ArrayList<Person> doctorsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return doctorsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            doctorsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
