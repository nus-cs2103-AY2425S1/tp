package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.id.counter.list.IdCounterList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Event> newEvents = new ArrayList<>();
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateEvents_throwsDuplicateEventException() {
        // Two events with the same identity fields
        Event editedMeeting = new EventBuilder(MEETING).build();
        List<Person> newPersons = new ArrayList<>();
        List<Event> newEvents = Arrays.asList(MEETING, editedMeeting);
        AddressBookStub newData = new AddressBookStub(newPersons, newEvents);

        assertThrows(DuplicateEventException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEvent(MEETING));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        addressBook.addEvent(MEETING);
        assertTrue(addressBook.hasEvent(MEETING));
    }

    @Test
    public void hasEvent_eventWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEvent(MEETING);
        Event editedMeeting = new EventBuilder(MEETING).build();
        assertTrue(addressBook.hasEvent(editedMeeting));
    }

    @Test
    public void findPersonsWithName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.findPersonsWithName(null));
    }

    @Test
    public void findPersonsWithName_personNotInAddressBook_returnsEmptyList() {
        List<Person> resultList = new ArrayList<>();
        assertEquals(addressBook.findPersonsWithName(ALICE.getName()), resultList);
    }

    @Test
    public void findPersonsWithName_personInAddressBook_returnsPersonList() {
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        addressBook.addPerson(ALICE);
        assertEquals(addressBook.findPersonsWithName(ALICE.getName()), resultList);
    }

    @Test
    public void findPersonsWithName_personWithPartOfNameNotInAddressBook_returnsEmptyList() {
        List<Person> resultList = new ArrayList<>();

        addressBook.addPerson(ALICE);
        String nameString = ALICE.getName().toString();
        String partOfNameString = nameString.substring(0, nameString.length() - 1);
        Name partOfName = new Name(partOfNameString);

        assertEquals(addressBook.findPersonsWithName(partOfName), resultList);
    }

    @Test
    public void findPersonsWithName_personWithLowerCasedNameInAddressBook_returnsPersonList() {
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);

        addressBook.addPerson(ALICE);
        String nameString = ALICE.getName().toString();
        String lowerCasedNameString = nameString.toLowerCase();
        Name lowerCasedName = new Name(lowerCasedNameString);
        assertEquals(addressBook.findPersonsWithName(lowerCasedName), resultList);
    }

    @Test
    public void findPersonsWithName_personWithUpperCasedNameInAddressBook_returnsPersonList() {
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);

        addressBook.addPerson(ALICE);
        String nameString = ALICE.getName().toString();
        String upperCasedNameString = nameString.toUpperCase();
        Name upperCasedName = new Name(upperCasedNameString);
        assertEquals(addressBook.findPersonsWithName(upperCasedName), resultList);
    }

    @Test
    public void generateNewPersonId_success() {
        Person aliceWithId = ALICE.changeId(1);
        Person amyWithId = AMY.changeId(2);
        addressBook.addPerson(aliceWithId);
        addressBook.addPerson(amyWithId);
        assertEquals(addressBook.generateNewPersonId(), 3);
    }

    @Test
    public void generateNewEventId_success() {
        Event meetingWithId = MEETING.changeId(1);
        Event workshopWithId = WORKSHOP.changeId(2);
        addressBook.addEvent(meetingWithId);
        addressBook.addEvent(workshopWithId);
        assertEquals(addressBook.generateNewEventId(), 3);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEventList().remove(0));
    }

    @Test
    public void setIdCounterList_success() {
        IdCounterList newIdCounterList = new IdCounterList(5, 10);
        addressBook.setIdCounterList(newIdCounterList);
        assertEquals(newIdCounterList, addressBook.getIdCounterList());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(addressBook.equals(addressBook));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(addressBook.equals(5));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(addressBook.equals(null));
    }

    @Test
    public void equals_differentAddressBook_returnsFalse() {
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addPerson(ALICE);
        assertFalse(addressBook.equals(differentAddressBook));
    }

    @Test
    public void hashCode_sameAddressBook_returnsSameHashCode() {
        AddressBook anotherAddressBook = new AddressBook();
        assertEquals(addressBook.hashCode(), anotherAddressBook.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void setEvent_existingEvent_replacesEvent() {
        // Add an initial event to the address book
        Event originalEvent = new EventBuilder()
                .withEventName("Original Meeting")
                .withEventDescription("A meeting to discuss project updates.")
                .withEventDuration("2024-10-01", "2024-10-02")
                .build();
        addressBook.addEvent(originalEvent);

        // Create an edited version of the event
        Event editedEvent = new EventBuilder(originalEvent)
                .withEventName("Edited Meeting")
                .build();

        // Replace the existing event with the edited event
        addressBook.setEvent(originalEvent, editedEvent);

        // Check that the event list contains the edited event and not the original
        assertTrue(addressBook.hasEvent(editedEvent));
        assertFalse(addressBook.hasEvent(originalEvent));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and events lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Event> events) {
            this.persons.setAll(persons);
            this.events.setAll(events);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public IdCounterList getIdCounterList() {
            return new IdCounterList(0, 0);
        }
    }
}
