package seedu.eventtory.model;

import javafx.collections.ObservableList;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Unmodifiable view of an EventTory
 */
public interface ReadOnlyEventTory {

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
     * Returns an unmodifiable view of the associations list.
     * This list will not contain any duplicate associations.
     */
    ObservableList<Association> getAssociationList();
}
