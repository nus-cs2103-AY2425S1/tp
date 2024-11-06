package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ATTENDEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_SPONSOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VENDOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_VOLUNTEER;
import static seedu.address.testutil.TypicalEvents.TECH_CONFERENCE;
import static seedu.address.testutil.TypicalEvents.TECH_CONFERENCE_EDITED;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.EDITED_ALICE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.role.Attendee;
import seedu.address.model.role.Role;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.ui.Observer;


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
        attendees.add(new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build());
        Set<Person> vendors = new HashSet<>();
        vendors.add(new PersonBuilder().withRoles(VALID_ROLE_VENDOR).build());
        Set<Person> sponsors = new HashSet<>();
        sponsors.add(new PersonBuilder().withRoles(VALID_ROLE_SPONSOR).build());
        Set<Person> volunteers = new HashSet<>();
        volunteers.add(new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build());
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
            event.addPerson(person, VALID_ROLE_ATTENDEE);
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
        assertThrows(IllegalValueException.class, () -> event.addPerson(person, VALID_ROLE_ATTENDEE));

    }
    @Test
    public void addVendor() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles(VALID_ROLE_VENDOR).build();
            event.addPerson(person, VALID_ROLE_VENDOR);
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_SPONSOR, VALID_ROLE_ATTENDEE).build();
            event.addPerson(person, VALID_ROLE_SPONSOR);
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build();
            event.addPerson(person, VALID_ROLE_VOLUNTEER);
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build();
            event.addPerson(person, VALID_ROLE_VOLUNTEER);
            assertThrows(IllegalValueException.class, () -> event.addPerson(person, VALID_ROLE_VOLUNTEER));

        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void addPerson_invalidRole() {
        Event event = new Event("Event1");
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
        assertThrows(IllegalValueException.class, () -> event.addPerson(person, "invalidRole"));
    }



    @Test
    public void removePerson() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
            event.addPerson(person, VALID_ROLE_ATTENDEE);
            event.removePerson(person, VALID_ROLE_ATTENDEE);
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_VENDOR).build();
            event.addPerson(person, VALID_ROLE_VENDOR);
            event.removePerson(person, VALID_ROLE_VENDOR);
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
            Person person = new PersonBuilder().withRoles(VALID_ROLE_SPONSOR, VALID_ROLE_ATTENDEE).build();
            event.addPerson(person, VALID_ROLE_ATTENDEE);
            assertThrows(IllegalArgumentException.class, () -> event.removePerson(person, VALID_ROLE_SPONSOR));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_volunteer() {
        try {
            Event event = new Event("Event1");
            Person person = new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build();
            event.addPerson(person, VALID_ROLE_VOLUNTEER);
            event.removePerson(person, VALID_ROLE_VOLUNTEER);
            Set<Person> volunteers = new HashSet<>();
            assertEquals(volunteers, event.getVolunteers());
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_invalidRole() {
        Event event = new Event("Event1");
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
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
            Person personAttendee = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE).build();
            event.addPerson(personAttendee, VALID_ROLE_ATTENDEE);
            assertEquals(VALID_ROLE_ATTENDEE, event.getRole(personAttendee));
            Person personVendor = new PersonBuilder().withRoles(VALID_ROLE_VENDOR).build();
            event.addPerson(personVendor, VALID_ROLE_VENDOR);
            assertEquals(VALID_ROLE_VENDOR, event.getRole(personVendor));
            Person personSponsor = new PersonBuilder().withRoles(VALID_ROLE_SPONSOR).build();
            event.addPerson(personSponsor, VALID_ROLE_SPONSOR);
            assertEquals(VALID_ROLE_SPONSOR, event.getRole(personSponsor));
            Person personVolunteer = new PersonBuilder().withRoles(VALID_ROLE_VOLUNTEER).build();
            event.addPerson(personVolunteer, VALID_ROLE_VOLUNTEER);
            assertEquals(VALID_ROLE_VOLUNTEER, event.getRole(personVolunteer));
            Person personNoRole = new PersonBuilder().build();
            assertNull(event.getRole(personNoRole));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void removePerson_fromMultipleRoles_success() {
        Event event = new Event("Event1");
        Person person = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE,
                VALID_ROLE_VENDOR, VALID_ROLE_SPONSOR, VALID_ROLE_VOLUNTEER).build();

        try {
            // Adding person to all roles
            event.addPerson(person, VALID_ROLE_ATTENDEE);
            event.addPerson(person, VALID_ROLE_VENDOR);
            event.addPerson(person, VALID_ROLE_SPONSOR);
            event.addPerson(person, VALID_ROLE_VOLUNTEER);

            // Removing person from all roles
            event.removePerson(person);

            // Assert that person has been removed from all roles
            assertFalse(event.getAttendees().contains(person));
            assertFalse(event.getVendors().contains(person));
            assertFalse(event.getSponsors().contains(person));
            assertFalse(event.getVolunteers().contains(person));
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void editPerson_inMultipleRoles_success() {
        Event event = new Event("Event1");
        Person personToEdit = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE,
                VALID_ROLE_VENDOR, VALID_ROLE_SPONSOR, VALID_ROLE_VOLUNTEER).build();
        Person editedPerson = new PersonBuilder().withRoles(VALID_ROLE_ATTENDEE,
                VALID_ROLE_VENDOR, VALID_ROLE_SPONSOR, VALID_ROLE_VOLUNTEER).withName("Edited Name").build();

        try {
            // Adding person to all roles
            event.addPerson(personToEdit, VALID_ROLE_ATTENDEE);
            event.addPerson(personToEdit, VALID_ROLE_VENDOR);
            event.addPerson(personToEdit, VALID_ROLE_SPONSOR);
            event.addPerson(personToEdit, VALID_ROLE_VOLUNTEER);

            // Editing person
            event.editPerson(personToEdit, editedPerson);

            // Assert that editedPerson is present and personToEdit is removed
            assertTrue(event.getAttendees().contains(editedPerson));
            assertTrue(event.getVendors().contains(editedPerson));
            assertTrue(event.getSponsors().contains(editedPerson));
            assertTrue(event.getVolunteers().contains(editedPerson));

            assertFalse(event.getAttendees().contains(personToEdit));
            assertFalse(event.getVendors().contains(personToEdit));
            assertFalse(event.getSponsors().contains(personToEdit));
            assertFalse(event.getVolunteers().contains(personToEdit));
        } catch (Exception e) {
            assert false;
        }
    }

    public class DummyObserver implements Observer {
        public void update() {

        }

        public void update(Person person) {

        }
    }
    @Test
    public void testAddObserver() {
        Observer dummyObserver = new DummyObserver();
        Event dummyEvent = new Event("dummy");
        dummyEvent.addObserver(dummyObserver);
        assertEquals(dummyEvent.getObserver(), dummyObserver);
    }

    @Test
    public void testGetTotalNumberOfDistinctContacts() {
        Event event = TypicalEvents.getTypicalEvents().get(0);
        assertEquals(event.getTotalNumberOfDistinctContacts(), 6);
    }

    @Test
    public void testRemoveContactFromEventRoleOnEdit() {
        Event event = TypicalEvents.getTypicalEvents().get(0);
        event.editPerson(ALICE, EDITED_ALICE);
        assertEquals(event, TECH_CONFERENCE_EDITED);
    }

    @Test
    public void testMakeRoles() {
        HashSet<Role> roles = TECH_CONFERENCE.makeRoles(ALICE);
        HashSet<Role> expectedRoles = new HashSet<>();
        expectedRoles.add(new Attendee());
        assertEquals(roles, expectedRoles);
    }
}
