package seedu.sellsavvy.model;

import javafx.collections.ObservableList;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();

}
