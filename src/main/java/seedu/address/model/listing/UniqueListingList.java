package seedu.address.model.listing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.listing.exceptions.DuplicateListingException;
import seedu.address.model.listing.exceptions.ListingNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueListingList implements Iterable<Listing> {
    private final ObservableList<Listing> internalList = FXCollections.observableArrayList();
    private final ObservableList<Listing> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Listing toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameListing);
    }

    public void add(Listing toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateListingException();
        }
        internalList.add(toAdd);
    }

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

    public void remove(Listing toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ListingNotFoundException();
        }
    }

    public void setListings(UniqueListingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setListings(List<Listing> listings) {
        requireAllNonNull(listings);
        if (!listingsAreUnique(listings)) {
            throw new DuplicateListingException();
        }

        internalList.setAll(listings);
    }

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
