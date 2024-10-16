package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.property.Property;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Property}.
 */
class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String address;
    private final Double price; // Use Double for nullable price

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("address") String address,
                               @JsonProperty("price") Double price) {
        this.address = address;
        this.price = price;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        address = source.getAddress();
        price = source.getPrice();
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "address"));
        }
        if (address.isEmpty()) {
            throw new IllegalValueException("Address cannot be empty.");
        }

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "price"));
        }
        if (price < 0) {
            throw new IllegalValueException("Price cannot be negative.");
        }

        return new Property(address, price);
    }
}
