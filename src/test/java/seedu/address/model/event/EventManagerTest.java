package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ATTENDEE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.EDITED_ALICE_WITH_SAME_ROLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonInEventPredicate;

public class EventManagerTest {

    private EventManager eventManager;
    private Event event1;
    private Event event2;
    private Event event3;
    private Event duplicateEvent1;
    private Event duplicateEvent2;

    @BeforeEach
    public void setUp() {
        eventManager = new EventManager();
        event1 = new Event("event1");
        event2 = new Event("event2");
        event3 = new Event("event3");
        duplicateEvent1 = new Event("event1");
        duplicateEvent2 = new Event("event2");
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
    public void getPersonInEventPredicate_eventExistent_returnsTrue() {
        eventManager.addEvent(event1);
        PersonInEventPredicate predicate = new PersonInEventPredicate(event1);

        assertTrue(predicate.equals(eventManager.getPersonInEventPredicate(event1)));
    }

    @Test
    public void getPersonInEventPredicate_eventNonExistent_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> eventManager.getPersonInEventPredicate(event1));
    }

    @Test
    public void resetData_nullData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eventManager.resetData(null));

        eventManager.addEvent(event1);
        assertThrows(NullPointerException.class, () -> eventManager.resetData(null));
    }

    @Test
    public void resetData_validData_returnsTrue() {
        eventManager.addEvent(event1);

        EventManager otherEventManager = new EventManager();
        otherEventManager.addEvent(event2);

        eventManager.resetData(otherEventManager);

        assertTrue(eventManager.equals(otherEventManager));
    }

    @Test
    public void eventManager_copyData_returnsTrue() {
        try {
            event1.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            event2.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            eventManager.addEvent(event1);
            eventManager.addEvent(event2);

            EventManager copyOfEventManager = new EventManager(eventManager);

            assertTrue(copyOfEventManager.equals(eventManager));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void editAllPersonsInEvents_nullPersons_throwsNullPointerException() {
        Person personToEdit = ALICE;
        Person editedPerson = BOB;

        assertThrows(NullPointerException.class, () ->
                eventManager.editAllPersonsInEvents(personToEdit, null));

        assertThrows(NullPointerException.class, () ->
                eventManager.editAllPersonsInEvents(null, editedPerson));
    }

    @Test
    public void editAllPersonsInEvents_validPerson_returnTrue() {
        try {
            event1.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            event2.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            eventManager.addEvent(event1);
            eventManager.addEvent(event2);

            eventManager.editAllPersonsInEvents(ALICE, EDITED_ALICE_WITH_SAME_ROLE);

            EventManager otherEventManager = new EventManager();
            duplicateEvent1.addPerson(EDITED_ALICE_WITH_SAME_ROLE, VALID_ROLE_ATTENDEE);
            duplicateEvent2.addPerson(EDITED_ALICE_WITH_SAME_ROLE, VALID_ROLE_ATTENDEE);
            otherEventManager.addEvent(duplicateEvent1);
            otherEventManager.addEvent(duplicateEvent2);

            assertTrue(eventManager.equals(otherEventManager));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removeAllPersonsInEvents_nullPersons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                eventManager.removeAllPersonsInEvents(null));
    }

    @Test
    public void removeAllPersonsInEvents_validPerson_returnTrue() {
        try {
            event1.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            event2.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            eventManager.addEvent(event1);
            eventManager.addEvent(event2);

            eventManager.removeAllPersonsInEvents(ALICE);

            EventManager otherEventManager = new EventManager();
            // Add events with no person to the other event manager
            otherEventManager.addEvent(duplicateEvent1);
            otherEventManager.addEvent(duplicateEvent2);

            assertTrue(eventManager.equals(otherEventManager));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void clearAllContactsFromAllEvents_returnTrue() {
        try {
            event1.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            event2.addPerson(ALICE, VALID_ROLE_ATTENDEE);
            eventManager.addEvent(event1);
            eventManager.addEvent(event2);

            eventManager.clearAllContactsFromAllEvents();

            EventManager otherEventManager = new EventManager();
            // Add events with no person to the other event manager
            otherEventManager.addEvent(duplicateEvent1);
            otherEventManager.addEvent(duplicateEvent2);

            assertTrue(eventManager.equals(otherEventManager));
        } catch (Exception e) {
            assert false;
        }
    }
}

