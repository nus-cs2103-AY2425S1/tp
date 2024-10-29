package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.PERSON_ALEX;
import static seedu.address.testutil.EventBuilder.DEFAULT_ATTENDEES;
import static seedu.address.testutil.TypicalEvents.EVENT_MULTIPLE_ATTENDEE;
import static seedu.address.testutil.TypicalEvents.EVENT_NO_ATTENDEE;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class EventTest {
    private Event event;
    private Person person1;
    private Person person2;
    private Address address;

    @BeforeEach
    public void setUp() {
        person1 = new PersonBuilder().withName("John Doe").build();
        person2 = new PersonBuilder().withName("Jane Doe").build();
        address = new Address("123 Main St, City");
        Set<Person> attendees = new HashSet<>();
        attendees.add(person1);
        attendees.add(person2);

        event = new Event("Conference", LocalDate.of(2023, 10, 29), address, attendees);
    }

    @Test
    public void getEventName_returnsCorrectName() {
        assertEquals("Workshop", EVENT_NO_ATTENDEE.getEventName());
    }

    @Test
    public void getDate_returnsCorrectDate() {
        assertEquals(LocalDate.of(2023, 10, 01), EVENT_NO_ATTENDEE.getDate());
    }

    @Test
    public void getLocation_returnsCorrectLocation() {
        assertEquals(new Address("123 Main Street"), EVENT_NO_ATTENDEE.getLocation());
    }

    @Test
    public void getAttendees_returnsUnmodifiableSet() {
        Set<Person> attendees = EVENT_MULTIPLE_ATTENDEE.getAttendees();
        assertTrue(attendees.contains(PERSON_ALEX));
        assertFalse(attendees.contains(ALICE));
    }

    @Test
    public void isPersonAttending() {
        // Event with no attendees
        assertFalse(EVENT_NO_ATTENDEE.isPersonAttending(ALICE));

        // Event with attendees
        // person in attendees -> returns true
        assertTrue(event.isPersonAttending(person1));

        // person not in attendees -> returns false
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isPersonAttending(ALICE));
    }

    @Test
    public void removeAttendee() {
        // Event with no attendees
        EVENT_NO_ATTENDEE.removeAttendee(ALICE);
        assertFalse(EVENT_NO_ATTENDEE.isPersonAttending(ALICE));

        // Event with attendees
        // person in attendees, removed successfully
        EVENT_MULTIPLE_ATTENDEE.removeAttendee(PERSON_ALEX);
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isPersonAttending(PERSON_ALEX));

        // person not in attendees, no effect
        EVENT_MULTIPLE_ATTENDEE.removeAttendee(ALICE);
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isPersonAttending(ALICE));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_NO_ATTENDEE.isSameEvent(EVENT_NO_ATTENDEE));

        // null -> returns false
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(null));

        Set<Person> attendees = new HashSet<>();
        Address location = new Address("123 Main Street");

        // same attributes -> returns true
        Event otherEvent = new Event("Family Gathering", LocalDate.of(2023, 10, 01),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isSameEvent(otherEvent));

        // different name -> returns false
        otherEvent = new Event("Conference", LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different date -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 11, 1),
                location, attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different location -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                new Address("456 Other St, City"), attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different attendees -> returns false
        attendees.add(ALICE);
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isSameEvent(otherEvent));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event eventCopy = new Event("Family Gathering", LocalDate.of(2023, 10, 03),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertTrue(EVENT_MULTIPLE_ATTENDEE.equals(eventCopy));

        // same object -> returns true
        assertTrue(EVENT_MULTIPLE_ATTENDEE.equals(EVENT_MULTIPLE_ATTENDEE));

        // null -> returns false
        assertFalse(EVENT_MULTIPLE_ATTENDEE.equals(null));

        // different type -> returns false
        assertFalse(EVENT_MULTIPLE_ATTENDEE.equals(5));

        // different event -> returns false
        assertFalse(EVENT_MULTIPLE_ATTENDEE.equals(EVENT_NO_ATTENDEE));

        Set<Person> attendees = new HashSet<>();
        Address location = new Address("123 Main Street");

        // same attributes -> returns true
        Event otherEvent = new Event("Family Gathering", LocalDate.of(2023, 10, 01),
                new Address("89 City Hall"), DEFAULT_ATTENDEES);
        assertFalse(EVENT_MULTIPLE_ATTENDEE.isSameEvent(otherEvent));

        // different name -> returns false
        otherEvent = new Event("Conference", LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different date -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 11, 01),
                location, attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different location -> returns false
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                new Address("456 Other St, City"), attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));

        // different attendees -> returns false
        attendees.add(ALICE);
        otherEvent = new Event("Workshop", LocalDate.of(2023, 10, 01),
                location, attendees);
        assertFalse(EVENT_NO_ATTENDEE.isSameEvent(otherEvent));
    }

    @Test
    public void toString_containsEventDetails() {
        String expected = "Event{name='" + EVENT_MULTIPLE_ATTENDEE.getEventName()
                + "', date=" + EVENT_MULTIPLE_ATTENDEE.getDate()
                + ", \nattendees=";

        // Build the string for each attendee, adding a newline after each one
        for (Person attendee : EVENT_MULTIPLE_ATTENDEE.getAttendees()) {
            expected += "\n" + attendee.toString();
        }

        expected += "}";

        assertEquals(expected, EVENT_MULTIPLE_ATTENDEE.toString());
    }

}
