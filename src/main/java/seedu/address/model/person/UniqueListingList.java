package seedu.address.model.person;

import seedu.address.model.person.exceptions.DuplicateListingException;
import seedu.address.model.person.exceptions.ListingNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class UniqueListingList implements Iterable<Listing> {
    private final ArrayList<Listing> listings;
    private final HashSet<Listing> listingsSet;

    public UniqueListingList() {
        listings = new ArrayList<>();
        listingsSet = new HashSet<>();
    }

    public UniqueListingList(UniqueListingList oldListings) {
        listings = new ArrayList<>();
        listingsSet = new HashSet<>();
        oldListings.getListings().forEach((listing) -> {
            this.add(listing);
        });
    }

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public HashSet<Listing> getHashSet() {
        return listingsSet;
    }

    public boolean add(Listing listing) {
        if (listingsSet.contains(listing)) {
            throw new DuplicateListingException();
        }
        listings.add(listing);
        listingsSet.add(listing);

        return true;
    }

    public Listing remove(int index) {
        if (index < 0 || index >= listings.size()) {
            throw new ListingNotFoundException(index);
        }
        Listing removedListing = listings.remove(index);
        listingsSet.remove(removedListing);
        return removedListing;
    }

    public int size() {
        return listings.size();
    }

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
