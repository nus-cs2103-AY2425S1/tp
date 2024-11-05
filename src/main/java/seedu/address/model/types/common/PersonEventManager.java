package seedu.address.model.types.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.types.event.Event;
import seedu.address.model.types.event.exceptions.DuplicateEventException;
import seedu.address.model.types.event.exceptions.EventNotFoundException;
import seedu.address.model.types.person.Person;

/**
 * Manages the relationship between events and persons.
 */
public class PersonEventManager {

    private HashMap<Event, ArrayList<Person>> eventPersonMap;

    public PersonEventManager() {
        eventPersonMap = new HashMap<>();
    }

    /* ============================== Person Methods ============================== */

    /**
     * Returns true if the person is linked to the specified event.
     * @param person
     * @param event
     * @return
     */
    public boolean isPersonLinkedToEvent(Person person, Event event) {
        return eventPersonMap.get(event).contains(person);
    }

    /**
     * Adds the person to the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public void addPersonToEvent(Person person, Event event) {
        eventPersonMap.get(event).add(person);
    }

    /**
     * removes the person from the specified event.
     * @param event
     * @param person
     * @throws EventNotFoundException
     */
    public void removePersonFromEvent(Person person, Event event) {
        eventPersonMap.get(event).remove(person);
    }

    /**
     * Removes the person from all events.
     * @param person
     */
    public void removePersonFromAllEvents(Person person) {
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
    public void setPersonForAllEvents(Person target, Person editedPerson) {
        if (eventPersonMap.keySet() == null) {
            return;
        }

        for (Event event : eventPersonMap.keySet()) {
            ArrayList<Person> linkedPeople = eventPersonMap.get(event);

            if (linkedPeople == null) {
                continue;
            }

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
    public boolean hasEvent(Event event) {
        return eventPersonMap.containsKey(event);
    }

    /**
     * Adds the event to the eventPersonMap.
     * @param event
     * @throws DuplicateEventException
     */
    public void addEvent(Event event) {
        eventPersonMap.put(event, new ArrayList<>());
    }

    /**
     * Removes the event from the eventPersonMap.
     * @param event
     * @throws EventNotFoundException
     */
    public void removeEvent(Event event) {
        eventPersonMap.remove(event);
    }

    /**
     * Replaces the target event with the edited event.
     * @param target
     * @param editedEvent
     */
    public void setEvent(Event target, Event editedEvent) {
        ArrayList<Person> persons = eventPersonMap.get(target);
        eventPersonMap.remove(target);
        eventPersonMap.put(editedEvent, persons);
    }

    public Event getEventByName(Event target) {
        return eventPersonMap.keySet().stream()
                .filter(event -> event.isSameEvent(target))
                .findFirst()
                .orElse(null);
    }

    /**
     * adds a linked persons entry to the eventPersonMap.
     */
    public void addLinkedPersonsEntry(LinkedPersonsEntry linkedPersonsEntry) {
        eventPersonMap.put(linkedPersonsEntry.getEvent(), linkedPersonsEntry.getPersons());
    }

    public ArrayList<LinkedPersonsEntry> getLinkedPersonsEntryList() {
        ArrayList<LinkedPersonsEntry> linkedPersonsEntries = new ArrayList<>();
        for (Map.Entry<Event, ArrayList<Person>> entry : eventPersonMap.entrySet()) {
            Event event = entry.getKey();
            ArrayList<Person> persons = entry.getValue();
            linkedPersonsEntries.add(new LinkedPersonsEntry(event, persons));
        }
        return linkedPersonsEntries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Event, ArrayList<Person>> entry : eventPersonMap.entrySet()) {
            Event event = entry.getKey();
            ArrayList<Person> persons = entry.getValue();
            sb.append(event.getName().fullName).append(": ");
            for (Person person : persons) {
                sb.append(person.getName().fullName).append(", ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof PersonEventManager) {
            PersonEventManager otherManager = (PersonEventManager) other;
            return otherManager.eventPersonMap.equals(eventPersonMap);
        } else {
            return false;
        }
    }

    public Map<Event, ArrayList<Person>> getEventPersonMap() {
        return eventPersonMap;
    }
}
