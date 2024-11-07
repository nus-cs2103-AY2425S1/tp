package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private Model model;
    private Model expectedModel;

    @Test
    public void execute_emptyListClearPromptsConfirmationCheck_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();
        ClearCommand.setIsClear(false);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_EMPTY, expectedModel);
        assertFalse(ClearCommand.getIsClear());
    }

    @Test
    public void execute_nonEmptyListClearPromptsConfirmationCheck_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());;
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ClearCommand.setIsClear(false);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CHECK, expectedModel);
        assertTrue(ClearCommand.getIsClear());
    }

}
