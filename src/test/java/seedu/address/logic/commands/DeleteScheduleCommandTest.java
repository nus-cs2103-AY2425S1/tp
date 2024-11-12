package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Meeting;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteScheduleCommandTest {

    private Model modelWithSchedule = new ModelManager(getTypicalAddressBook(),
            new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_validIndexFullSchedule_success() {
        Meeting meetingToDelete = modelWithSchedule.getWeeklySchedule().get(INDEX_FIRST_MEETING.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_MEETING);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS,
                Messages.formatMeetings(meetingToDelete));

        ModelManager expectedModel = new ModelManager(modelWithSchedule.getAddressBook(),
                new UserPrefs(), getTypicalMeetings());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandSuccess(deleteScheduleCommand, modelWithSchedule, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFullSchedule_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(modelWithSchedule.getWeeklySchedule().size() + 1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, modelWithSchedule, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexWeeklySchedule_success() {
        showMeetingAtIndex(modelWithSchedule, INDEX_FIRST_MEETING);

        Meeting meetingToDelete = modelWithSchedule.getWeeklySchedule().get(INDEX_FIRST_MEETING.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_MEETING);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS,
                Messages.formatMeetings(meetingToDelete));

        Model expectedModel = new ModelManager(modelWithSchedule.getAddressBook(),
                new UserPrefs(), modelWithSchedule.getScheduleList());
        expectedModel.deleteMeeting(meetingToDelete);
        showNoMeeting(expectedModel);

        assertCommandSuccess(deleteScheduleCommand, modelWithSchedule, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexWeeklySchedule_throwsCommandException() {
        showMeetingAtIndex(modelWithSchedule, INDEX_FIRST_MEETING);

        Index outOfBoundIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < modelWithSchedule.getScheduleList().getMeetingList().size());

        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, modelWithSchedule, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstCommand = new DeleteScheduleCommand(INDEX_FIRST_MEETING);
        DeleteScheduleCommand deleteSecondCommand = new DeleteScheduleCommand(INDEX_SECOND_MEETING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_MEETING);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different meeting -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(targetIndex);
        String expected = DeleteScheduleCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteScheduleCommand.toString());
    }

    /**
     * Updates {@code model}'s weekly schedule to show no meeting.
     */
    private void showNoMeeting(Model model) {
        model.changeWeeklySchedule(p -> false);

        assertTrue(model.getWeeklySchedule().isEmpty());
    }
}
