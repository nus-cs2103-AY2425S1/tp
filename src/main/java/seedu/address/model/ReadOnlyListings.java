package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.listing.Listing;

/**
 * Unmodifiable view of the listings
 */
public interface ReadOnlyListings {

    /**
     * Returns an unmodifiable view of the listings list.
     * This list will not contain any duplicate listings.
     */
    ObservableList<Listing> getListingList();
}
