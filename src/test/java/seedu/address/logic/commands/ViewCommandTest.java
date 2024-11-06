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

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
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
        model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
        unregisteredNric = new Nric("S1234567A");
    }

    @Test
    public void execute_validPatient_success() {
        Patient patientToView = KEANU;
        ModelManager expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());
        CommandResult expectedCommandResult = new ShowPatientInfoCommandResult(String.format(
                ViewCommand.MESSAGE_VIEW_SUCCESS, patientToView.getName()), patientToView, true);

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
