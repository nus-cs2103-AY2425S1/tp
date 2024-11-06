package seedu.address.model.listing;

import java.util.Collections;
import java.util.HashSet;
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
     * @param buyers   Buyers of the listing, represented by a {@code Person}.
     */
    public Listing(Name name, Address address, Price price,
                   Area area, Region region, Person seller, Set<Person> buyers) {
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
        this.buyers = new HashSet<>(buyers);
    }

    /**
     * Copy constructor for creating a new {@code Listing} that is a copy of another {@code Listing}.
     *
     * @param other The {@code Listing} to copy.
     */
    public Listing(Listing other) {
        Objects.requireNonNull(other);

        this.name = other.name;
        this.address = other.address;
        this.price = other.price;
        this.area = other.area;
        this.region = other.region;
        this.seller = other.seller;
        this.buyers = new HashSet<>();
        this.buyers.addAll(other.buyers);
    }

    public Name getName() {
        return name;
    }

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
     * Removes a buyer from this listing's buyers.
     * @param buyer The buyer to be removed.
     */
    public void removeBuyer(Person buyer) {
        buyers.remove(buyer);
    }

    /**
     * Replaces a buyer from this listing's buyers.
     * @param buyerToRemove The buyer to be removed.
     * @param buyerToAdd The new buyer to add to the listing.
     */
    public void replaceBuyer(Person buyerToRemove, Person buyerToAdd) {
        if (buyers.contains(buyerToRemove)) {
            buyers.remove(buyerToRemove);
            buyers.add(buyerToAdd);
        }
    }

    /**
     * Returns a new Listing with a different seller
     * @param sellerToAdd The new buyer to add to the listing.
     * @return Listing with a modified seller.
     */
    public Listing modifyListingWithSeller(Person sellerToAdd) {
        return new Listing(name, address, price, area, region, sellerToAdd, buyers);
    }

    /**
     * Checks if the given listing is the same listing as the current listing.
     * Two listings are considered the same if they have the same address or name.
     *
     * @param otherListing The other listing to compare.
     * @return True if the listings are the same, false otherwise.
     */
    public boolean isSameListing(Listing otherListing) {
        if (this == otherListing) {
            return true;
        }
        return otherListing != null
                && (otherListing.address.equals(this.address)
                || otherListing.name.equals(this.name));
    }

    // CHECK LOGIC FOR THIS
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Listing)) {
            return false;
        }

        Listing otherListing = (Listing) other;
        return name.equals(otherListing.name) || address.equals(otherListing.address);
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
                .toString();
    }
}
