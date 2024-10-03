package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Property {
    private final PostalCode postalCode;
    private final Unit unit;

    /**
     * Every field must be present and not null.
     */
    public Property(PostalCode postalCode, Unit unit) {
        requireAllNonNull(postalCode, unit);
        this.postalCode = postalCode;
        this.unit = unit;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public Unit getUnit() {
        return unit;
    }

    /**
     * Returns true if both properties have the same unit number and postal code.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnit().equals(getUnit());
    }

    /**
     * Returns true if both properties have the same identity and data fields.
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

        return otherProperty != null
                && otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnit().equals(getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, unit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("postalCode", postalCode)
                .add("unit", unit)
                .toString();
    }
}