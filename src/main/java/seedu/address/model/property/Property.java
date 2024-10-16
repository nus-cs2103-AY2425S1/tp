package seedu.address.model.property;

import java.util.Objects;

/**
 * Represents a property with an address and price.
 * This class contains methods to retrieve the property's details.
 */
public class Property {
    private final String address;
    private final double price;

    /**
     * Property constructor.
     */
    public Property(String address, double price) {
        this.address = address;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Property)) {
            return false;
        }
        Property property = (Property) other;
        return Double.compare(property.price, price) == 0 && address.equals(property.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, price);
    }

    @Override
    public String toString() {
        return String.format("Property[address=%s, price=%.2f]", address, price);
    }
}
