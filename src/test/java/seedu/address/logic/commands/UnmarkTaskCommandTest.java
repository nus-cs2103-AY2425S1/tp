package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBookWithTasks;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

public class UnmarkTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithTasks(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS,
                taskToUnmark.getDescription(), taskToUnmark.getPatient().getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        taskToUnmark.markTaskIncomplete();

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_PERSON);

        Task taskToUnmark = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnmarkTaskCommand.MESSAGE_UNMARK_TASK_SUCCESS,
                taskToUnmark.getDescription(), taskToUnmark.getPatient().getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        taskToUnmark.markTaskIncomplete();
        showNoTask(expectedModel);

        assertCommandSuccess(unmarkTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(unmarkTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnmarkTaskCommand unmarkFirstCommand = new UnmarkTaskCommand(INDEX_FIRST_PERSON);
        UnmarkTaskCommand unmarkSecondCommand = new UnmarkTaskCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkTaskCommand unmarkFirstCommandCopy = new UnmarkTaskCommand(INDEX_FIRST_PERSON);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnmarkTaskCommand unmarkTaskCommand = new UnmarkTaskCommand(targetIndex);
        String expected = UnmarkTaskCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unmarkTaskCommand.toString());
    }

    private void showNoTask(Model model) {
        model.updateFilteredTaskList(t -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
