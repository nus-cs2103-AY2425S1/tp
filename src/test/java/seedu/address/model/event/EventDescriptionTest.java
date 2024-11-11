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
    public void constructor_invalidEventDescription_throwsIllegalArgumentException() {
        String invalidEventDescription = " ";
        assertThrows(IllegalArgumentException.class, () -> new EventDescription(invalidEventDescription));
    }

    @Test
    public void isValidEventDescription() {
        // null event description
        assertThrows(NullPointerException.class, () -> EventDescription.isValidDescription(null));

        // invalid event description
        assertFalse(EventDescription.isValidDescription("")); // empty string
        assertFalse(EventDescription.isValidDescription(" ")); // spaces only

        // valid event description
        assertTrue(EventDescription.isValidDescription("afternoon meeting")); // alphabets only
        assertTrue(EventDescription.isValidDescription("12345")); // numbers only
        assertTrue(EventDescription.isValidDescription("cs2103t lecture")); // alphanumeric characters
        assertTrue(EventDescription.isValidDescription("CS2103T Project Meeting")); // with capital letters
        assertTrue(EventDescription.isValidDescription(
            "CS2103T Project Weekly Meeting on 14th October 2024 from 4pm to 6pm")); // long names
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
