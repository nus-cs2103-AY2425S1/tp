package seedu.ddd.model.event.common;

import static java.util.Objects.requireNonNull;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents the description of a {@code Event} in the add
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS =
            "The description of a event cannot be empty.";
    /*
     * Checks that there is at least one non-whitespace character
     */
    public static final String VALIDATION_REGEX = ".*\\S.*";

    public final String description;

    /**
     * Constructs a {@Description}.
     * @param description A description of a event.
     */
    public Description(String description) {
        requireNonNull(description);
        AppUtil.checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        assert !description.isEmpty();
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
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
        return description.equals(otherDescription.description);
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
