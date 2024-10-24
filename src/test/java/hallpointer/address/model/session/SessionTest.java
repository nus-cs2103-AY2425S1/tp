package hallpointer.address.model.session;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalSessions.MEETING;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.point.Point;
import hallpointer.address.testutil.SessionBuilder;

public class SessionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        SessionName validSessionName = new SessionName("Valid Session");
        SessionDate validDate = new SessionDate("24 Sep 2024");
        Point validPoints = new Point("10");

        assertThrows(NullPointerException.class, () -> new Session(null, validDate, validPoints));
        assertThrows(NullPointerException.class, () -> new Session(validSessionName, null, validPoints));
        assertThrows(NullPointerException.class, () -> new Session(validSessionName, validDate, null));
        assertThrows(NullPointerException.class, () -> new Session(null, null, null));
    }

    @Test
    public void isSameSession() {
        Session session = new SessionBuilder(REHEARSAL).build();

        // null -> returns false
        assertFalse(session.isSameSession(null));

        // same object -> returns true
        assertTrue(session.isSameSession(session));

        // same name, all other attributes different -> returns true
        Session updatedSession = new SessionBuilder(REHEARSAL)
                .withDate("20 Jan 2025").withPoints("123").build();
        assertTrue(session.isSameSession(updatedSession));

        // different name, all other attributes same -> returns false
        updatedSession = new SessionBuilder(REHEARSAL)
                .withSessionName("Meeting").build();
        assertFalse(session.isSameSession(updatedSession));

        // name differs in case, all other attributes different -> returns true
        updatedSession = new SessionBuilder(REHEARSAL)
                .withSessionName("reHEARSAL W1").withDate("20 Jan 2025").withPoints("123").build();
        assertTrue(session.isSameSession(updatedSession));
    }

    @Test
    public void equals() {
        Session sessionCopy = new SessionBuilder(MEETING).build();

        // same values -> returns true
        assertTrue(MEETING.equals(sessionCopy));

        // same object -> returns true
        assertTrue(MEETING.equals(MEETING));

        // null -> returns false
        assertFalse(MEETING.equals(null));

        // different types -> returns false
        assertFalse(MEETING.equals(5.0f));

        // different session name -> returns false
        Session updatedSession = new SessionBuilder(MEETING).withSessionName("Meeting W3").build();
        assertFalse(MEETING.equals(updatedSession));

        // different dates -> returns false
        updatedSession = new SessionBuilder(MEETING).withDate("01 Aug 2020").build();
        assertFalse(MEETING.equals(updatedSession));

        // different points -> returns false
        updatedSession = new SessionBuilder(MEETING).withPoints("0").build();
        assertFalse(MEETING.equals(updatedSession));
    }

    @Test
    public void hashCode_remainsConsistentAcrossCalls() {
        Session session = new SessionBuilder(MEETING).build();

        // Ensure that hash code remains consistent for the same object across calls
        int initialHashCode = session.hashCode();
        assertTrue(initialHashCode == session.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Session.class.getCanonicalName() + "{sessionName=" + MEETING.getSessionName()
                + ", date=" + MEETING.getDate() + ", points=" + MEETING.getPoints() + "}";
        assertEquals(expected, MEETING.toString());
    }
}
