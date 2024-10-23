package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DATE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTFOUND_MEETING_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTFOUND_MEETING_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteMeetingCommand}.
 */
public class DeleteMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
            getTypicalClientBook(), getTypicalMeetingBook());

    @Test
    public void execute_validMeetingTitleAndDate_success() {
        MeetingTitle meetingTitle = new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY);
        MeetingDate meetingDate = new MeetingDate(VALID_MEETING_DATE_ADMIRALTY);
        Meeting meetingToDelete = model.getFilteredMeetingList().stream()
                .filter(meeting -> meeting.getMeetingTitle().equals(meetingTitle)
                        && meeting.getMeetingDate().equals(meetingDate))
                .findFirst().orElseThrow(() -> new AssertionError(String.format("Meeting not found: %s %s",
                        VALID_MEETING_TITLE_ADMIRALTY, VALID_MEETING_DATE_ADMIRALTY)));
        DeleteMeetingCommand deleteMeetingCommand =
                new DeleteMeetingCommand(meetingToDelete.getMeetingTitle(), meetingToDelete.getMeetingDate());

        String expectedMessage = String.format(DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(meetingToDelete));

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                getTypicalPropertyBook(), getTypicalClientBook(), getTypicalMeetingBook());
        expectedModel.deleteMeeting(meetingToDelete);

        assertCommandSuccess(deleteMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_meetingTitleNotFound_throwsCommandException() {
        MeetingTitle notFoundMeetingTitle = new MeetingTitle(VALID_NOTFOUND_MEETING_TITLE);
        MeetingDate meetingDate = new MeetingDate(VALID_MEETING_DATE_ADMIRALTY);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(notFoundMeetingTitle, meetingDate);
        assertCommandFailure(deleteMeetingCommand, model,
                String.format("Meeting not found with title: %s and date: %s", notFoundMeetingTitle, meetingDate));
    }

    @Test
    public void execute_meetingDateNotFound_throwsCommandException() {
        MeetingTitle meetingTitle = new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY);
        MeetingDate notFoundMeetingDate = new MeetingDate(VALID_NOTFOUND_MEETING_DATE);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(meetingTitle, notFoundMeetingDate);
        assertCommandFailure(deleteMeetingCommand, model,
                String.format("Meeting not found with title: %s and date: %s", meetingTitle, notFoundMeetingDate));
    }

    @Test
    public void execute_meetingTitleAndDateNotFound_throwsCommandException() {
        MeetingTitle notFoundMeetingTitle = new MeetingTitle(VALID_NOTFOUND_MEETING_TITLE);
        MeetingDate notFoundMeetingDate = new MeetingDate(VALID_NOTFOUND_MEETING_DATE);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(notFoundMeetingTitle, notFoundMeetingDate);
        assertCommandFailure(deleteMeetingCommand, model,
                String.format("Meeting not found with title: %s and date: %s",
                        notFoundMeetingTitle, notFoundMeetingDate));
    }

    @Test
    public void equals() {
        MeetingTitle meetingTitle1 = new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY);
        MeetingTitle meetingTitle2 = new MeetingTitle("Other Meeting");
        MeetingDate meetingDate1 = new MeetingDate(VALID_MEETING_DATE_ADMIRALTY);
        MeetingDate meetingDate2 = new MeetingDate("02-02-2024");

        DeleteMeetingCommand deleteFirstCommand = new DeleteMeetingCommand(meetingTitle1, meetingDate1);
        DeleteMeetingCommand deleteSecondCommand = new DeleteMeetingCommand(meetingTitle2, meetingDate2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMeetingCommand deleteFirstCommandCopy = new DeleteMeetingCommand(meetingTitle1, meetingDate1);
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
        MeetingTitle meetingTitle = new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY);
        MeetingDate meetingDate = new MeetingDate(VALID_MEETING_DATE_ADMIRALTY);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(meetingTitle, meetingDate);
        String expected = DeleteMeetingCommand.class.getCanonicalName()
                + "{meetingTitle=" + meetingTitle + ", meetingDate=" + meetingDate + "}";
        assertEquals(expected, deleteMeetingCommand.toString());
    }
}
