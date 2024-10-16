package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.util.Pair;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the vendors list.
     * This list will not contain any duplicate vendors.
     */
    ObservableList<Vendor> getVendorList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns an unmodifiable view of the associations set.
     */
    ObservableSet<Pair<Vendor, Event>> getAssociations();

}
