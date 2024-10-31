package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.supplier.Supplier;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the suppliers list.
     * This list will not contain any duplicate suppliers.
     */
    ObservableList<Supplier> getSupplierList();
    ObservableList<Delivery> getDeliveryList();

}
