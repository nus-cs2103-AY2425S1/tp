package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for HomeCommand.
 */
public class HomeCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
        expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());
    }

    @Test
    public void execute_homeCommand_success() {
        String msg_success = String.format(HomeCommand.MESSAGE_SUCCESS, model.getPatientSize());
        assertCommandSuccess(new HomeCommand(), model, msg_success, expectedModel);
    }
}
