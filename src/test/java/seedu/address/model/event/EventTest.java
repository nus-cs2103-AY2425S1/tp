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

    @Test
    public void equals_sameEvent_returnsTrue() {
        Event event = new Event("Meeting");
        assertEquals(event, new Event("Meeting"));
    }

    @Test
    public void equals_differentEvent_returnsFalse() {
        Event event = new Event("Meeting");
        assertNotEquals(event, new Event("Conference"));
    }

    @Test
    public void hashCode_sameEvent_returnsSameHashCode() {
        Event event = new Event("Meeting");
        assertEquals(event.hashCode(), new Event("Meeting").hashCode());
    }

    @Test
    public void hashCode_differentEvent_returnsDifferentHashCode() {
        Event event = new Event("Meeting");
        assertNotEquals(event.hashCode(), new Event("Conference").hashCode());
    }
}
