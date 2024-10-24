package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Description in the system.
 * Guarantees: immutable; is valid as declared in {@link #VALIDATION_REGEX}
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values but not more than 500"
            + "characters";

    public static final String VALIDATION_REGEX = "^.{1,500}$";

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return description.equals(otherDescription.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + description + ']';
    }

    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
