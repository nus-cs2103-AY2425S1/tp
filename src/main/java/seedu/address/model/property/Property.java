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
    private final Type type;

    /**
     * Every field must be present and not null.
     * If the type is landed, then unit of a landed property will always default to 00-00
     */
    public Property(PostalCode postalCode, Unit unit, Type type) {
        requireAllNonNull(postalCode, unit, type);
        this.postalCode = postalCode;
        this.type = type;
        if (this.type.isLandedType()) {
            this.unit = Unit.DEFAULT_LANDED_UNIT;
        } else {
            this.unit = unit;
        }
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public Unit getUnit() {
        return unit;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns true if either property is landed and have same postal code
     * OR is not landed and different type and have same postal code
     * OR is not landed and same type and same postal code and unit number
     * This defines a weaker notion of equality between two properties.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        } else if (otherProperty == null) {
            return false;
        }

        boolean hasLanded = this.type.equals(new Type(PropertyType.LANDED.toString()));

        if (hasLanded || !otherProperty.getType().equals(getType())) {
            return otherProperty.getPostalCode().equals(getPostalCode());
        } else {
            return otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnit().equals(getUnit());
        }
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
                && otherProperty.getUnit().equals(getUnit())
                && otherProperty.getType().equals(getType());
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
                .add("type", type)
                .toString();
    }
}
