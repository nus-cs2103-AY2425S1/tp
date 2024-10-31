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
        String invalidEventName = "";
        assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null event name
        assertThrows(NullPointerException.class, () -> EventName.isValidName(null));

        // invalid event name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only

        // valid event name
        assertTrue(EventName.isValidName("peter jack")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidName("Capital Tan")); // with capital letters
        assertTrue(EventName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        EventName eventName = new EventName("Valid Event Name");

        // same values -> returns true
        assertTrue(eventName.equals(new EventName("Valid Event Name")));

        // same object -> returns true
        assertTrue(eventName.equals(eventName));

        // null -> returns false
        assertFalse(eventName.equals(null));

        // different types -> returns false
        assertFalse(eventName.equals(5.0f));

        // different values -> returns false
        assertFalse(eventName.equals(new EventName("Other Valid Event Name")));
    }

    @Test
    public void equalsLowerCase() {
        EventName eventName = new EventName("Valid Event Name");

        // same values -> returns true
        assertTrue(eventName.equalsLowerCase(new EventName("Valid Event Name")));

        // same values (case-insensitive) -> returns true
        assertTrue(eventName.equalsLowerCase(new EventName("Valid Event Name".toLowerCase())));
        assertTrue(eventName.equalsLowerCase(new EventName("Valid Event Name".toUpperCase())));

        // same object -> returns true
        assertTrue(eventName.equalsLowerCase(eventName));

        // null -> returns false
        assertFalse(eventName.equalsLowerCase(null));

        // different types -> returns false
        assertFalse(eventName.equalsLowerCase(5.0f));

        // different values -> returns false
        assertFalse(eventName.equalsLowerCase(new EventName("Other Valid Event Name")));
    }
}
