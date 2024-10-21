package seedu.address.model.event;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void isValidEvent_validEvent_returnsTrue() {
        assertTrue(Event.isValidEvent("Birthday Party"));
    }

    @Test
    public void isValidEvent_blankEvent_returnsFalse() {
        assertFalse(Event.isValidEvent(""));
    }

    @Test
    void isValidEvent_nonBlankEvent_returnsTrue() {
        assertTrue(Event.isValidEvent("Conference"));
    }

    @Test
    void equals_sameEvent_returnsTrue() {
        Event event1 = new Event("Meeting");
        Event event2 = new Event("Meeting");
        assertTrue(event1.equals(event2));
    }

    @Test
    void equals_differentEvent_returnsFalse() {
        Event event1 = new Event("Meeting");
        Event event2 = new Event("Conference");
        assertFalse(event1.equals(event2));
    }

    @Test
    void toString_validEvent_returnsEventString() {
        Event event = new Event("Meeting");
        assertTrue(event.toString().equals("Meeting"));
    }

    @Test
    void hashCode_sameEvent_returnsSameHashCode() {
        Event event1 = new Event("Meeting");
        Event event2 = new Event("Meeting");
        assertTrue(event1.hashCode() == event2.hashCode());
    }

    @Test
    void hashCode_differentEvent_returnsDifferentHashCode() {
        Event event1 = new Event("Meeting");
        Event event2 = new Event("Conference");
        assertFalse(event1.hashCode() == event2.hashCode());
    }

    public void isValidEvent_eventWithOnlySpaces_returnsFalse() {
        assertFalse(Event.isValidEvent("   "));
    }

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null));
    }

    @Test
    public void constructor_invalidEvent_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event(""));
    }

}
