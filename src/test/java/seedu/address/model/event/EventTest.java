package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.PERSON_ALEX;
import static seedu.address.testutil.EventBuilder.DEFAULT_ATTENDEES;
import static seedu.address.testutil.TypicalEvents.EVENT_MULTIPLE_ATTENDEE;
import static seedu.address.testutil.TypicalEvents.EVENT_NO_ATTENDEE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class EventTest {
    private Event eventNoAttendee;
    private Event eventMultipleAttendee;
    private Event event;
    private Person person1;
    private Person person2;
    private Address address;

    @BeforeEach
    public void setUp() {
        eventNoAttendee = eventDeepCopy(EVENT_NO_ATTENDEE);
        eventMultipleAttendee = eventDeepCopy(EVENT_MULTIPLE_ATTENDEE);

        person1 = new PersonBuilder().withName("John Doe").build();
        person2 = new PersonBuilder().withName("Jane Doe").build();
        address = new Address("123 Main St, City");

        Set<Person> attendees = new HashSet<>();
        attendees.add(person1);
        attendees.add(person2);

        event = new Event("Conference",
                LocalDate.of(2023, 10, 29),
                LocalDate.of(2023, 10, 29), address, attendees);
    }

    @Test
    public void getEventName_returnsCorrectName() {
        assertEquals("Workshop", eventNoAttendee.getEventName());
    }

    @Test
    public void getStartDate_returnsCorrectDate() {
        assertEquals(LocalDate.of(2023, 10, 01), eventNoAttendee.getStartDate());
    }

    @Test
    public void getEndDate_returnsCorrectDate() {
        assertEquals(LocalDate.of(2023, 10, 01), eventNoAttendee.getEndDate());
    }

    @Test
    public void getLocation_returnsCorrectLocation() {
        assertEquals(new Address("123 Main Street"), eventNoAttendee.getLocation());
    }

    @Test
    public void getAttendees_returnsUnmodifiableSet() {
        Set<Person> attendees = eventMultipleAttendee.getAttendees();
        assertTrue(attendees.contains(PERSON_ALEX));
        assertFalse(attendees.contains(ALICE));
    }

    @Test
    public void isPersonAttending() {
        // Event with no attendees
        assertFalse(eventNoAttendee.isPersonAttending(ALICE));

        // Event with attendees
        // person in attendees -> returns true
        assertTrue(event.isPersonAttending(person1));

        // person not in attendees -> returns false
        assertFalse(eventMultipleAttendee.isPersonAttending(ALICE));
    }

    @Test
    public void removeAttendee() {
        // Event with no attendees
        eventNoAttendee.removeAttendee(ALICE);
        assertFalse(eventNoAttendee.isPersonAttending(ALICE));

        // Event with attendees
        // person in attendees, removed successfully
        eventMultipleAttendee.removeAttendee(PERSON_ALEX);
        assertFalse(eventMultipleAttendee.isPersonAttending(PERSON_ALEX));

        // person not in attendees, no effect
        eventMultipleAttendee.removeAttendee(ALICE);
        assertFalse(eventMultipleAttendee.isPersonAttending(ALICE));
    }

    @Test
    public void editAttendee() {
        // Event with no attendees
        eventNoAttendee.removeAttendee(PERSON_ALEX);
        assertFalse(eventNoAttendee.isPersonAttending(PERSON_ALEX));

        // Event with attendees
        // person in attendees, updated successfully
        eventMultipleAttendee.editAttendee(PERSON_ALEX, ALICE);
        assertFalse(eventMultipleAttendee.isPersonAttending(PERSON_ALEX));
        assertTrue(eventMultipleAttendee.isPersonAttending(ALICE));

        // person in attendees, no effect

        eventMultipleAttendee.editAttendee(BOB, BENSON);
        assertFalse(eventMultipleAttendee.isPersonAttending(BENSON));
        assertFalse(eventMultipleAttendee.isPersonAttending(BOB));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(eventNoAttendee.isSameEvent(eventNoAttendee));

        // null -> returns false
        assertFalse(eventNoAttendee.isSameEvent(null));

        Set<Person> attendees = new HashSet<>();
        Address location = new Address("123 Main Street");

        // same attributes -> returns true
        Event otherEvent = new Event("Family Gathering", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertFalse(eventMultipleAttendee.isSameEvent(otherEvent));

        // different name -> returns false
        otherEvent = new Event("Conference", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different date -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 11, 1),
                LocalDate.of(2023, 11, 1),
                location, attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different location -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                new Address("456 Other St, City"), attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different attendees -> returns false
        attendees.add(ALICE);
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(eventMultipleAttendee.isSameEvent(otherEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event eventCopy = new Event("Family Gathering", LocalDate.of(2023, 10, 03),
                LocalDate.of(2023, 10, 03),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertTrue(eventMultipleAttendee.equals(eventCopy));

        // same object -> returns true
        assertTrue(eventMultipleAttendee.equals(eventMultipleAttendee));

        // null -> returns false
        assertFalse(eventMultipleAttendee.equals(null));

        // different type -> returns false
        assertFalse(eventMultipleAttendee.equals(5));

        // different event -> returns false
        assertFalse(eventMultipleAttendee.equals(eventNoAttendee));

        Set<Person> attendees = new HashSet<>();
        Address location = new Address("123 Main Street");

        // same attributes -> returns true
        Event otherEvent = new Event("Family Gathering", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertFalse(eventMultipleAttendee.isSameEvent(otherEvent));

        // different name -> returns false
        otherEvent = new Event("Conference", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different date -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 11, 01),
                LocalDate.of(2023, 11, 01),
                location, attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different location -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                new Address("456 Other St, City"), attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));

        // different attendees -> returns false
        attendees.add(ALICE);
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(eventNoAttendee.isSameEvent(otherEvent));
    }

    @Test
    public void toString_containsEventDetails() {
        String expected = "Event{name='" + eventMultipleAttendee.getEventName()
                + "', startDate=" + eventMultipleAttendee.getStartDate()
                + ", endDate=" + eventMultipleAttendee.getEndDate()
                + ", location=" + eventMultipleAttendee.getLocation().value
                + ", \nattendees=";

        // Build the string for each attendee, adding a newline after each one
        for (Person attendee : eventMultipleAttendee.getAttendees()) {
            expected += "\n" + attendee.toString();
        }

        expected += "}";

        assertEquals(expected, eventMultipleAttendee.toString());
    }

    public Event eventDeepCopy(Event eventToCopy) {
        return new Event(eventToCopy.getEventName(), eventToCopy.getStartDate(), eventToCopy.getEndDate(),
                eventToCopy.getLocation(), eventToCopy.getAttendees());
    }


}
