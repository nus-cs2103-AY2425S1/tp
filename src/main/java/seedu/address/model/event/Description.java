package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's description.
 * Handles optional descriptions by defaulting to a standard message if the input is blank or null.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String DEFAULT_DESCRIPTION = "No description provided";
    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any string value, but it should not be blank";

    /*
     * The description must not be blank.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Constructs a {@code Description} with the specified value.
     * If the given value is invalid (null or blank), it defaults to a standard description.
     *
     * @param description A valid description, or null for a default description.
     */
    private Description(String description) {
        if (description == null || !isValidDescription(description)) {
            this.value = DEFAULT_DESCRIPTION;
        } else {
            this.value = description;
        }
    }

    /**
     * Factory method to create a Description.
     * This method ensures that if the provided value is null or blank, it defaults to a standard description.
     *
     * @param description A possible description.
     * @return A {@code Description} object with a valid value.
     */
    public static Description ofNullable(String description) {
        return new Description(description);
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Description)) {
            return false;
        }

        Description otherDescription = (Description) other;
        return value.equals(otherDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

