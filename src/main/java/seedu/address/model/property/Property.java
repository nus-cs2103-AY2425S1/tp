package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Property {

    // Identity fields
    private final LandlordName name;
    private final Phone phone;
    private final Location location;

    // Data fields
    private final AskingPrice askingPrice;
    private final PropertyType propertyType;

    /**
     * Every field must be present and not null.
     */
    public Property(LandlordName name, Phone phone, Location location,
                    AskingPrice askingPrice, PropertyType propertyType) {
        requireAllNonNull(name, phone, location, askingPrice, propertyType);
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.askingPrice = askingPrice;
        this.propertyType = propertyType;
    }

    public LandlordName getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Location getLocation() {
        return location;
    }

    public AskingPrice getAskingPrice() {
        return askingPrice;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Returns true if both properties have the same landlord name and location and property type.
     * This defines a weaker notion of equality between two properties.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getName().equals(getName())
                && otherProperty.getLocation().equals(getLocation())
                && otherProperty.getPropertyType().equals(getPropertyType());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two properties.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return name.equals(otherProperty.name)
                && phone.equals(otherProperty.phone)
                && location.equals(otherProperty.location)
                && askingPrice.equals(otherProperty.askingPrice)
                && propertyType.equals(otherProperty.propertyType);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, location, askingPrice, propertyType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("location", location)
                .add("askingPrice", askingPrice)
                .add("propertyType", propertyType)
                .toString();
    }

}
