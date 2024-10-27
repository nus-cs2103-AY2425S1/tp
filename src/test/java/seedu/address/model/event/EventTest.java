package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;



public class EventTest {

    @Test
    public void getName() {
        Event event = new Event("Event1");
        assertEquals("Event1", event.getName());
    }

    @Test
    public void equals_equalEvent() {
        Event event1 = new Event("Event1");
        Event event2 = new Event("Event1");
        assertEquals(event1, event2);
    }

    @Test
    public void equals_sameEventDifferentConstructor() {
        Event event1 = new Event("Event1");
        Event event2 = new Event("Event1", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertEquals(event1, event2);
    }

    @Test
    public void equals_sameEventDifferentConstructorEmptySet() {
        Set<Person> attendees = new HashSet<>();
        attendees.add(new PersonBuilder().withRoles("attendee").build());
        Set<Person> vendors = new HashSet<>();
        vendors.add(new PersonBuilder().withRoles("vendor").build());
        Set<Person> sponsors = new HashSet<>();
        sponsors.add(new PersonBuilder().withRoles("sponsor").build());
        Set<Person> volunteers = new HashSet<>();
        volunteers.add(new PersonBuilder().withRoles("volunteer").build());
        Event event1 = new Event("Event1", attendees, vendors, sponsors, volunteers);
        Event event2 = new Event("Event1", attendees, vendors, sponsors, volunteers);
        assertEquals(event1, event2);
    }

    @Test
    public void equals_differentEvent() {
        Event event1 = new Event("Event1");
        Event event2 = new Event("Event2");
        assert (!event1.equals(event2));
    }

    @Test
    public void equals_nullEvent() {
        Event event1 = new Event("Event1");
        assert (!event1.equals(null));
    }

    @Test
    public void addAttendee() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("attendee").build();
            event.addPerson(person, "attendee");
            Set<Person> attendees = new HashSet<>();
            attendees.add(person);
            assertEquals(attendees, event.getAttendees());
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void addAttendee_personNoRole() {

        Event event = new Event("Event1");
        Person person = new PersonBuilder().build();
        assertThrows(IllegalValueException.class, () -> event.addPerson(person, "attendee"));

    }
    @Test
    public void addVendor() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("vendor").build();
            event.addPerson(person, "vendor");
            Set<Person> vendors = new HashSet<>();
            vendors.add(person);
            assertEquals(vendors, event.getVendors());
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void addSponsor_multipleRole() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("sponsor", "attendee").build();
            event.addPerson(person, "sponsor");
            Set<Person> sponsors = new HashSet<>();
            sponsors.add(person);
            assertEquals(sponsors, event.getSponsors());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void addVolunteer() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("volunteer").build();
            event.addPerson(person, "volunteer");
            Set<Person> volunteers = new HashSet<>();
            volunteers.add(person);
            assertEquals(volunteers, event.getVolunteers());
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    public void addVolunteer_alreadyInRole() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("volunteer").build();
            event.addPerson(person, "volunteer");
            assertThrows(IllegalValueException.class, () -> event.addPerson(person, "volunteer"));

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void addPerson_invalidRole() {
        Event event = new Event("Event1");
        Person person = new PersonBuilder().withRoles("attendee").build();
        assertThrows(IllegalValueException.class, () -> event.addPerson(person, "invalidRole"));
    }



    @Test
    public void removePerson() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("attendee").build();
            event.addPerson(person, "attendee");
            event.removePerson(person, "attendee");
            Set<Person> attendees = new HashSet<>();
            assertEquals(attendees, event.getAttendees());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_vendor() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("vendor").build();
            event.addPerson(person, "vendor");
            event.removePerson(person, "vendor");
            Set<Person> vendors = new HashSet<>();
            assertEquals(vendors, event.getVendors());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_sponsor_notInRole() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("sponsor", "attendee").build();
            event.addPerson(person, "attendee");
            assertThrows(IllegalArgumentException.class, () -> event.removePerson(person, "sponsor"));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_volunteer() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles("volunteer").build();
            event.addPerson(person, "volunteer");
            event.removePerson(person, "volunteer");
            Set<Person> volunteers = new HashSet<>();
            assertEquals(volunteers, event.getVolunteers());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_invalidRole() {
        Event event = new Event("Event1");
        Person person = new PersonBuilder().withRoles("attendee").build();
        assertThrows(IllegalArgumentException.class, () -> event.removePerson(person, "invalidRole"));
    }

    @Test
    public void isValidEvent_validInput_true() {
        assertTrue(Event.isValidEvent("birthday party"));
    }

    @Test
    public void isValidEvent_invalidInput_false() {
        assertFalse(Event.isValidEvent(""));
    }

    @Test
    public void getRole_validRole_true() {
        try {
            Event event = new Event("Event1");
            Person personAttendee = new PersonBuilder().withRoles("attendee").build();
            event.addPerson(personAttendee, "attendee");
            assertEquals("attendee", event.getRole(personAttendee));
            Person personVendor = new PersonBuilder().withRoles("vendor").build();
            event.addPerson(personVendor, "vendor");
            assertEquals("vendor", event.getRole(personVendor));
            Person personSponsor = new PersonBuilder().withRoles("sponsor").build();
            event.addPerson(personSponsor, "sponsor");
            assertEquals("sponsor", event.getRole(personSponsor));
            Person personVolunteer = new PersonBuilder().withRoles("volunteer").build();
            event.addPerson(personVolunteer, "volunteer");
            assertEquals("volunteer", event.getRole(personVolunteer));
            Person personNoRole = new PersonBuilder().build();
            assertNull(event.getRole(personNoRole));
        } catch (Exception e) {
            assert false;
        }

    }
}
