package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Optional;

public class EventTest {

    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        Name name = new Name("Meeting");
        TimeRange timeRange = new TimeRange(LocalDateTime.now());
        Location location = new Location("Conference Room");
        Description description = Description.ofNullable("Discuss project");

        // Check if each field being null throws NullPointerException
        assertThrows(NullPointerException.class, () -> new Event(null, timeRange, location, description));
        assertThrows(NullPointerException.class, () -> new Event(name, null, location, description));
        assertThrows(NullPointerException.class, () -> new Event(name, timeRange, null, description));
        assertThrows(NullPointerException.class, () -> new Event(name, timeRange, location, null));
    }

    @Test
    public void eventConstructor_validValues_createsEvent() {
        Name name = new Name("Conference");
        TimeRange timeRange = new TimeRange(LocalDateTime.of(2024, 10, 10, 14, 0), Optional.of(LocalDateTime.of(2024, 10, 10, 16, 0)));
        Location location = new Location("Main Hall");
        Description description = Description.ofNullable("Annual tech conference");

        Event event = new Event(name, timeRange, location, description);

        // Validate that the Event object is created with the correct values
        assertEquals(name, event.getName());
        assertEquals(timeRange, event.getTimeRange());
        assertEquals(location, event.getLocation());
        assertEquals(description, event.getDescription());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Name name = new Name("Workshop");
        TimeRange timeRange = new TimeRange(LocalDateTime.now());
        Location location = new Location("Room 101");
        Description description = Description.ofNullable("Technical workshop");

        Event event = new Event(name, timeRange, location, description);

        // Validate that an object equals itself
        assertTrue(event.equals(event));
    }

    @Test
    public void equals_differentObjectsSameValues_returnsTrue() {
        Name name = new Name("Workshop");
        TimeRange timeRange = new TimeRange(LocalDateTime.now());
        Location location = new Location("Room 101");
        Description description = Description.ofNullable("Technical workshop");

        Event event1 = new Event(name, timeRange, location, description);
        Event event2 = new Event(name, timeRange, location, description);

        // Validate that two different Event objects with the same values are equal
        assertTrue(event1.equals(event2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Name name1 = new Name("Workshop");
        TimeRange timeRange1 = new TimeRange(LocalDateTime.now());
        Location location1 = new Location("Room 101");
        Description description1 = Description.ofNullable("Technical workshop");

        Name name2 = new Name("Seminar");
        TimeRange timeRange2 = new TimeRange(LocalDateTime.now().plusDays(1)); // different time
        Location location2 = new Location("Room 202"); // different location
        Description description2 = Description.ofNullable("Educational seminar"); // different description

        Event event1 = new Event(name1, timeRange1, location1, description1);
        Event event2 = new Event(name2, timeRange2, location2, description2);

        // Validate that two Event objects with different values are not equal
        assertFalse(event1.equals(event2));
    }

    @Test
    public void hashCode_consistencyWithEquals() {
        Name name = new Name("Workshop");
        TimeRange timeRange = new TimeRange(LocalDateTime.now());
        Location location = new Location("Room 101");
        Description description = Description.ofNullable("Technical workshop");

        Event event1 = new Event(name, timeRange, location, description);
        Event event2 = new Event(name, timeRange, location, description);

        // Check that if two objects are equal, their hashCodes are also equal
        assertTrue(event1.equals(event2));
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void toString_checkFormat() {
        Name name = new Name("Conference");
        TimeRange timeRange = new TimeRange(LocalDateTime.of(2024, 10, 10, 14, 0), Optional.of(LocalDateTime.of(2024, 10, 10, 16, 0)));
        Location location = new Location("Main Hall");
        Description description = Description.ofNullable("Annual tech conference");

        Event event = new Event(name, timeRange, location, description);

        String expected = Event.class.getCanonicalName() + "{name=Conference, timeRange=Start: 2024-10-10T14:00, End: 2024-10-10T16:00, location=Main Hall, description=Annual tech conference}";
        assertEquals(expected, event.toString());
    }
}

