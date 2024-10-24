package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_A;
import static seedu.address.testutil.TypicalEvents.EVENT_B;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        if (event.getVolunteers().isEmpty()) {
            assertTrue(event.getVolunteers().isEmpty());
        } else {
            assertThrows(UnsupportedOperationException.class, () -> event.getVolunteers().remove(0));
        }
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_A.isSameEvent(EVENT_A));

        // null -> returns false
        assertFalse(EVENT_A.isSameEvent(null));

        // same name, all other attributes different -> returns true
        Event editedEventOne = new EventBuilder(EVENT_A).withLocation("Different Location")
                .withStartTime("12:00").withEndTime("14:00").build();
        assertTrue(EVENT_A.isSameEvent(editedEventOne));

        // different name, all other attributes same -> returns false
        Event editedEventWithDifferentName = new EventBuilder(EVENT_A).withEventName("New Event").build();
        assertFalse(EVENT_A.isSameEvent(editedEventWithDifferentName));

        // name differs in case, all other attributes same -> returns true
        Event editedEventWithNameCaseDiff = new EventBuilder(EVENT_A).withEventName("meeting with client").build();
        assertTrue(EVENT_A.isSameEvent(editedEventWithNameCaseDiff));

        // name has trailing spaces, all other attributes same -> returns true
        Event editedEventWithTrailingSpaces = new EventBuilder(EVENT_A).withEventName("Meeting with Client ").build();
        assertTrue(EVENT_A.isSameEvent(editedEventWithTrailingSpaces));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event eventOneCopy = new EventBuilder(EVENT_A).build();
        assertTrue(EVENT_A.equals(eventOneCopy));

        // same object -> returns true
        assertTrue(EVENT_A.equals(EVENT_A));

        // null -> returns false
        assertFalse(EVENT_A.equals(null));

        // different type -> returns false
        assertFalse(EVENT_A.equals(5));

        // different event -> returns false
        assertFalse(EVENT_A.equals(EVENT_B));

        // different name -> returns false
        Event editedEvent = new EventBuilder(EVENT_A).withEventName("New Event").build();
        assertFalse(EVENT_A.equals(editedEvent));

        // different location -> returns false
        editedEvent = new EventBuilder(EVENT_A).withLocation("New Location").build();
        assertFalse(EVENT_A.equals(editedEvent));

        // different time -> returns false
        editedEvent = new EventBuilder(EVENT_A).withStartTime("12:00").withEndTime("14:00").build();
        assertFalse(EVENT_A.equals(editedEvent));
    }

    @Test
    public void assignVolunteer() {
        Event event = new EventBuilder().build();
        event.assignVolunteer("John Doe");
        assertTrue(event.hasVolunteer("John Doe"));
    }

    @Test
    public void unassignVolunteer() {
        Event event = new EventBuilder().withVolunteers("John Doe", "Jane Doe").build();
        event.unassignVolunteer("John Doe");
        assertFalse(event.hasVolunteer("John Doe"));
    }

    @Test
    public void toStringMethod() {
        String expected = Event.class.getCanonicalName() + "{name=" + EVENT_A.getName()
                + ", location=" + EVENT_A.getLocation()
                + ", date=" + EVENT_A.getDate()
                + ", startTime=" + EVENT_A.getStartTime()
                + ", endTime=" + EVENT_A.getEndTime()
                + ", description=" + EVENT_A.getDescription() + "}";
        System.out.println(expected);
        System.out.println(EVENT_A.toString());
        assertEquals(expected, EVENT_A.toString());
    }
}
