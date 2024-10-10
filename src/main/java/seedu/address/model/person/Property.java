package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Property {

    public static final String MESSAGE_CONSTRAINTS = "Property names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+"; // Checks if the property name is alphanumeric

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

    /**
     * Returns true if a given string is a valid property name.
     */
    public static boolean isValidPropertyName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return postalCode.equals(otherProperty.postalCode);
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
