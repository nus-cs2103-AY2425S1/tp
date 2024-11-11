package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.nowPlusDays;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class PersonEventManagerTest {

    private PersonEventManager personEventManager;
    private Event event;
    private Person person;

    @BeforeEach
    public void setUp() {
        personEventManager = new PersonEventManager();
        event = new EventBuilder()
                .withName("Concert Night")
                .withAddress("123, Orchard Rd, #05-01")
                .withStartTime(nowPlusDays(6))
                .withTags("music")
                .build();
        person = new PersonBuilder()
                .withName("Daniel Meier")
                .withPhone("87652533")
                .withEmail("cornelia@example.com")
                .withAddress("10th street")
                .withTags("friends")
                .build();;
        personEventManager.addEvent(event);
    }

    @Test
    public void addPersonToEvent_personAddedSuccessfully() {
        personEventManager.addPersonToEvent(person, event);
        assertTrue(personEventManager.isPersonLinkedToEvent(person, event));
    }

    @Test
    public void removePersonFromEvent_personRemovedSuccessfully() {
        personEventManager.addPersonToEvent(person, event);
        personEventManager.removePersonFromEvent(person, event);
        assertFalse(personEventManager.isPersonLinkedToEvent(person, event));
    }

    @Test
    public void removePersonFromAllEvents_personRemovedFromAllEvents() {
        Event anotherEvent = new EventBuilder()
                .withName("Sentosa")
                .withAddress("Sentosa Island")
                .withStartTime(nowPlusDays(10))
                .withTags("Sun")
                .build();
        personEventManager.addEvent(anotherEvent);
        personEventManager.addPersonToEvent(person, event);
        personEventManager.addPersonToEvent(person, anotherEvent);

        personEventManager.removePersonFromAllEvents(person);
        assertFalse(personEventManager.isPersonLinkedToEvent(person, event));
        assertFalse(personEventManager.isPersonLinkedToEvent(person, anotherEvent));
    }

    @Test
    public void setPersonForAllEvents_personReplacedSuccessfully() {
        Person editedPerson = new PersonBuilder()
                .withName("Alice")
                .withPhone("87654321")
                .withEmail("alice_edited@example.com")
                .withAddress("1321, Jurong East Ave 1, #01-123")
                .withTags("friends")
                .build();
        personEventManager.addPersonToEvent(person, event);
        personEventManager.setPersonForAllEvents(person, editedPerson);

        assertFalse(personEventManager.isPersonLinkedToEvent(person, event));
        assertTrue(personEventManager.isPersonLinkedToEvent(editedPerson, event));
    }

    @Test
    public void addEvent_eventAddedSuccessfully() {
        Event newEvent = new EventBuilder()
                .withName("Sentosa")
                .withAddress("Sentosa Island")
                .withStartTime(nowPlusDays(10))
                .withTags("Sun")
                .build();
        personEventManager.addEvent(newEvent);
        assertTrue(personEventManager.hasEvent(newEvent));
    }

    @Test
    public void removeEvent_eventRemovedSuccessfully() {
        personEventManager.removeEvent(event);
        assertFalse(personEventManager.hasEvent(event));
    }

    @Test
    public void getEventByName_eventFoundSuccessfully() {
        Event foundEvent = personEventManager.getEventByName(event);
        assertEquals(event, foundEvent);
    }

    @Test
    public void addLinkedPersonsEntry_linkedPersonsEntryAddedSuccessfully() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(person);
        LinkedPersonsEntry linkedPersonsEntry = new LinkedPersonsEntry(event, persons);
        personEventManager.addLinkedPersonsEntry(linkedPersonsEntry);

        assertTrue(personEventManager.isPersonLinkedToEvent(person, event));
    }

    @Test
    public void getLinkedPersonsEntryList_linkedPersonsEntryListRetrievedSuccessfully() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(person);
        LinkedPersonsEntry linkedPersonsEntry = new LinkedPersonsEntry(event, persons);
        personEventManager.addLinkedPersonsEntry(linkedPersonsEntry);

        ArrayList<LinkedPersonsEntry> linkedPersonsEntries = personEventManager.getLinkedPersonsEntryList();
        assertEquals(1, linkedPersonsEntries.size());
        assertEquals(linkedPersonsEntry, linkedPersonsEntries.get(0));
    }
}
