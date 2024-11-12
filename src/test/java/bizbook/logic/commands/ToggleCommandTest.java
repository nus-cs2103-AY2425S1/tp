package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;

/**
 * Contains unit tests for {@code ToggleCommand}.
 */
public class ToggleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_toggle_success() throws CommandException {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        ToggleCommand toggleCommand = new ToggleCommand();

        assertCommandSuccess(toggleCommand, model, ToggleCommand.MESSAGE_TOGGLE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ToggleCommand toggleFirstCommand = new ToggleCommand();
        ToggleCommand toggleSecondCommand = new ToggleCommand();

        assertEquals(toggleFirstCommand, toggleFirstCommand);
        assertTrue(toggleFirstCommand.equals(toggleSecondCommand));

        assertFalse(toggleFirstCommand.equals(null));
    }
}
