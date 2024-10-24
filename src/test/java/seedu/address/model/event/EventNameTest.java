package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        // invalid names
        assertThrows(IllegalArgumentException.class, () -> new EventName("")); // empty string
        assertThrows(IllegalArgumentException.class, () -> new EventName(" ")); // spaces only
        assertThrows(IllegalArgumentException.class, () -> new EventName("a ".repeat(51))); // > than 50 char
    }

    @Test
    public void isValidName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EventName.isValidName(null));
    }

    @Test
    public void isValidName_invalidEventNames_returnsFalse() {
        // invalid names
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("a ".repeat(51))); // 51 characters after space inclusion
        assertFalse(EventName.isValidName("Event-Name-1")); // contains non-alphanumeric character (dash)
        assertFalse(EventName.isValidName("Event.Name")); // contains punctuation (dot)
    }

    @Test
    public void isValidName_validEventNames_returnsTrue() {
        // valid names
        assertTrue(EventName.isValidName(" a")); // leading space
        assertTrue(EventName.isValidName("ValidEvent")); // single word
        assertTrue(EventName.isValidName("Valid Event Name")); // multiple words, spaces allowed
        assertTrue(EventName.isValidName("Valid Event123")); // alphanumeric with spaces
        assertTrue(EventName.isValidName("a".repeat(50))); // exactly 50 characters
        assertTrue(EventName.isValidName("Event123 with spaces")); // alphanumeric + spaces
    }

    @Test
    public void equals() {
        EventName name1 = new EventName("Event 1");
        EventName name2 = new EventName("Event 1");
        EventName name3 = new EventName("Different Event");

        // same values -> returns true
        assertTrue(name1.equals(name2));

        // same object -> returns true
        assertTrue(name1.equals(name1));

        // null -> returns false
        assertFalse(name1.equals(null));

        // different types -> returns false
        assertFalse(name1.equals(5.0f));

        // different values -> returns false
        assertFalse(name1.equals(name3));
    }
}
