package seedu.internbuddy.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.internbuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents an Application's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS =
        "Description can take any values, and it should not be blank";

    /*
    * The first character of the description must not be a whitespace,
    * otherwise " " (a blank string) becomes a valid input.
    */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullDescription;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        fullDescription = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return fullDescription.equals(otherDescription.fullDescription);
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }
}
