package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import seedu.address.model.person.exceptions.DuplicateListingException;
import seedu.address.model.person.exceptions.ListingNotFoundException;

/**
 * A list of unique listings that prevents duplicates and supports fast lookups.
 * Provides operations to add, remove, and retrieve listings.
 */
public class UniqueListingList implements Iterable<Listing> {
    private final ArrayList<Listing> listings;
    private final HashSet<Listing> listingsSet;
    private int hdbCount = 0;
    private int landedCount = 0;
    private int condoCount = 0;

    /**
     * Constructs an empty UniqueListingList.
     */
    public UniqueListingList() {
        listings = new ArrayList<>();
        listingsSet = new HashSet<>();
    }

    /**
     * Constructs a UniqueListingList by copying an existing one.
     *
     * @param oldListings The existing UniqueListingList to copy.
     */
    public UniqueListingList(UniqueListingList oldListings) {
        listings = new ArrayList<>();
        listingsSet = new HashSet<>();
        oldListings.getListings().forEach(this::add);
    }

    /**
     * Retrieves the list of listings.
     *
     * @return The list of listings.
     */
    public ArrayList<Listing> getListings() {
        return listings;
    }

    /**
     * Retrieves a listing specified by index
     *
     * @return The list of listings.
     */
    public Listing get(Integer id) {
        return listings.get(id);
    }

    /**
     * Retrieves the set of listings.
     *
     * @return The set of listings.
     */
    public HashSet<Listing> getHashSet() {
        return listingsSet;
    }

    /**
     * Adds a new listing to the list.
     *
     * @param listing The listing to be added.
     * @return true if the listing was added successfully.
     * @throws DuplicateListingException if the listing already exists in the list.
     */
    public boolean add(Listing listing) {
        if (listingsSet.contains(listing)) {
            throw new DuplicateListingException();
        }
        listings.add(listing);
        listingsSet.add(listing);
        return true;
    }

    /**
     * Removes a listing at a given index.
     *
     * @param index The index of the listing to be removed.
     * @return The removed listing.
     * @throws ListingNotFoundException if the index is invalid.
     */
    public Listing remove(int index) {
        if (index < 0 || index >= listings.size()) {
            throw new ListingNotFoundException(index);
        }
        Listing removedListing = listings.remove(index);
        listingsSet.remove(removedListing);
        return removedListing;
    }

    /**
     * Returns the number of listings.
     *
     * @return The size of the list.
     */
    public int size() {
        return listings.size();
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty; otherwise, false.
     */
    public boolean isEmpty() {
        return listings.isEmpty();
    }

    @Override
    public String toString() {
        return listings.toString();
    }

    @Override
    public Iterator<Listing> iterator() {
        return listings.iterator();
    }
}
