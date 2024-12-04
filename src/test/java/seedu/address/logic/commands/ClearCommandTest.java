package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void undo_commandExecuted_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = ClearCommand.MESSAGE_UNDO_SUCCESS;
        ClearCommand clearCommand = new ClearCommand();
        clearCommand.execute(model);
        CommandResult undoResult = clearCommand.undo(model);

        assertEquals(model, expectedModel);
        assertEquals(undoResult.getFeedbackToUser(), expectedMessage);
    }

    @Test
    public void undo_commandNotExecuted_throwsAssertionError() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand clearCommand = new ClearCommand();
        assertThrows(AssertionError.class, Command.MESSAGE_NOT_EXECUTED_ERROR, () -> clearCommand.undo(model));
    }
}
