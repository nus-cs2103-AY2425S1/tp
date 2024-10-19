package seedu.address.model.person;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.tag.PropertyTagType;

/**
 * Represents a property Listing of a person in the address book.
 * Guarantees: immutable
 */
public class Listing {

    public static final String MESSAGE_CONSTRAINTS = "Listing takes a valid PropertyTagType enum and Address";

    public final PropertyTagType propertyType;
    public final Address address;

    /**
     * Constructs an {@code Listing}.
     *
     * @param propertyType Type of property, follows the propertyType enum
     * @param address The address of the property
     */
    public Listing(PropertyTagType propertyType, Address address) {
        this.propertyType = propertyType;
        this.address = address;
    }

    /**
     * Constructs an {@code Listing} - meant for use for Jackson.
     *
     * @param propertyType Type of property, follows the propertyType enum
     * @param address The address of the property
     */
    @JsonCreator
    public Listing(@JsonProperty("propertyType") String propertyType,
                   @JsonProperty("address") Address address) {
        this.propertyType = PropertyTagType.fromString(propertyType);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Type: " + propertyType.toString() + " | Address: " + address;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Listing)) {
            return false;
        }

        Listing otherListing = (Listing) other;
        return address.equals(otherListing.getAddress());
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

}
