package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

public class ViewCommandTest {
    private Model model;
    private Nric unregisteredNric;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        unregisteredNric = new Nric("S1234567A");
    }

    @Test
    public void execute_validPatient_success() {
        Patient patientToView = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(String.format(ViewCommand.MESSAGE_VIEW_SUCCESS,
                patientToView.getName()), null, false, patientToView, true,
                false);

        assertCommandSuccess(new ViewCommand(patientToView.getNric()), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        ViewCommand failingViewCommand = new ViewCommand(unregisteredNric);
        assertCommandFailure(failingViewCommand, model, ViewCommand.MESSAGE_PATIENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        Nric nric1 = new Nric("S1234567A");
        Nric nric2 = new Nric("S7654321B");
        ViewCommand viewCommand1 = new ViewCommand(nric1);
        ViewCommand viewCommand2 = new ViewCommand(nric2);

        // same object -> returns true
        assertTrue(viewCommand1.equals(viewCommand1));

        // same values -> returns true
        ViewCommand viewCommand1Copy = new ViewCommand(nric1);
        assertTrue(viewCommand1.equals(viewCommand1Copy));

        // different types -> returns false
        assertFalse(viewCommand1.equals(1));

        // null -> returns false
        assertFalse(viewCommand1.equals(null));

        // different nric -> returns false
        assertFalse(viewCommand1.equals(viewCommand2));
    }

    @Test
    public void toStringMethod() {
        Nric targetNric = new Nric("S1234567A");
        ViewCommand viewCommand = new ViewCommand(targetNric);
        String expected = ViewCommand.class.getCanonicalName() + "{targetNric=" + targetNric + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
