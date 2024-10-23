package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());

    @Test
    public void execute_emptyFutureStack_success() {
        ModelManager expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
