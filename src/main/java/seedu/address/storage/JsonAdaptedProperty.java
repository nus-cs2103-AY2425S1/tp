package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.person.Property;

public class JsonAdaptedProperty {

    // Fields to store property details as strings
    private final String address;
    private final String town;
    private final String propertyType;
    private final String size;
    private final String numberOfBedrooms;
    private final String numberOfBathrooms;
    private final String price;

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given details.
     *
     * @param address The address of the property.
     * @param town The town where the property is located.
     * @param propertyType The type of the property (e.g., condo, landed, HDB).
     * @param size The size of the property in square meters.
     * @param numberOfBedrooms The number of bedrooms.
     * @param numberOfBathrooms The number of bathrooms.
     * @param price The asking price of the property.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("address") String address,
                               @JsonProperty("town") String town,
                               @JsonProperty("type") String propertyType, // Changed to match JSON field
                               @JsonProperty("size") String size,
                               @JsonProperty("bedrooms") String numberOfBedrooms, // Changed to match JSON field
                               @JsonProperty("bathrooms") String numberOfBathrooms, // Changed to match JSON field
                               @JsonProperty("price") String price) {
        this.address = address;
        this.town = town;
        this.propertyType = propertyType;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.price = price;
    }

    /**
     * Constructs a {@code JsonAdaptedProperty} from a {@code Property} object.
     *
     * @param property The Property object to adapt.
     */
    public JsonAdaptedProperty(Property property) {
        this.address = property.getAddress();
        this.town = property.getTown();
        this.propertyType = property.getPropertyType();
        this.size = String.valueOf(property.getSize());
        this.numberOfBedrooms = String.valueOf(property.getNumberOfBedrooms());
        this.numberOfBathrooms = String.valueOf(property.getNumberOfBathrooms());
        this.price = String.valueOf(property.getPrice());
    }

    /**
     * Converts this JSON adapted property into a {@code Property} object.
     *
     * @return A {@code Property} object.
     * @throws IllegalArgumentException if any required field is invalid.
     */
    public Property toModelType() {
        double parsedSize = Double.parseDouble(size);
        int parsedNumberOfBedrooms = Integer.parseInt(numberOfBedrooms);
        int parsedNumberOfBathrooms = Integer.parseInt(numberOfBathrooms);
        double parsedPrice = Double.parseDouble(price);

        return Property.of(address, town, propertyType, parsedSize,
                parsedNumberOfBedrooms, parsedNumberOfBathrooms, parsedPrice);
    }
}
