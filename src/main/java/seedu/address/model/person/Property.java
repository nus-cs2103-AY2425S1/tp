package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * The {@code Property} class represents a property that is listed for sale.
 * Each property has an address, town, type, size, number of bedrooms, bathrooms, and a price.
 * Guarantees: immutable;
 */
public class Property {

    // Attributes of the property
    private final String address;
    private final String town;
    private final String propertyType;
    private final double size;
    private final int numberOfBedrooms;
    private final int numberOfBathrooms;
    private final double price;

    /**
     * Constructs a {@code Property} object with the specified attributes.
     * This constructor is private to encourage the use of the static factory method {@link #of}.
     *
     * @param address The address of the property.
     * @param town The town where the property is located.
     * @param propertyType The type of the property (e.g., condo, landed, HDB).
     * @param size The size of the property in square meters.
     * @param numberOfBedrooms The number of bedrooms.
     * @param numberOfBathrooms The number of bathrooms.
     * @param price The asking price of the property.
     */
    private Property(String address, String town, String propertyType,
                            double size, int numberOfBedrooms,
                            int numberOfBathrooms, double price) {
        this.address = address;
        this.town = town;
        this.propertyType = propertyType;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.price = price;
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

    /**
     * Static factory method to create a new {@code Property} instance.
     * This method is preferred over directly calling the constructor.
     *
     * @param address The address of the property.
     * @param town The town where the property is located.
     * @param propertyType The type of the property (e.g., condo, landed, HDB).
     * @param size The size of the property in square meters.
     * @param numberOfBedrooms The number of bedrooms.
     * @param numberOfBathrooms The number of bathrooms.
     * @param price The asking price of the property.
     * @return A new {@code Property} object.
     * @throws IllegalArgumentException if any required field is null or invalid.
     */
    public static Property of(String address, String town, String propertyType,
                                     double size, int numberOfBedrooms,
                                     int numberOfBathrooms, double price) {
        requireNonNull(address, "Address cannot be null.");
        requireNonNull(town, "Town cannot be null.");
        requireNonNull(propertyType, "Property type cannot be null.");

        // Additional validation for primitive fields
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0.");
        }
        if (numberOfBedrooms < 0) {
            throw new IllegalArgumentException("Number of bedrooms cannot be negative.");
        }
        if (numberOfBathrooms < 0) {
            throw new IllegalArgumentException("Number of bathrooms cannot be negative.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        return new Property(address, town, propertyType, size,
                numberOfBedrooms, numberOfBathrooms, price);
    }

    /**
     * Returns a string representation of the property.
     * The string includes the address, town, type, size, bedrooms, bathrooms, and price.
     *
     * @return A string representation of the property.
     */
    @Override
    public String toString() {
        return String.format("Property at %s, %s (%s): %.2f sqm, %d bed, %d bath - $%.2f",
                address, town, propertyType, size,
                numberOfBedrooms, numberOfBathrooms, price);
    }
}
