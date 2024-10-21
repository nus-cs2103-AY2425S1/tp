package seedu.sellsavvy.model;

import javafx.collections.ObservableList;
import seedu.sellsavvy.model.person.Person;

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
     * Returns a {@code Person} in the {@code UniquePersonList} equivalent to target Person given.
     * Returns null if target is null
     */
    Person findEquivalentPerson(Person target);

}
