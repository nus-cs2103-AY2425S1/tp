package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());
        expectedModel.addPatient(validPatient);
        CommandResult expectedCommandResult =
                new ShowPatientInfoCommandResult(String.format(
                        AddCommand.MESSAGE_SUCCESS, Messages.format(validPatient)), validPatient, true);
        assertCommandSuccess(new AddCommand(validPatient), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient patientInList = model.getClinicConnectSystem().getPatientList().get(0);
        assertCommandFailure(new AddCommand(patientInList), model,
                AddCommand.MESSAGE_DUPLICATE_PATIENT);
    }

}
