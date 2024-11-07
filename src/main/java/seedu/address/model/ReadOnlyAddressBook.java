package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

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
     * Checks if the address book contains a person with the given phone number.
     *
     * @param phone The phone number to check for.
     * @return True if a person with the given phone number exists in the address book, false otherwise.
     */
    boolean hasPhoneNumber(Phone phone);

}
