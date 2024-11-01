package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyClinicConnectSystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class AddFCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFCommand(null));
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        Patient validPatient = new PatientBuilder().build();
        AddFCommandTest.ModelStubWithOnlyOnePatient modelStub =
                new AddFCommandTest.ModelStubWithOnlyOnePatient(validPatient);
        Patient alice = new PatientBuilder().withNric("T0123456A").build();
        CommandResult commandResult = new AddFCommand(alice).execute(modelStub);

        assertEquals(String.format(AddFCommand.MESSAGE_SUCCESS, Messages.format(alice)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        AddFCommand addFCommand = new AddFCommand(validPatient);
        AddFCommandTest.ModelStubWithOnlyOnePatient modelStub =
                new AddFCommandTest.ModelStubWithOnlyOnePatient(validPatient);

        assertThrows(CommandException.class, AddFCommand.MESSAGE_DUPLICATE_PATIENT, () ->
                addFCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withNric("T0123456A").build();
        Patient bob = new PatientBuilder().withNric("S0123456A").build();
        AddFCommand addFAliceCommand = new AddFCommand(alice);
        AddFCommand addFBobCommand = new AddFCommand(bob);

        // same object -> returns true
        assertTrue(addFAliceCommand.equals(addFAliceCommand));

        // same values -> returns true
        AddFCommand addAliceCommandCopy = new AddFCommand(alice);
        assertTrue(addFAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addFAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addFAliceCommand.equals(null));

        // different nric -> returns false
        assertFalse(addFAliceCommand.equals(addFBobCommand));
    }
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
        public Path getClinicConnectSystemFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicConnectSystemFilePath(Path clinicConnectSystemFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClinicConnectSystem(ReadOnlyClinicConnectSystem clinicConnectSystem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinicConnectSystem getClinicConnectSystem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFilteredAppts(TreeSet<FilteredAppointment> filteredAppointments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TreeSet<FilteredAppointment> getFilteredAppts() {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that will only store 1 patient and always accept the patient being added
     * unless the patient is already stored in the model.
     */
    private class ModelStubWithOnlyOnePatient extends ModelStub {

        private final Patient patient;

        ModelStubWithOnlyOnePatient(Patient patient) {
            this.patient = patient;
        }
        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.isSamePatient(patient);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
        }
    }
}
