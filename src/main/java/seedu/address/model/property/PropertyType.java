package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class PropertyType {

    public static final String MESSAGE_CONSTRAINTS = "Property types can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code PropertyType}.
     *
     * @param propertyType A valid propertyTYpe.
     */
    public PropertyType(String propertyType) {
        requireNonNull(propertyType);
        checkArgument(isValidType(propertyType), MESSAGE_CONSTRAINTS);
        value = propertyType;
    }

    /**
     * Returns true if a given string is a valid property type.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PropertyType)) {
            return false;
        }

        PropertyType otherType = (PropertyType) other;
        return value.equals(otherType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
