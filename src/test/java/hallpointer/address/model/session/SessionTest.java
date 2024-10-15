package hallpointer.address.model.session;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.point.Point;

public class SessionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Check if constructor throws NullPointerException for null arguments
        SessionName validSessionName = new SessionName("Valid Session");
        SessionDate validDate = new SessionDate("24 Sep 2024");
        Point validPoints = new Point(10);

        assertThrows(NullPointerException.class, () -> new Session(null, validDate, validPoints));
        assertThrows(NullPointerException.class, () -> new Session(validSessionName, null, validPoints));
        assertThrows(NullPointerException.class, () -> new Session(validSessionName, validDate, null));
    }

    @Test
    public void isSameSession() {
        SessionName sessionName = new SessionName("Session 1");
        SessionDate date = new SessionDate("24 Sep 2024");
        Point points = new Point(10);

        Session session = new Session(sessionName, date, points);

        // same session -> returns true
        assertTrue(session.isSameSession(session));

        // different session with same name -> returns true
        Session sameNameSession = new Session(new SessionName("Session 1"), new SessionDate("25 Sep 2024"), points);
        assertTrue(session.isSameSession(sameNameSession));

        // different session with different name -> returns false
        Session differentNameSession = new Session(new SessionName("Session 2"), date, points);
        assertFalse(session.isSameSession(differentNameSession));

        // null -> returns false
        assertFalse(session.isSameSession(null));
    }

    @Test
    public void equals() {
        SessionName sessionName = new SessionName("Session 1");
        SessionDate date = new SessionDate("24 Sep 2024");
        Point points = new Point(10);

        Session session = new Session(sessionName, date, points);

        // same values -> returns true
        assertTrue(session.equals(new Session(sessionName, date, points)));

        // same object -> returns true
        assertTrue(session.equals(session));

        // null -> returns false
        assertFalse(session.equals(null));

        // different types -> returns false
        assertFalse(session.equals(5.0f));

        // different session name -> returns false
        assertFalse(session.equals(new Session(new SessionName("Session 2"), date, points)));

        // different points -> returns false
        assertFalse(session.equals(new Session(sessionName, date, new Point(5))));
    }

    @Test
    public void hashCodeConsistency() {
        SessionName sessionName = new SessionName("Session 1");
        SessionDate date = new SessionDate("24 Sep 2024");
        Point points = new Point(10);

        Session session = new Session(sessionName, date, points);

        // Ensure that hash code remains consistent for the same object
        int initialHashCode = session.hashCode();
        assertTrue(initialHashCode == session.hashCode());
    }

    @Test
    public void toStringTest() {
        SessionName sessionName = new SessionName("Session 1");
        SessionDate date = new SessionDate("24 Sep 2024");
        Point points = new Point(10);

        Session session = new Session(sessionName, date, points);

        String expectedString = "Session{sessionName=Session 1, points=10}";
        assertTrue(session.toString().contains("sessionName=Session 1"));
        assertTrue(session.toString().contains("points=10"));
    }
}
