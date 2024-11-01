package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Prudy;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyPrudy_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPrudy_success() {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPrudy(), new UserPrefs());
        expectedModel.setPrudy(new Prudy());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
