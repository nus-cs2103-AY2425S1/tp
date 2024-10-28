package hallpointer.address.storage;

import static hallpointer.address.storage.JsonAdaptedMember.MISSING_FIELD_MESSAGE_FORMAT;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.exceptions.IllegalValueException;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;

public class JsonAdaptedSessionTest {
    private static final String INVALID_SESSION_NAME = "AGM #2";
    private static final String INVALID_DATE = "99 Dec 0000";

    // Valid values
    private static final String VALID_SESSION_NAME = REHEARSAL.getSessionName().toString();
    private static final String VALID_DATE = REHEARSAL.getDate().toString();
    private static final JsonAdaptedPoint VALID_POINTS = new JsonAdaptedPoint(REHEARSAL.getPoints());

    @Test
    public void toModelType_validSessionDetails_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(REHEARSAL);
        assertEquals(REHEARSAL, session.toModelType());
    }

    @Test
    public void toModelType_invalidSessionName_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(INVALID_SESSION_NAME, VALID_DATE, VALID_POINTS);
        String expectedMessage = SessionName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullSessionName_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(null, VALID_DATE, VALID_POINTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SessionName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_SESSION_NAME, INVALID_DATE, VALID_POINTS);
        String expectedMessage = SessionDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_SESSION_NAME, null, VALID_POINTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SessionDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullPoints_throwsIllegalValueException() {
        JsonAdaptedSession session =
                new JsonAdaptedSession(VALID_SESSION_NAME, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Point.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }
}
