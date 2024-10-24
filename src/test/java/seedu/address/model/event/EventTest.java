package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.id.UniqueId;

public class EventTest {

    private final Event testEvent = new Event(new Name("Test Event"), new Date("2024-10-11"));
    private final Event similarTestEvent = new Event(new Name("Test Event"), new Date("2023-05-20"));
    private final Event differentEvent = new Event(new Name("Different"), new Date("2020-06-01"));

    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        Name name = new Name("Meeting");
        Date date = new Date("2024-10-10");

        // Check if each field being null throws NullPointerException
        assertThrows(NullPointerException.class, () -> new Event(null, date));
        assertThrows(NullPointerException.class, () -> new Event(name, null));
    }

    @Test
    public void eventConstructor_validValues_createsEvent() {
        Name name = new Name("Conference");
        Date date = new Date("2024-10-10");

        Event event = new Event(name, date);

        // Validate that the Event object is created with the correct values
        assertEquals(name, event.getName());
        assertEquals(date, event.getDate());
    }

    @Test
    public void isSameEvent_nullEvent_returnsFalse() {
        assertFalse(testEvent.isSameEvent(null));
    }

    @Test
    public void isSameEvent_sameEvent_returnsTrue() {
        assertTrue(testEvent.isSameEvent(testEvent));
    }

    @Test
    public void isSameEvent_differentEvent_returnsFalse() {
        assertFalse(testEvent.isSameEvent(differentEvent));
    }

    @Test
    public void isSameEvent_eventWithSameIdentity_returnsTrue() {
        assertTrue(testEvent.isSameEvent(similarTestEvent));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Name name = new Name("Workshop");
        Date date = new Date("2024-10-10");

        Event event = new Event(name, date);

        // Validate that an object equals itself
        assertTrue(event.equals(event));
    }

    @Test
    public void equals_differentObjectsSameValues_returnsTrue() {
        Name name = new Name("Workshop");
        Date date = new Date("2024-10-10");

        // Generate a valid UUID
        UniqueId id = new UniqueId(UUID.randomUUID().toString());
        Event event1 = new Event(id, name, date);
        Event event2 = new Event(id, name, date);

        // Validate that two different Event objects with the same values (including UniqueId) are equal
        assertTrue(event1.equals(event2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Name name1 = new Name("Workshop");
        Date date1 = new Date("2024-10-10");

        Name name2 = new Name("Seminar");
        Date date2 = new Date("2024-11-11"); // different date

        Event event1 = new Event(name1, date1);
        Event event2 = new Event(name2, date2);

        // Validate that two Event objects with different values are not equal
        assertFalse(event1.equals(event2));
    }

    @Test
    public void hashCode_consistencyWithEquals() {
        Name name = new Name("Workshop");
        Date date = new Date("2024-10-10");

        UniqueId id = new UniqueId(UUID.randomUUID().toString());
        Event event1 = new Event(id, name, date);
        Event event2 = new Event(id, name, date);

        // Check that if two objects are equal, their hashCodes are also equal
        assertTrue(event1.equals(event2));
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void toString_checkFormat() {
        Name name = new Name("Conference");
        Date date = new Date("2024-10-10");
        UniqueId id = new UniqueId(UUID.randomUUID().toString());

        Event event = new Event(id, name, date);

        String expected = Event.class.getCanonicalName() + "{id=" + id.toString()
                + ", name=Conference, date=2024-10-10}";
        assertEquals(expected, event.toString());
    }
}

