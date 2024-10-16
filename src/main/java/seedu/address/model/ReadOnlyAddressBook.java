package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAdaptedGroup;


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
     * Returns a list of {@code JsonAdaptedGroup}s in the address book.
     */
    List<JsonAdaptedGroup> getGroupsAsJson();
}
