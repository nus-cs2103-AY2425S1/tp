package seedu.address.model.listing;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Listing {
    private final Price price;
    private final Size size;
    private final Location location;
    private final Address address;
    private final Person seller;
    private final Set<Person> buyers;

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
