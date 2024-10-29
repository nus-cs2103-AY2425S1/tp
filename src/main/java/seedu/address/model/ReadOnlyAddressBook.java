package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.types.common.LinkedPersonsEntry;
import seedu.address.model.types.common.PersonEventManager;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns the PersonEventManager.
     */
    PersonEventManager getPersonEventManager();

    /**
     * Returns an unmodifiable view of the linked persons entry list.
     * This list will not contain any duplicate linked persons entries.
     */
    List<LinkedPersonsEntry> getLinkedPersonsEntryList();
}
