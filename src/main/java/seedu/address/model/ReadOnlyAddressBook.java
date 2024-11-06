package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the volunteers list.
     * This list will not contain any duplicate volunteers.
     */
    ObservableList<Volunteer> getVolunteerList();
    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    List<Event> getEventFromListOfNames(ObservableList<String> events);

    boolean eventHasOverlapWithList(Event event, List<Event> volnEvents);
}
