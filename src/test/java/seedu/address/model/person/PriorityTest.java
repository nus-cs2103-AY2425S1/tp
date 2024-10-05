package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid Priorities
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("test")); // not lOW MEDIUM or HIGH


        // valid Priorities
        assertTrue(Priority.isValidPriority("LOW"));
        assertTrue(Priority.isValidPriority("MEDIUM"));
        assertTrue(Priority.isValidPriority("HIGH"));
    }

    @Test
    public void equals() {
        Priority priority = new Priority("LOW");

        // same values -> returns true
        assertTrue(priority.equals(new Priority("LOW")));

        // same object -> returns true
        assertTrue(priority.equals(priority));

        // null -> returns false
        assertFalse(priority.equals(null));

        // different types -> returns false
        assertFalse(priority.equals(5.0f));

        // different values -> returns false
        assertFalse(priority.equals(new Priority("HIGH")));
    }
}
