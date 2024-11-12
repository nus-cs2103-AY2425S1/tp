package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.EventManager;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VolunteerBuilder;

public class EventTest {

    private EventManager eventManager;
    private Event eventOne;
    private Event eventTwo;
    private Volunteer volunteerJohn;
    private Volunteer volunteerJane;

    @BeforeEach
    public void setUp() {
        eventManager = new EventManager();
        eventOne = new EventBuilder().withEventName("Charity Run").withStartTime("08:00").withEndTime("10:00").build();
        eventTwo = new EventBuilder().withEventName("Workshop").withStartTime("09:00").withEndTime("11:00").build();
        volunteerJohn = new VolunteerBuilder().withName("John Doe").build();
        volunteerJane = new VolunteerBuilder().withName("Jane Doe").build();
    }

    @Test
    public void addEvent_eventIsAdded() {
        eventManager.addEvent(eventOne);
        assertTrue(eventManager.hasEvent(eventOne));
    }

    @Test
    public void removeEvent_eventIsRemoved() {
        eventManager.addEvent(eventOne);
        eventManager.removeEvent(eventOne);
        assertFalse(eventManager.hasEvent(eventOne));
    }

    @Test
    public void assignVolunteerToEvent_successfulAssignment()
            throws DuplicateAssignException, OverlappingAssignException {
        eventManager.addEvent(eventOne);
        eventManager.assignVolunteerToEvent(volunteerJohn, eventOne);
        assertTrue(eventOne.getVolunteers().contains(volunteerJohn.getName().fullName));
    }

    @Test
    public void assignVolunteerToEvent_duplicateAssignment_throwsDuplicateAssignException() {
        eventManager.addEvent(eventOne);
        assertThrows(DuplicateAssignException.class, () -> {
            eventManager.assignVolunteerToEvent(volunteerJohn, eventOne);
            eventManager.assignVolunteerToEvent(volunteerJohn, eventOne); // Duplicate
        });
    }

    @Test
    public void unassignVolunteerFromEvent_successfulUnassignment()
            throws DuplicateAssignException, OverlappingAssignException {
        eventManager.addEvent(eventOne);
        eventManager.assignVolunteerToEvent(volunteerJohn, eventOne);
        eventManager.unassignVolunteerFromEvent(volunteerJohn, eventOne);
        assertFalse(eventOne.getVolunteers().contains(volunteerJohn.getName().fullName));
    }

    @Test
    public void getEventFromName_eventExists_returnsEvent() throws EventNotFoundException {
        eventManager.addEvent(eventOne);
        assertEquals(eventOne, eventManager.getEventFromName(eventOne.getName().toString()));
    }

    @Test
    public void getEventFromName_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> eventManager.getEventFromName("Nonexistent Event"));
    }

    @Test
    public void getEventsFromListOfNames_returnsCorrectEvents() throws EventNotFoundException {
        eventManager.addEvent(eventOne);
        eventManager.addEvent(eventTwo);

        List<String> eventNames = new ArrayList<>();
        eventNames.add(eventOne.getName().toString());
        eventNames.add(eventTwo.getName().toString());

        List<Event> events = eventManager.getEventsFromListOfNames(eventNames);
        assertTrue(events.contains(eventOne));
        assertTrue(events.contains(eventTwo));
    }

    @Test
    public void unassignVolunteerFromAllEvents_successfulUnassignmentFromAll()
            throws DuplicateAssignException, OverlappingAssignException {
        eventManager.addEvent(eventOne);
        eventManager.addEvent(eventTwo);

        eventManager.assignVolunteerToEvent(volunteerJohn, eventOne);
        eventManager.assignVolunteerToEvent(volunteerJohn, eventTwo);

        eventManager.unassignVolunteerFromAllEvents(volunteerJohn);

        assertFalse(eventOne.getVolunteers().contains(volunteerJohn.getName().fullName));
        assertFalse(eventTwo.getVolunteers().contains(volunteerJohn.getName().fullName));
    }

    @Test
    public void hasEvent_eventExists_returnsTrue() {
        eventManager.addEvent(eventOne);
        assertTrue(eventManager.hasEvent(eventOne));
    }

    @Test
    public void hasEvent_eventDoesNotExist_returnsFalse() {
        assertFalse(eventManager.hasEvent(eventOne));
    }

    @Test
    public void isOverlappingWith_partialOverlap_returnsTrue() {
        Event event1 = new EventBuilder().withEventName("Overlapping Event").withDate("2021-01-01")
                .withStartTime("09:00").withEndTime("10:00").build();
        Event event2 = new EventBuilder().withEventName("Overlapping Event").withDate("2021-01-01")
                        .withStartTime("09:30").withEndTime("10:30").build();
        assertTrue(event1.isOverlappingWith(event2));
    }

    @Test
    public void isOverlappingWith_noOverlap_returnsFalse() {
        Event event1 = new EventBuilder().withEventName("Non overlapping Event").withDate("2021-01-01")
                .withStartTime("09:00").withEndTime("10:00").build();
        Event event2 = new EventBuilder().withEventName("Non overlapping Event").withDate("2021-01-01")
                        .withStartTime("10:01").withEndTime("11:00").build();
        assertFalse(event1.isOverlappingWith(event2));
    }

    @Test
    public void isOverlappingWith_fullOverlap_returnsTrue() {
        Event event1 = new EventBuilder().withEventName("Overlapping Event").withDate("2021-01-01")
                .withStartTime("09:00").withEndTime("10:00").build();
        Event event2 = new EventBuilder().withEventName("Overlapping Event").withDate("2021-01-01")
                        .withStartTime("09:00").withEndTime("10:00").build();
        assertTrue(event1.isOverlappingWith(event2));
    }
}
