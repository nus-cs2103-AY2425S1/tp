package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.model.listing.exceptions.ListingNotFoundException;

/**
 * A list of listings that enforces uniqueness between its elements and does not allow nulls.
 * A listing is considered unique by comparing using {@code Listing#isSameListing(Listing)}.
 * Supports a minimal set of list operations.
 *
 * @see Listing#isSameListing(Listing)
 */
public class UniqueListingList implements Iterable<Listing> {
    private final ObservableList<Listing> internalList = FXCollections.observableArrayList();
    private final ObservableList<Listing> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent listing as the given argument.
     *
     * @param toCheck The listing to check.
     * @return True if the listing is in the list, false otherwise.
     */
    public boolean contains(Listing toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameListing);
    }

    /**
     * Adds a listing to the list.
     * The listing must not already exist in the list.
     *
     * @param toAdd The listing to add.
     * @throws DuplicateListingException If the listing already exists in the list.
     */
    public void add(Listing toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateListingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the target listing in the list with the edited listing.
     * The target listing must exist in the list.
     * The edited listing must not be the same as another existing listing in the list.
     *
     * @param target The listing to replace.
     * @param editedListing The new listing to set.
     * @throws ListingNotFoundException If the target listing is not found in the list.
     * @throws DuplicateListingException If the edited listing is a duplicate of another listing.
     */
    public void setListing(Listing target, Listing editedListing) {
        requireAllNonNull(target, editedListing);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ListingNotFoundException();
        }

        if (!target.isSameListing(editedListing) && contains(editedListing)) {
            throw new DuplicateListingException();
        }

        internalList.set(index, editedListing);
    }

    /**
     * Removes the equivalent listing from the list.
     * The listing must exist in the list.
     *
     * @param toRemove The listing to remove.
     * @throws ListingNotFoundException If the listing is not found in the list.
     */
    public void remove(Listing toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ListingNotFoundException();
        }
    }

    /**
     * Replaces the current list with the provided replacement list.
     *
     * @param replacement The replacement list.
     */
    public void setListings(UniqueListingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the current list with the provided list of listings.
     * The provided list must not contain duplicate listings.
     *
     * @param listings The list of listings to set.
     * @throws DuplicateListingException If the list contains duplicate listings.
     */
    public void setListings(List<Listing> listings) {
        requireAllNonNull(listings);
        if (!listingsAreUnique(listings)) {
            throw new DuplicateListingException();
        }

        internalList.setAll(listings);
    }

    /**
     * Returns true if the provided list of listings contains only unique listings.
     *
     * @param listings The list of listings to check.
     * @return True if the list contains only unique listings, false otherwise.
     */
    private boolean listingsAreUnique(List<Listing> listings) {
        for (int i = 0; i < listings.size() - 1; i++) {
            for (int j = 1; j < listings.size(); j++) {
                if (listings.get(i).isSameListing(listings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ObservableList<Listing> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Listing> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueListingList)) {
            return false;
        }

        UniqueListingList otherUniqueListingList = (UniqueListingList) other;
        return internalList.equals(otherUniqueListingList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
