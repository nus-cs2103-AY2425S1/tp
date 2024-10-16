package seedu.address.model.property;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Property with various details such as address, town, type, size, and price.
 */
public class Property {
    private final String address;
    private final String town;
    private final String propertyType;
    private final double size;
    private final int numberOfBedrooms;
    private final int numberOfBathrooms;
    private final double price;
    private final Optional<String> remark;

    /**
     * Constructs a {@code Property} with the specified details.
     *
     * @param address The address of the property.
     * @param town The town where the property is located.
     * @param propertyType The type of the property (e.g., apartment, house).
     * @param size The size of the property in square meters.
     * @param numberOfBedrooms The number of bedrooms in the property.
     * @param numberOfBathrooms The number of bathrooms in the property.
     * @param price The price of the property.
     * @param remark An optional remark about the property.
     */
    public Property(String address, String town, String propertyType, double size,
                    int numberOfBedrooms, int numberOfBathrooms, double price,
                    Optional<String> remark) {
        this.address = address;
        this.town = town;
        this.propertyType = propertyType;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.price = price;
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public String getTown() {
        return town;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public double getSize() {
        return size;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public double getPrice() {
        return price;
    }

    public Optional<String> getRemark() {
        return remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        return Double.compare(property.size, size) == 0
                && numberOfBedrooms == property.numberOfBedrooms
                && numberOfBathrooms == property.numberOfBathrooms
                && Double.compare(property.price, price) == 0
                && address.equals(property.address)
                && town.equals(property.town)
                && propertyType.equals(property.propertyType)
                && remark.equals(property.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, town, propertyType, size,
                numberOfBedrooms, numberOfBathrooms, price, remark);
    }

    @Override
    public String toString() {
        return String.format("Property[address=%s, town=%s, type=%s, size=%.2f, "
                        + "bedrooms=%d, bathrooms=%d, price=%.2f, remark=%s]",
                address, town, propertyType, size,
                numberOfBedrooms, numberOfBathrooms, price,
                remark.orElse("No remark"));
    }
}
