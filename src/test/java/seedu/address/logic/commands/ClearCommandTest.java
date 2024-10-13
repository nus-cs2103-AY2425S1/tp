package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import org.junit.jupiter.api.Test;

import seedu.address.model.ClientHub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyClientHub_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyClientHub_success() {
        Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalClientHub(), new UserPrefs());
        expectedModel.setClientHub(new ClientHub());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
