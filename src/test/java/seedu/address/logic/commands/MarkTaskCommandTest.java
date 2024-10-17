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

public class MarkTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithTasks(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS,
                taskToMark.getDescription(), taskToMark.getPatient().getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        taskToMark.markTaskComplete();

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_PERSON);

        Task taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS,
                taskToMark.getDescription(), taskToMark.getPatient().getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        taskToMark.markTaskComplete();
        showNoTask(expectedModel);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        MarkTaskCommand markTaskCommand = new MarkTaskCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkTaskCommand markFirstCommand = new MarkTaskCommand(INDEX_FIRST_PERSON);
        MarkTaskCommand markSecondCommand = new MarkTaskCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkTaskCommand markFirstCommandCopy = new MarkTaskCommand(INDEX_FIRST_PERSON);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(targetIndex);
        String expected = MarkTaskCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markTaskCommand.toString());
    }

    private void showNoTask(Model model) {
        model.updateFilteredTaskList(t -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
