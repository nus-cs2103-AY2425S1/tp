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
 * Contains integration tests (interaction with the Model) for {@code UndoCommand}.
 */
public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undo_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(CommandException.class, UndoCommand.MESSAGE_UNDO_FAILURE, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_undo_success() throws CommandException {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        deleteCommand.execute(model);
        model.saveAddressBookVersion();

        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        UndoCommand undoFirstCommand = new UndoCommand();
        UndoCommand undoSecondCommand = new UndoCommand();

        assertTrue(undoFirstCommand.equals(undoFirstCommand));
        assertTrue(undoFirstCommand.equals(undoSecondCommand));

        assertFalse(undoFirstCommand.equals(null));
    }
}
