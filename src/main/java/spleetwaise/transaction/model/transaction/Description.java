package spleetwaise.transaction.model.transaction;

import static java.util.Objects.requireNonNull;

import spleetwaise.commons.util.AppUtil;

/**
 * Represents a Transaction's description in the transaction book. Guarantees: immutable; is valid as declared in
 * {@link #isValidDescription(String)}
 */
public class Description {

    /**
     * Maximum length of description
     */
    public static final int MAX_LENGTH = 120;

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank or more than " + MAX_LENGTH
            + " characters.";

    public final String description;

    /**
     * Constructs a {@code Description}
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        description = description.trim();
        AppUtil.checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    public static boolean isValidDescription(String testString) {
        return !testString.trim().isEmpty() && testString.trim().length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return description;
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
}

