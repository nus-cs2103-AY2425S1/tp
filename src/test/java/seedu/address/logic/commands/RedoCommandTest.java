package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());

    @Test
    public void execute_emptyFutureStack_failure() {
        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_FAILURE);
    }
}
