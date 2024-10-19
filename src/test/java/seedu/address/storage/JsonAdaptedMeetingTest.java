package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMeetings.MEETING_ADMIRALTY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

public class JsonAdaptedMeetingTest {
    private static final String INVALID_MEETINGTITLE = "Meeting @$#%&";
    private static final String INVALID_MEETINGDATE = "123456";

    private static final String VALID_MEETINGTITLE = MEETING_ADMIRALTY.getMeetingTitle().toString();
    private static final String VALID_MEETINGDATE = MEETING_ADMIRALTY.getMeetingTitle().toString();
    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(MEETING_ADMIRALTY);
        assertEquals(MEETING_ADMIRALTY, meeting.toModelType());
    }

    @Test
    public void toModelType_invalidMeetingTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(INVALID_MEETINGTITLE, VALID_MEETINGDATE);
        String expectedMessage = MeetingTitle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null, VALID_MEETINGDATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingTitle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidMeetingDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_MEETINGTITLE, INVALID_MEETINGDATE);
        String expectedMessage = MeetingDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETINGTITLE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }
}
