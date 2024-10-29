package seedu.address.model.types.common;

import java.util.ArrayList;

import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * Entry in the event-person mapping.
 * Represents a mapping between an event and a list of persons linked to the event.
 */
public class LinkedPersonsEntry {
    private Event event;
    private ArrayList<Person> persons;

    /**
     * Creates a LinkedPersonsEntry with the specified event and list of persons.
     * @param event Event to be linked to the persons.
     * @param persons List of persons to be linked to the event.
     */
    public LinkedPersonsEntry(Event event, ArrayList<Person> persons) {
        this.event = event;
        this.persons = persons;
    }

    /**
     * Returns the event linked to the persons.
     * @return Event linked to the persons.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Returns the list of persons linked to the event.
     * @return List of persons linked to the event.
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }
}
