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

public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithTasks(), new UserPrefs());


    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                taskToDelete.getDescription(), taskToDelete.getPatient().getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_PERSON);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS,
                taskToDelete.getDescription(), taskToDelete.getPatient().getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(targetIndex);
        String expected = DeleteTaskCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTaskCommand.toString());
    }

    private void showNoTask(Model model) {
        model.updateFilteredTaskList(t -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
