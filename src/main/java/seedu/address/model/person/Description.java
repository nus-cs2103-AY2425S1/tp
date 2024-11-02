package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's description in the address book.
 * Guarantees: immutable;
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Description can take on any value or just blank,"
        + " but should not be more than 500 characters";

    /*
     * The input can take on any character, and can be empty,
     * The length of the string should not be more than 500 characters.
     */
    public static final String VALIDATION_REGEX = "^.{0,500}$";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the description is empty or blank
     */
    public boolean isBlank() {
        return value.isBlank();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return this.value.equals(otherDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
