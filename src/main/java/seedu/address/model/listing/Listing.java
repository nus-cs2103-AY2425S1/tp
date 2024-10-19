package seedu.address.model.listing;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;


/**
 * Represents a Listing in the real estate application.
 * Guarantees: all fields are present and not null, and buyers is initialized to an empty set.
 */
public class Listing {
    private final Price price;
    private final Size size;
    private final Location location;
    private final Address address;
    private final Person seller;
    private final Set<Person> buyers;

    /**
     * Constructs a {@code Listing}.
     *
     * @param address  Address of the listing.
     * @param price    Price of the listing.
     * @param size     Size of the listing in square meters.
     * @param location Location of the listing, represented by a {@code Location} enum.
     * @param seller   Seller of the listing, represented by a {@code Person}.
     */
    public Listing(Address address, Price price, Size size, Location location, Person seller) {
        Objects.requireNonNull(price);
        Objects.requireNonNull(size);
        Objects.requireNonNull(location);
        Objects.requireNonNull(address);
        Objects.requireNonNull(seller);

        this.price = price;
        this.size = size;
        this.location = location;
        this.address = address;
        this.seller = seller;
        this.buyers = new HashSet<>();
    }

    public Price getPrice() {
        return price;
    }

    public Size getSize() {
        return size;
    }

    public Location getLocation() {
        return location;
    }

    public Address getAddress() {
        return address;
    }

    public Person getSeller() {
        return seller;
    }

    public Set<Person> getBuyers() {
        return Collections.unmodifiableSet(buyers);
    }

    /**
     * Checks if the given listing is the same as the current listing.
     * Two listings are considered the same if they have the same address and seller.
     *
     * @param otherListing The other listing to compare.
     * @return True if the listings are the same, false otherwise.
     */
    public boolean isSameListing(Listing otherListing) {
        if (this == otherListing) {
            return true;
        }

        return otherListing != null
                && otherListing.address == this.address
                && otherListing.seller == this.seller;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Listing)) {
            return false;
        }

        Listing otherListing = (Listing) other;
        return address.equals(otherListing.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, size, location, seller);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("address", address)
                .add("size", size)
                .add("location", location)
                .add("seller", seller)
                //.add("buyers", )
                .toString();
    }
}
