package seedu.address.model.event;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.EventNotFoundException;


public class EventListTest {

    private EventList eventList;
    private Event event1;
    private Event event2;
    private Event event3;

    @BeforeEach
    public void setUp() {
        eventList = new EventList();
        event1 = new Event("event1");
        event2 = new Event("event2");
        event3 = new Event("event3");
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        eventList.add(event1);
        Assertions.assertTrue(eventList.contains(event1));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        Assertions.assertFalse(eventList.contains(event1));
    }

    @Test
    public void add_event_success() {
        eventList.add(event1);
        Assertions.assertTrue(eventList.contains(event1));
    }

    @Test
    public void remove_event_success() {
        eventList.add(event1);
        eventList.remove(event1);
        Assertions.assertFalse(eventList.contains(event1));
    }

    @Test
    public void remove_eventNotInList_throwsEventNotFoundException() {
        Assertions.assertThrows(EventNotFoundException.class, () -> eventList.remove(event1));
    }

    @Test
    public void setEvent_existingEvent_success() {
        eventList.add(event1);
        eventList.setEvent(event1, event2);
        Assertions.assertFalse(eventList.contains(event1));
        Assertions.assertTrue(eventList.contains(event2));
    }

    @Test
    public void setEvent_nonExistentEvent_throwsEventNotFoundException() {
        Assertions.assertThrows(EventNotFoundException.class, () -> eventList.setEvent(event1, event2));
    }

    @Test
    public void setEvents_replaceAllEvents_success() {
        List<Event> newEvents = new ArrayList<>();
        newEvents.add(event2);
        newEvents.add(event3);

        eventList.setEvents(newEvents);

        Assertions.assertTrue(eventList.contains(event2));
        Assertions.assertTrue(eventList.contains(event3));
        Assertions.assertFalse(eventList.contains(event1)); // No longer contains event1 since it was replaced

        EventList otherEventList = new EventList();
        otherEventList.add(event2);
        otherEventList.add(event3);
    }

    @Test
    public void setEvents_eventListReplaceAllEvents_success() {
        EventList otherEventList = new EventList();
        otherEventList.add(event2);
        otherEventList.add(event3);

        eventList.setEvents(otherEventList);

        Assertions.assertTrue(eventList.contains(event2));
        Assertions.assertTrue(eventList.contains(event3));
        Assertions.assertFalse(eventList.contains(event1)); // No longer contains event1 since it was replaced

    }

    @Test
    public void setEvents_nullEvent_throwsNullPointerException() {
        List<Event> newEvents = new ArrayList<>();
        newEvents.add(null);
        newEvents.add(event3);
        Assertions.assertThrows(NullPointerException.class, () -> eventList.setEvents(newEvents));

        // Test the case where EventList is null
        Assertions.assertThrows(NullPointerException.class, () -> eventList.setEvents((EventList) null));
    }

    @Test
    public void asUnmodifiableObservableList_returnsCorrectList() {
        eventList.add(event1);
        eventList.add(event2);

        ObservableList<Event> unmodifiableList = eventList.asUnmodifiableObservableList();

        Assertions.assertEquals(FXCollections.unmodifiableObservableList(
                FXCollections.observableArrayList(event1, event2)), unmodifiableList);
    }

    @Test
    public void equals_sameList_returnsTrue() {
        eventList.add(event1);

        Assertions.assertEquals(eventList, eventList);
    }

    @Test
    public void equals_sameListContents_returnsTrue() {
        EventList otherList = new EventList();
        otherList.add(event1);
        eventList.add(event1);

        Assertions.assertEquals(eventList, otherList);
    }

    @Test
    public void equals_differentLength_returnsFalse() {
        EventList otherList = new EventList();
        eventList.add(event1);
        otherList.add(event1);
        otherList.add(event2);

        Assertions.assertNotEquals(eventList, otherList);
    }

    @Test
    public void equals_differentList_returnsFalse() {
        EventList otherList = new EventList();
        otherList.add(event1);
        eventList.add(event2);

        Assertions.assertNotEquals(eventList, otherList);
    }

    @Test
    public void equals_null_returnsFalse() {
        EventList otherList = null;

        Assertions.assertNotEquals(eventList, otherList);
    }

    @Test
    public void hashCode_sameList_returnsSameHashCode() {
        eventList.add(event1);
        EventList otherList = new EventList();
        otherList.add(event1);

        Assertions.assertEquals(eventList.hashCode(), otherList.hashCode());
    }

}
