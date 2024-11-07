package seedu.sellsavvy.model;

import javafx.collections.ObservableList;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Customer> getPersonList();

}
