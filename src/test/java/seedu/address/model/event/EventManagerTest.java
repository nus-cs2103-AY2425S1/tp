package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.EventNotFoundException;

public class EventManagerTest {

    private EventManager eventManager;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    public void setUp() {
        eventManager = new EventManager();
        event1 = new Event("event1");
        event2 = new Event("event2");
    }

    @Test
    public void addEvent_eventAddedSuccessfully() {
        eventManager.addEvent(event1);
        assertTrue(eventManager.getEventList().contains(event1));
    }

    @Test
    public void removeEvent_eventRemovedSuccessfully() {
        eventManager.addEvent(event1);
        eventManager.removeEvent(event1);
        assertFalse(eventManager.getEventList().contains(event1));
    }

    @Test
    public void removeEvent_eventNotInList_throwsException() {
        assertThrows(EventNotFoundException.class, () -> eventManager.removeEvent(event1));
    }

    @Test
    public void setEvent_existingEvent_replacesSuccessfully() {
        eventManager.addEvent(event1);
        eventManager.setEvent(event1, event2);

        ObservableList<Event> eventList = eventManager.getEventList();
        assertFalse(eventList.contains(event1));
        assertTrue(eventList.contains(event2));
    }

    @Test
    public void setEvent_eventNotFound_throwsException() {
        assertThrows(EventNotFoundException.class, () -> eventManager.setEvent(event1, event2));
    }

    @Test
    public void getEventList_returnsUnmodifiableList() {
        eventManager.addEvent(event1);
        ObservableList<Event> unmodifiableList = eventManager.getEventList();

        assertEquals(1, unmodifiableList.size());
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(event2));
        // Ensure it's unmodifiable
    }

    @Test
    public void getEventList_initialEmptyList() {
        ObservableList<Event> eventList = eventManager.getEventList();
        assertTrue(eventList.isEmpty());
    }

    @Test
    public void hasEvent_newEvent_returnsFalse() {
        event3 = new Event("event3");
        eventManager.addEvent(event3);
        assertFalse(this.eventManager.hasEvent(new Event("event4")));
    }

    @Test
    public void hasEvent_existingEvent_returnsTrue() {
        event3 = new Event("event3");
        eventManager.addEvent(event3);
        assertTrue(this.eventManager.hasEvent(new Event("event3")));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Test where other == this
        assertTrue(eventManager.equals(eventManager));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // Test where other is null
        assertFalse(eventManager.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        // Test where other is not an instance of EventManager
        assertFalse(eventManager.equals("not an event manager"));
    }

    @Test
    public void equals_sameContent_returnsTrue() {
        // Test where two EventManager objects have the same content
        EventManager anotherEventManager = new EventManager();
        anotherEventManager.addEvent(event1);
        eventManager.addEvent(event1);
        assertTrue(eventManager.equals(anotherEventManager));
    }

    @Test
    public void equals_differentContent_returnsFalse() {
        // Test where two EventManager objects have different content
        EventManager anotherEventManager = new EventManager();
        anotherEventManager.addEvent(event2);
        eventManager.addEvent(event1);
        assertFalse(eventManager.equals(anotherEventManager));
    }

    @Test
    public void getPersonInEventPredicate_eventNonExistent_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> eventManager.getPersonInEventPredicate(event1));
    }
}

