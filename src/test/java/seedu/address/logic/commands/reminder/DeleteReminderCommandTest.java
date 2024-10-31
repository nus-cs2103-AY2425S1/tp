package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalReminders.getTypicalClientHub;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;


public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Retrieve the reminder to delete based on the first index of the unfiltered list
        Reminder reminderToDelete = model.getDisplayReminders().get(INDEX_FIRST_REMINDER.getZeroBased());

        // Create the DeleteReminderCommand using the index
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        // Expected success message after the reminder is deleted
        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.format(reminderToDelete));

        // Create the expected model and perform the delete operation on it
        ModelManager expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        // Perform the command and verify that the expected model and actual model match
        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayRemindersListSize() + 1);
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        //showReminderAtIndex(model, INDEX_FIRST_REMINDER);
        Reminder reminderToDelete = model.getDisplayReminders().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                  Messages.format(reminderToDelete));

        Model expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        DeleteReminderCommand deleteSecondCommand = new DeleteReminderCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteReminderCommand deleteFirstCommandCopy = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteReminderCommand deleteCommand = new DeleteReminderCommand(targetIndex);
        String expected = DeleteReminderCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
