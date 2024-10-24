package seedu.address.model.event;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    void equals_sameObject_returnsTrue() {
        Event event = new Event(new EventName("Event 1"));
        assertEquals(event, event);
    }

    @Test
    void equals_nullObject_returnsFalse() {
        Event event = new Event(new EventName("Event 1"));
        assertNotEquals(null, event);
    }

    @Test
    void equals_differentType_returnsFalse() {
        Event event = new Event(new EventName("Event 1"));
        assertNotEquals("String", event);
    }

    @Test
    void equals_differentEventName_returnsFalse() {
        Event event1 = new Event(new EventName("Event 1"));
        Event event2 = new Event(new EventName("Event 2"));
        assertNotEquals(event1, event2);
    }

    @Test
    void equals_sameEventName_returnsTrue() {
        Event event1 = new Event(new EventName("Event 1"));
        Event event2 = new Event(new EventName("Event 1"));
        assertEquals(event1, event2);
    }

    @Test
    void hashCode_sameEventName_returnsSameHashCode() {
        Event event1 = new Event(new EventName("Event 1"));
        Event event2 = new Event(new EventName("Event 1"));
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void hashCode_differentEventName_returnsDifferentHashCode() {
        Event event1 = new Event(new EventName("Event 1"));
        Event event2 = new Event(new EventName("Event 2"));
        assertNotEquals(event1.hashCode(), event2.hashCode());
    }
}
