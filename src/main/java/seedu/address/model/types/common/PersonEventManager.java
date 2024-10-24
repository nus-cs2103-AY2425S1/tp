package seedu.address.model.types.common;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.types.event.Event;
import seedu.address.model.types.event.exceptions.DuplicateEventException;
import seedu.address.model.types.event.exceptions.EventNotFoundException;
import seedu.address.model.types.person.Person;

/**
 * Manages the relationship between events and persons.
 */
public class PersonEventManager {

    private static HashMap<Event, ArrayList<Person>> eventPersonMap;

    /**
     * Initialises the eventPersonMap, with events and persons from storage.
     */
    public static void initialiseHashMap() {
        if (eventPersonMap == null) {
            eventPersonMap = new HashMap<>();
            //TODO add events and persons from storage
        }
    }

    /* ============================== Person Methods ============================== */

    /**
     * Returns true if the person is linked to the specified event.
     * @param person
     * @param event
     * @return
     */
    public static boolean isPersonLinkedToEvent(Person person, Event event) {
        return eventPersonMap.get(event).contains(person);
    }

    /**
     * Adds the person to the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public static void addPersonToEvent(Person person, Event event) {
        eventPersonMap.get(event).add(person);
    }

    /**
     * removes the person from the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public static void removePersonFromEvent(Person person, Event event) {
        eventPersonMap.get(event).remove(person);
    }

    /**
     * Removes the person from all events.
     * @param person
     */
    public static void removePersonFromAllEvents(Person person) {
        if (eventPersonMap.keySet() == null) {
            return;
        }

        for (Event event : eventPersonMap.keySet()) {
            ArrayList<Person> linkedPeople = eventPersonMap.get(event);
            if (linkedPeople != null) {
                linkedPeople.remove(person);
            }
        }
    }

    /**
     * Replaces specified person with the edited person for all events.
     * @param target
     * @param editedPerson
     */
    public static void setPersonForAllEvents(Person target, Person editedPerson) {
        if (eventPersonMap.keySet() == null) {
            return;
        }

        for (Event event : eventPersonMap.keySet()) {
            if (eventPersonMap.get(event).contains(target)) {
                eventPersonMap.get(event).remove(target);
                eventPersonMap.get(event).add(editedPerson);
            }
        }
    }

    /* ============================== Event Methods ============================== */

    /**
     * Returns true if the event is in the eventPersonMap.
     * @param event
     * @return
     */
    public static boolean hasEvent(Event event) {
        return eventPersonMap.containsKey(event);
    }

    /**
     * Adds the event to the eventPersonMap.
     * @param event
     * @throws DuplicateEventException
     */
    public static void addEvent(Event event) {
        eventPersonMap.put(event, new ArrayList<>());
    }

    /**
     * Removes the event from the eventPersonMap.
     * @param event
     * @throws EventNotFoundException
     */
    public static void removeEvent(Event event) {
        eventPersonMap.remove(event);
    }

    /**
     * Replaces the target event with the edited event.
     * @param target
     * @param editedEvent
     */
    public static void setEvent(Event target, Event editedEvent) {
        ArrayList<Person> persons = eventPersonMap.get(target);
        eventPersonMap.remove(target);
        eventPersonMap.put(editedEvent, persons);
    }

    public static Event getEventByName(Event target) {
        return eventPersonMap.keySet().stream()
                .filter(event -> event.isSameEvent(target))
                .findFirst()
                .orElse(null);
    }
}
