package seedu.address.model.listing;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;


/**
 * Represents a Listing in the real estate application.
 * Guarantees: all fields are present and not null, and buyers is initialized to an empty set.
 */
public class Listing {
    private final Name name;
    private final Price price;
    private final Area area;
    private final Region region;
    private final Address address;
    private final Person seller;
    private final Set<Person> buyers;

    /**
     * Constructs a {@code Listing}.
     *
     * @param address  Address of the listing.
     * @param price    Price of the listing.
     * @param area     Area of the listing in square meters.
     * @param region   Region of the listing, represented by a {@code Region} enum.
     * @param seller   Seller of the listing, represented by a {@code Person}.
     */
    public Listing(Name name, Address address, Price price, Area area, Region region, Person seller, Set<Person> buyers) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(area);
        Objects.requireNonNull(region);
        Objects.requireNonNull(address);
        Objects.requireNonNull(seller);

        this.name = name;
        this.price = price;
        this.area = area;
        this.region = region;
        this.address = address;
        this.seller = seller;
        this.buyers = buyers;
    }

    public Name getName() { return name; }
    public Price getPrice() {
        return price;
    }

    public Area getArea() {
        return area;
    }

    public Region getRegion() {
        return region;
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
                && otherListing.name == this.name
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
        return name.equals(otherListing.name) && address.equals(otherListing.address)
                && seller.equals(otherListing.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, address, area, region, seller, buyers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("address", address)
                .add("area", area)
                .add("region", region)
                .add("seller", seller)
                //.add("buyers", )
                .toString();
    }
}
