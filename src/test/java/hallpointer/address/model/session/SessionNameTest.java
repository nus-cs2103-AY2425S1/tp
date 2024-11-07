package hallpointer.address.model.session;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SessionNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SessionName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new SessionName(invalidName));
    }

    @Test
    public void isValidSessionName() {
        // null name
        assertThrows(NullPointerException.class, () -> SessionName.isValidSessionName(null));

        // invalid names
        assertFalse(SessionName.isValidSessionName("")); // empty string
        assertFalse(SessionName.isValidSessionName(" ")); // spaces only
        assertFalse(SessionName.isValidSessionName("^")); // only non-alphanumeric characters
        assertFalse(SessionName.isValidSessionName("session*")); // contains non-alphanumeric characters
        assertFalse(SessionName.isValidSessionName("session/2")); // slashes not allowed either


        // valid names
        assertTrue(SessionName.isValidSessionName("Math Workshop")); // alphabets and spaces only
        assertTrue(SessionName.isValidSessionName("Session 101")); // alphanumeric characters
        assertTrue(SessionName.isValidSessionName("Physics Seminar 2024")); // long valid names
        assertTrue(SessionName.isValidSessionName("2024")); // just numbmers is fine here
    }

    @Test
    public void equals() {
        SessionName sessionName = new SessionName("Valid Session Name");

        // same values -> returns true
        assertTrue(sessionName.equals(new SessionName("Valid Session Name")));

        // same object -> returns true
        assertTrue(sessionName.equals(sessionName));

        // null -> returns false
        assertFalse(sessionName.equals(null));

        // different types -> returns false
        assertFalse(sessionName.equals(5.0f));

        // different values -> returns false
        assertFalse(sessionName.equals(new SessionName("Other Valid Session Name")));
    }
}
