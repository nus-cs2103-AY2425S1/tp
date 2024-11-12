package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_CONFIRMATION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains tests for {@code ClearCommand}.
 */

public class ClearCommandTest {
    @Test
    public void execute_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult commandResult = new ClearCommand().execute(model);

        assertEquals(commandResult, new CommandResult(MESSAGE_CONFIRMATION, false, false,
                true));
    }


}
