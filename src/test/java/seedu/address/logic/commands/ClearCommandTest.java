package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import org.junit.jupiter.api.Test;

import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyClinicConnectSystem_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyClinicConnectSystem_success() {
        Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
        expectedModel.setClinicConnectSystem(new ClinicConnectSystem());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
