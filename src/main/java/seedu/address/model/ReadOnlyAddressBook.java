package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.buyer.Buyer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<Buyer> getBuyerList();

}
