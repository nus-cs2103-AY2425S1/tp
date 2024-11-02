package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.Assert.assertThrows;

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
    public void execute_Undo_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(CommandException.class, UndoCommand.MESSAGE_UNDO_FAILURE, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_undo_success() {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        try {
            deleteCommand.execute(model);
        } catch (CommandException err) {
            throw new AssertionError("There should not be an error thrown");
        }

        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_UNDO_SUCCESS, expectedModel);
    }
}
