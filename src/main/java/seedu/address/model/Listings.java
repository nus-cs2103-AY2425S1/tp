package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.UniqueListingList;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Listings implements ReadOnlyListings {
    private final UniqueListingList listings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        listings = new UniqueListingList();
    }

    public Listings() {
    }

    /**
     * Creates Listings using the Listings in the {@code toBeCopied}
     */
    public Listings(ReadOnlyListings toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite level operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setListings(List<Listing> listings) {
        this.listings.setListings(listings);
    }

    /**
     * Resets the existing data of this {@code Listings} with {@code newData}.
     */
    public void resetData(ReadOnlyListings newData) {
        requireNonNull(newData);

        setListings(newData.getListingList());
    }

    //// listing-level operations

    /**
     * Returns true if a listing with the same identity as {@code listing} exists in the address book.
     */
    public boolean hasListing(Listing listing) {
        requireNonNull(listing);
        return listings.contains(listing);
    }

    /**
     * Adds a listing to the address book.
     * The listing must not already exist in the address book.
     */
    public void addListing(Listing l) {
        listings.add(l);
    }

    /**
     * Replaces the given listing {@code listing} in the list with {@code editedListing}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedListing} must not be the same as another existing listing in the address book.
     */
    public void setListing(Listing listing, Listing editedListing) {
        requireNonNull(editedListing);

        listings.setListing(listing, editedListing);
    }

    /**
     * Removes {@code key} from this {@code Listings}.
     * {@code key} must exist in the address book.
     */
    public void removeListing(Listing listing) {
        listings.remove(listing);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("listings", listings)
                .toString();
    }

    @Override
    public ObservableList<Listing> getListingList() {
        return listings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Listings)) {
            return false;
        }

        Listings otherListings = (Listings) other;
        return listings.equals(otherListings.listings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listings);
    }


}
