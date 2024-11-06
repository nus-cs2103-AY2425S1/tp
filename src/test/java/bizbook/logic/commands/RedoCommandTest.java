package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code RedoCommand}.
 */
public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_redo_throwsCommandException() {
        RedoCommand redoCommand = new RedoCommand();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_REDO_FAILURE, () -> redoCommand.execute(model));
    }

    @Test
    public void redoList_clear_afterCommit() throws CommandException {
        RedoCommand redoCommand = new RedoCommand();

        // Here we need to call undo function and then call another function for this test case
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        deleteCommand.execute(model);
        model.saveAddressBookVersion();

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        deleteCommand.execute(model);
        model.saveAddressBookVersion();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_REDO_FAILURE, () -> redoCommand.execute(model));
    }

    @Test
    public void execute_redo_success() throws CommandException {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        deleteCommand.execute(model);
        model.saveAddressBookVersion();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        RedoCommand redoCommand = new RedoCommand();
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_REDO_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RedoCommand redoFirstCommand = new RedoCommand();
        RedoCommand redoSecondCommand = new RedoCommand();

        assertTrue(redoFirstCommand.equals(redoFirstCommand));
        assertTrue(redoFirstCommand.equals(redoSecondCommand));

        assertFalse(redoFirstCommand.equals(null));
    }
}
