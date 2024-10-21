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
     * Adds the person to the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public static void addPersonToEvent(Event event, Person person) throws EventNotFoundException {
        if (eventPersonMap.containsKey(event)) {
            eventPersonMap.get(event).add(person);
        } else if (!eventPersonMap.containsKey(event)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * removes the person from the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public static void removePersonFromEvent(Event event, Person person) throws EventNotFoundException {
        if (eventPersonMap.containsKey(event)) {
            eventPersonMap.get(event).remove(person);
        } else if (!eventPersonMap.containsKey(event)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Removes the person from all events.
     * @param person
     */
    public static void removePersonFromAllEvents(Person person) {
        for (Event event : eventPersonMap.keySet()) {
            eventPersonMap.get(event).remove(person);
        }
    }

    /**
     * Replaces specified person with the edited person for all events.
     * @param target
     * @param editedPerson
     */
    public static void setPersonForAllEvents(Person target, Person editedPerson) {
        for (Event event : eventPersonMap.keySet()) {
            if (eventPersonMap.get(event).contains(target)) {
                eventPersonMap.get(event).remove(target);
                eventPersonMap.get(event).add(editedPerson);
            }
        }
    }

    /* ============================== Event Methods ============================== */

    /**
     * Adds the event to the eventPersonMap.
     * @param event
     * @throws DuplicateEventException
     */
    public static void addEvent(Event event) throws DuplicateEventException {
        if (!eventPersonMap.containsKey(event)) {
            eventPersonMap.put(event, new ArrayList<>());
        } else if (eventPersonMap.containsKey(event)) {
            throw new DuplicateEventException();
        }
    }

    /**
     * Removes the event from the eventPersonMap.
     * @param event
     * @throws EventNotFoundException
     */
    public static void removeEvent(Event event) throws EventNotFoundException {
        if (eventPersonMap.containsKey(event)) {
            eventPersonMap.remove(event);
        } else if (!eventPersonMap.containsKey(event)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Replaces the target event with the edited event.
     * @param target
     * @param editedEvent
     */
    public static void setEvent(Event target, Event editedEvent) {
        if (eventPersonMap.containsKey(target)) {
            ArrayList<Person> persons = eventPersonMap.get(target);
            eventPersonMap.remove(target);
            eventPersonMap.put(editedEvent, persons);
        }
    }
}
