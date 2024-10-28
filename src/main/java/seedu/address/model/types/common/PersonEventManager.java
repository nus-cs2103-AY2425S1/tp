package seedu.address.model.types.common;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ListChangeListener;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.event.exceptions.DuplicateEventException;
import seedu.address.model.types.event.exceptions.EventNotFoundException;
import seedu.address.model.types.person.Person;

public class PersonEventManager {

    private static ObservableMap<Event, ObservableList<Person>> eventPersonMap;

    /**
     * Initialises the eventPersonMap, with events and persons from storage.
     */
    public static void initialiseHashMap() {
        if (eventPersonMap == null) {
            eventPersonMap = FXCollections.observableHashMap();
            // TODO add events and persons from storage
        }
    }

    /* ============================== Person Methods ============================== */

    public static boolean isPersonLinkedToEvent(Person person, Event event) {
        ObservableList<Person> persons = eventPersonMap.get(event);
        return persons != null && persons.contains(person);
    }

    public static void addPersonToEvent(Person person, Event event) {
        ObservableList<Person> persons = eventPersonMap.get(event);
        if (persons != null) {
            persons.add(person);
        } else {
            throw new EventNotFoundException();
        }
    }

    public static void removePersonFromEvent(Person person, Event event) {
        ObservableList<Person> persons = eventPersonMap.get(event);
        if (persons != null) {
            persons.remove(person);
        } else {
            throw new EventNotFoundException();
        }
    }

    public static void removePersonFromAllEvents(Person person) {
        eventPersonMap.values().forEach(persons -> persons.remove(person));
    }

    public static void setPersonForAllEvents(Person target, Person editedPerson) {
        eventPersonMap.values().forEach(persons -> {
            if (persons.contains(target)) {
                persons.remove(target);
                persons.add(editedPerson);
            }
        });
    }

    /* ============================== Event Methods ============================== */

    public static boolean hasEvent(Event event) {
        return eventPersonMap.containsKey(event);
    }

    public static void addEvent(Event event) {
        if (eventPersonMap.containsKey(event)) {
            throw new DuplicateEventException();
        }
        ObservableList<Person> personsList = FXCollections.observableArrayList();
        
        // Add a listener to detect changes in the ObservableList
        // personsList.addListener((ListChangeListener<Person>) change -> {
        //     while (change.next()) {
        //         // This listener ensures that the ObservableList notifies any observers when it changes
        //         if (change.wasAdded() || change.wasRemoved()) {
        //             Person dummyPerson = new Person(new Name("zz"), 
        //                 new Phone("12345678"), 
        //                 new Email("johnd@example.com"), 
        //                 new Address("311, Clementi Ave 2, #02-25"), 
        //                 null);
        //             PersonEventManager.addressbook.addPerson(dummyPerson);
        //             PersonEventManager.addressbook.removePerson(dummyPerson);
        //         }
        //     }
        // });

        eventPersonMap.put(event, personsList);
    }

    public static void removeEvent(Event event) {
        eventPersonMap.remove(event);
    }

    public static void setEvent(Event target, Event editedEvent) {
        ObservableList<Person> persons = eventPersonMap.get(target);
        if (persons != null) {
            eventPersonMap.remove(target);
            eventPersonMap.put(editedEvent, persons);
        }
    }

    public static Event getEventByName(Event target) {
        return eventPersonMap.keySet().stream()
                .filter(event -> event.isSameEventName(target))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns an unmodifiable view of the eventPersonMap.
     * 
     * @return an ObservableMap<Event, ObservableList<Person>> representing the relationship between events and persons.
     */
    public static ObservableMap<Event, ObservableList<Person>> getEventPersonMap() {
        return FXCollections.unmodifiableObservableMap(eventPersonMap);
    }
}
