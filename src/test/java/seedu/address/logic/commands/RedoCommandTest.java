package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailureWithoutModel;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());

    @Test
    public void execute_emptyFutureAndHistoryStack_failure() {
        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_FAILURE);
    }

    @Test
    public void execute_emptyFutureStack_failure() {
        model.saveCurrentCampusConnect();
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(person);
        assertCommandFailure(new RedoCommand(), model, Messages.MESSAGE_REDO_FAILURE);
    }

    @Test
    public void execute_nonEmptyFutureStack_success() {
        ModelManager expectedModel = new ModelManager(model.getCampusConnect(), new UserPrefs());
        UndoCommand undoCommand = new UndoCommand();
        String expectedUndoMessage = UndoCommand.MESSAGE_SUCCESS;
        String expectedRedoMessage = RedoCommand.MESSAGE_SUCCESS;

        model.saveCurrentCampusConnect();
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(person);

        ModelManager expectedRedoneModel = new ModelManager(model.getCampusConnect(), new UserPrefs());

        assertCommandSuccess(undoCommand, model, expectedUndoMessage, expectedModel);
        assertCommandSuccess(new RedoCommand(), model, expectedRedoMessage, expectedRedoneModel);
    }
}
