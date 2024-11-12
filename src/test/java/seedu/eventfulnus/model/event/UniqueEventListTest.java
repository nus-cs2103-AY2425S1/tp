package seedu.eventfulnus.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.model.event.exceptions.DuplicateEventException;
import seedu.eventfulnus.model.event.exceptions.EventNotFoundException;
import seedu.eventfulnus.testutil.TypicalEvents;

public class UniqueEventListTest {
    @Test
    void contains_eventInList_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        uniqueEventList.add(event);
        assertTrue(uniqueEventList.contains(event));
    }

    @Test
    void contains_eventNotInList_returnsFalse() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        assertFalse(uniqueEventList.contains(event));
    }

    @Test
    void add_duplicateEvent_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        uniqueEventList.add(event);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(event));
    }

    @Test
    void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        List<Event> events = List.of(TypicalEvents.CHESS_COM_NUSC, TypicalEvents.CHESS_COM_NUSC);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(events));
    }

    @Test
    void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event target = TypicalEvents.CHESS_COM_NUSC;
        Event editedEvent = TypicalEvents.SWIMMING_M_MED_DEN;
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(target, editedEvent));
    }

    @Test
    void setEvent_editedEventHasDifferentIdentity_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        uniqueEventList.add(event);
        Event editedEvent = TypicalEvents.SWIMMING_M_MED_DEN;
        uniqueEventList.setEvent(event, editedEvent);
        assertTrue(uniqueEventList.contains(editedEvent));
    }

    @Test
    void remove_eventInList_removesEvent() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        uniqueEventList.add(event);
        uniqueEventList.remove(event);
        assertFalse(uniqueEventList.contains(event));
    }

    @Test
    void remove_eventNotInList_throwsEventNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        Event event = TypicalEvents.CHESS_COM_NUSC;
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(event));
    }
}
