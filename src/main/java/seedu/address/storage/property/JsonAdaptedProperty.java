package seedu.address.storage.property;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.Address;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Phone;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;
/**
 * Jackson-friendly version of {@link Property}.
 */
public class JsonAdaptedProperty {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String name;
    private final String phone;
    private final String address;
    private final String askingPrice;
    private final String propertyType;

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given meet up details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address, @JsonProperty("askingPrice") String askingPrice,
            @JsonProperty("propertyType") String propertyType) {

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.askingPrice = askingPrice;
        this.propertyType = propertyType;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        name = source.getName().toString();
        phone = source.getPhone().toString();
        address = source.getAddress().toString();
        askingPrice = source.getAskingPrice().toString();
        propertyType = source.getPropertyType().toString();
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        // This can only be implemented after model is refactored
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LandlordName.class.getSimpleName()));
        }
        if (!LandlordName.isValidName(name)) {
            throw new IllegalValueException(LandlordName.MESSAGE_CONSTRAINTS);
        }
        final LandlordName modelName = new LandlordName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (askingPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AskingPrice.class.getSimpleName()));
        }
        if (!AskingPrice.isValidPrice(askingPrice)) {
            throw new IllegalValueException(AskingPrice.MESSAGE_CONSTRAINTS);
        }
        final AskingPrice modelAskingPrice = new AskingPrice(askingPrice);

        if (propertyType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PropertyType.class.getSimpleName()));
        }
        if (!PropertyType.isValidType(propertyType)) {
            throw new IllegalValueException(PropertyType.MESSAGE_CONSTRAINTS);
        }
        final PropertyType modelpropertyType = new PropertyType(propertyType);

        return new Property(modelName, modelPhone, modelAddress, modelAskingPrice, modelpropertyType);
    }
}
