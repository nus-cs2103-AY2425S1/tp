package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.CARL;

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
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.PatientBuilder;

public class AddPatientCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPatientCommand(null));
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        AddPatientCommandTest.ModelStubAcceptingPatientAdded modelStub =
                new AddPatientCommandTest.ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new AddPatientCommand(validPatient).execute(modelStub);

        assertEquals(String.format(AddPatientCommand.MESSAGE_SUCCESS, Messages.format(validPatient)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        AddPatientCommand addPatientCommand = new AddPatientCommand(validPatient);
        AddPatientCommandTest.ModelStub modelStub = new AddPatientCommandTest.ModelStubWithPatient(validPatient);

        assertThrows(CommandException.class, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT, () ->
                addPatientCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        AddPatientCommand addPatientAliceCommand = new AddPatientCommand(alice);
        AddPatientCommand addPatientBobCommand = new AddPatientCommand(bob);

        // same object -> returns true
        assertTrue(addPatientAliceCommand.equals(addPatientAliceCommand));

        // same values -> returns true
        AddPatientCommand addPatientAliceCommandCopy = new AddPatientCommand(alice);
        assertTrue(addPatientAliceCommand.equals(addPatientAliceCommandCopy));

        // different types -> returns false
        assertFalse(addPatientAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addPatientAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addPatientAliceCommand.equals(addPatientBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddPatientCommand addPatientCommand = new AddPatientCommand(CARL);
        String expected = AddPatientCommand.class.getCanonicalName() + "{patientToAdd=" + CARL + "}";
        assertEquals(expected, addPatientCommand.toString());
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
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends AddPatientCommandTest.ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends AddPatientCommandTest.ModelStub {
        final ArrayList<Person> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return patientsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            patientsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
