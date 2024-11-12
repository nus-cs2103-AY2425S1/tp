package seedu.eventfulnus.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventNameTest {
    @Test
    public void isValidEvent_validEvent_returnsTrue() {
        assertTrue(EventName.isValidEventName("Birthday Party"));
    }

    @Test
    public void isValidEvent_blankEvent_returnsFalse() {
        assertFalse(EventName.isValidEventName(""));
    }

    @Test
    void equals_sameEvent_returnsTrue() {
        EventName event1 = new EventName("Meeting");
        EventName event2 = new EventName("Meeting");
        assertEquals(event1, event2);
    }

    @Test
    void equals_differentEvent_returnsFalse() {
        EventName event1 = new EventName("Meeting");
        EventName event2 = new EventName("Conference");
        assertNotEquals(event1, event2);
    }

    @Test
    void toString_validEvent_returnsEventString() {
        EventName eventName = new EventName("Meeting");
        assertEquals("Meeting", eventName.toString());
    }

    @Test
    void hashCode_sameEvent_returnsSameHashCode() {
        EventName event1 = new EventName("Meeting");
        EventName event2 = new EventName("Meeting");
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void hashCode_differentEvent_returnsDifferentHashCode() {
        EventName event1 = new EventName("Meeting");
        EventName event2 = new EventName("Conference");
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void isValidEvent_eventWithOnlySpaces_returnsFalse() {
        assertFalse(EventName.isValidEventName("   "));
    }
}
