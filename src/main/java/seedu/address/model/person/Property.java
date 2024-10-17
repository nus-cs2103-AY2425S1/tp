package seedu.address.model.person;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.tag.Tag;


/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Property {

    public static final String MESSAGE_CONSTRAINTS = "Property names should be alphanumeric";

    private final PostalCode postalCode;
    private final UnitNumber unitNumber;
    private final Price price;
    private final Set<Tag> tags;

    /**
     * Constructs a {@code Property}.
     *
     * @param postalCode A valid property name.
     */
    public Property(PostalCode postalCode, UnitNumber unitNumber, Price price, Set<Tag> tags) {
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.price = price;
        this.tags = tags;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public UnitNumber getUnitNumber() {
        return unitNumber;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the property is the same as the other property.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && this.getClass().equals(otherProperty.getClass())
                && otherProperty.getPostalCode().equals(getPostalCode())
                && otherProperty.getUnitNumber().equals(getUnitNumber());
    }

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
        return postalCode.equals(otherProperty.postalCode) && unitNumber.equals(otherProperty.unitNumber);
    }

    @Override
    public int hashCode() {
        return postalCode.hashCode();
    }
    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + postalCode + "]" + " Unit Number: " + unitNumber;
    }
}
