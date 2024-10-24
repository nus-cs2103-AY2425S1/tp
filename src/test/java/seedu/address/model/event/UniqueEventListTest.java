package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.name.Name;

public class UniqueEventListTest {
    private final UniqueEventList uniqueEventList = new UniqueEventList();
    private final Event testEvent = new Event(new Name("Test Event"), new Date("2024-10-11"));
    private final Event similarTestEvent = new Event(new Name("Test Event"), new Date("2023-05-20"));
    private final Event differentEvent = new Event(new Name("Different"), new Date("2020-06-01"));

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(testEvent));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(testEvent);
        assertTrue(uniqueEventList.contains(testEvent));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(testEvent);
        assertTrue(uniqueEventList.contains(similarTestEvent));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(testEvent);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(testEvent));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(testEvent));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(testEvent);
        uniqueEventList.remove(testEvent);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(testEvent);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(differentEvent);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(testEvent);
        List<Event> eventList = List.of(differentEvent);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(differentEvent);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(testEvent, testEvent);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}
