package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.buyer.Buyer;

/**
 * Unmodifiable view of a buyer list
 */
public interface ReadOnlyBuyerList {

    /**
     * Returns an unmodifiable view of the buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<Buyer> getBuyerList();
}
