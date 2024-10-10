package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a property that the client wants in the address book.
 * Guarantees: immutable; is always valid
 */
public class Property {

    private final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param property A valid address.
     */
    public Property(String property) {
        requireNonNull(property);
//        checkArgument(isValidProperty(property), MESSAGE_CONSTRAINTS);
        this.value = property;
    }

    /**
     * Returns true if a given string is a valid property.
     *
     */
//    public static boolean isValidProperty(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }

    public String getProperty() {
        return value;
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
        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return value.equals(otherProperty.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
