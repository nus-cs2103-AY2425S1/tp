package seedu.address.model.listing;

import seedu.address.model.person.Person;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Listing {
    private final Price price;
    private final Size size;
    private final Location location;
    private final Address address;
    private final Person seller;
//    private final Set<Person> buyers;

    // Constructor to initialize all fields
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
//        this.buyers = new HashSet<>(); // Initialize as empty buyers set
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

    //public Set<Person> getBuyers() {
    //  return buyers;
    //}
    //
    //// Method to add a buyer
    //public void addBuyer(Person buyer) {
    //  buyers.add(buyer);
    //}
    //
    //// Method to remove a buyer
    //public void removeBuyer(Person buyer) {
    //  buyers.remove(buyer);
    //}

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
        return Objects.hash(price, size, location, address, seller);
    }

    @Override
    public String toString() {
        return String.format("Listing at %s: %s, %s m², %s, Seller: %s",
                address, price, size.getSize(), location, seller.getName());
    }
}
