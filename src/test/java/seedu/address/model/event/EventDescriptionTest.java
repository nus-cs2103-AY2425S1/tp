package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventDescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventDescription(null));
    }

    @Test
    public void equals() {
        EventDescription eventDescription = new EventDescription("Valid Event Description");

        // same values -> returns true
        assertTrue(eventDescription.equals(new EventDescription("Valid Event Description")));

        // same object -> returns true
        assertTrue(eventDescription.equals(eventDescription));

        // null -> returns false
        assertFalse(eventDescription.equals(null));

        // different types -> returns false
        assertFalse(eventDescription.equals(5.0f));

        // different values -> returns false
        assertFalse(eventDescription.equals(new EventDescription("Other Valid Event Description")));
    }
}
