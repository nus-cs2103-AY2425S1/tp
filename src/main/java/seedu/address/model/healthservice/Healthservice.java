package seedu.address.model.healthservice;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Health Service in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidHealthserviceName(String)}
 */
public class Healthservice {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";

    public final String healthServiceName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param healthServiceName A valid tag name.
     */
    public Healthservice(String healthServiceName) {
        requireNonNull(healthServiceName);
        this.healthServiceName = healthServiceName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidHealthserviceName(String test) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Healthservice)) {
            return false;
        }

        Healthservice otherhealthService = (Healthservice) other;
        return healthServiceName.equals(otherhealthService.healthServiceName);
    }

    @Override
    public int hashCode() {
        return healthServiceName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + healthServiceName + ']';
    }

}
