package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.KEANU;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    private Model model;
    private Nric unregisteredNric;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
        unregisteredNric = new Nric("S1234567A");
    }
    @Test
    public void execute_validName_success() {
        Patient patientToDelete = KEANU;
        DeleteCommand deleteCommand = new DeleteCommand(patientToDelete.getNric());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PATIENT_SUCCESS,
                Messages.format(patientToDelete));

        ModelManager expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());
        expectedModel.deletePatient(patientToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(unregisteredNric);

        assertCommandFailure(deleteCommand, model,
                String.format(Messages.MESSAGE_INVALID_PATIENT_NRIC, unregisteredNric));
    }

    @Test
    public void equals() {
        Nric firstNric = new Nric("S1234567A");
        Nric secondNric = new Nric("S1234567B");
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstNric);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondNric);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstNric);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Nric targetNric = new Nric("S1234567A");
        DeleteCommand deleteCommand = new DeleteCommand(targetNric);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetNric=" + targetNric + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
