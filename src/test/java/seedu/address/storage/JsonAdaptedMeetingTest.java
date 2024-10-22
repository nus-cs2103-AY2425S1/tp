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
    private static final String INVALID_MEETING_TITLE = "Meeting @$#%&";
    private static final String INVALID_MEETING_DATE = "123456";
    private static final String VALID_BUYER_NAME = "bob";
    private static final String VALID_SELLER_NAME = "alice";
    private static final String VALID_TYPE = "HDB";
    private static final String VALID_POSTAL_CODE = "111111";

    private static final String VALID_MEETING_TITLE = MEETING_ADMIRALTY.getMeetingTitle().toString();
    private static final String VALID_MEETING_DATE = MEETING_ADMIRALTY.getMeetingTitle().toString();
    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(MEETING_ADMIRALTY);
        assertEquals(MEETING_ADMIRALTY, meeting.toModelType());
    }

    @Test
    public void toModelType_invalidMeetingTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(INVALID_MEETING_TITLE, VALID_MEETING_DATE, VALID_BUYER_NAME,
                        VALID_SELLER_NAME, VALID_TYPE, VALID_POSTAL_CODE);
        String expectedMessage = MeetingTitle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null, VALID_MEETING_DATE, VALID_BUYER_NAME,
                VALID_SELLER_NAME, VALID_TYPE, VALID_POSTAL_CODE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingTitle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidMeetingDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_MEETING_TITLE, INVALID_MEETING_DATE, VALID_BUYER_NAME,
                        VALID_SELLER_NAME, VALID_TYPE, VALID_POSTAL_CODE);
        String expectedMessage = MeetingDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETING_TITLE, null, VALID_BUYER_NAME,
                VALID_SELLER_NAME, VALID_TYPE, VALID_POSTAL_CODE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }
}
