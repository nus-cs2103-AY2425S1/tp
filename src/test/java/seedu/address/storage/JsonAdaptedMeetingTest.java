package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedMeetingTest {
    private static final String INVALID_UID = "1234";
    private static final String INVALID_DATE = "10-10-2024";
    private static final String INVALID_TIME = "10-00";

    private static final String VALID_UID = "12345678-1234-1234-1234-123456789012";
    private static final String VALID_MEETING_NAME = "Team Sync";
    private static final String VALID_DATE = "2024-10-10";
    private static final String VALID_TIME = "10:00";

    @Test
    public void toModelType_invalidUid_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(INVALID_UID,
                VALID_MEETING_NAME, VALID_DATE, VALID_TIME);
        String expectedMessage = JsonAdaptedMeeting.UID_FORMAT_ERROR;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullUid_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null,
                VALID_MEETING_NAME, VALID_DATE, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                "contact indexes");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingName_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_UID,
                null, VALID_DATE, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
            "meeting name");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_UID,
                VALID_MEETING_NAME, INVALID_DATE, VALID_TIME);
        String expectedMessage = JsonAdaptedMeeting.DATE_TIME_FORMAT_ERROR;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_UID,
                VALID_MEETING_NAME, null, VALID_TIME);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                "meeting date");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_UID,
                VALID_MEETING_NAME, VALID_DATE, INVALID_TIME);
        String expectedMessage = JsonAdaptedMeeting.DATE_TIME_FORMAT_ERROR;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_UID,
                VALID_MEETING_NAME, VALID_DATE, null);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                "meeting time");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }
}
