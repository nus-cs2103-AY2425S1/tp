//package seedu.address.model.event;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.event.exceptions.DuplicateEventException;
//import seedu.address.model.event.exceptions.EventNotFoundException;
//
//public class UniqueEventListTest {
//    @Test
//    void contains_eventInList_returnsTrue() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        assertTrue(uniqueEventList.contains(event));
//    }
//
//    @Test
//    void contains_eventNotInList_returnsFalse() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        assertFalse(uniqueEventList.contains(event));
//    }
//
//    @Test
//    void add_eventNotInList_addsEvent() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        assertTrue(uniqueEventList.contains(event));
//    }
//
//    @Test
//    void add_duplicateEvent_throwsDuplicateEventException() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(event));
//    }
//
//    @Test
//    void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        List<Event> events = List.of(new Event(new EventName("IFG")), new Event(new EventName("IFG")));
//        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(events));
//    }
//
//    @Test
//    void setEvent_targetEventNotInList_throwsEventNotFoundException() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event target = new Event(new EventName("IFG"));
//        Event editedEvent = new Event(new EventName("SUNIG"));
//        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(target, editedEvent));
//    }
//
//    @Test
//    void setEvent_editedEventHasSameIdentity_success() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        Event editedEvent = new Event(new EventName("IFG"));
//        uniqueEventList.setEvent(event, editedEvent);
//        assertTrue(uniqueEventList.contains(editedEvent));
//    }
//
//    @Test
//    void setEvent_editedEventHasDifferentIdentity_success() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        Event editedEvent = new Event(new EventName("SUNIG"));
//        uniqueEventList.setEvent(event, editedEvent);
//        assertTrue(uniqueEventList.contains(editedEvent));
//    }
//
//    @Test
//    void remove_eventInList_removesEvent() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        uniqueEventList.add(event);
//        uniqueEventList.remove(event);
//        assertFalse(uniqueEventList.contains(event));
//    }
//
//    @Test
//    void remove_eventNotInList_throwsEventNotFoundException() {
//        UniqueEventList uniqueEventList = new UniqueEventList();
//        Event event = new Event(new EventName("IFG"));
//        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(event));
//    }
//}
