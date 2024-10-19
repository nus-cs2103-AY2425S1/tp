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
}

