package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNDO_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
    @Test
    public void execute_emptyHistoryStack_failure() {
        ModelManager expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, expectedModel, MESSAGE_UNDO_FAILURE);
    }

    @Test
    public void execute_nonEmptyHistoryStack_success() {
        ModelManager expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        model.saveCurrentCampusConnect();
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(person);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UndoCommand u1 = new UndoCommand();
        UndoCommand u2 = new UndoCommand();

        assertTrue(u1.equals(u1));
        assertTrue(u1.equals(u2));
        assertFalse(u1.equals(null));
    }
}
