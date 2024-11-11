package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS =
            "Patient ID can include alphanumeric characters, hyphens, forward-slashes, hashes, rounded brackets.\n"
            + "It must have at least 1 alphanumeric character and has a character limit of 36.";
    public static final String VALIDATION_REGEX = "^(?=.*[A-Za-z0-9])[A-Za-z0-9/#()-]{1,36}$";
    public final String value;

    /**
     *  Construct id
     * @param id
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);

        this.value = id;
    }

    /**
     * Returns true if the input contains at least one non-alphanumeric character.
     */
    public boolean containsSpecialChar() {
        // Regular expression to match any non-alphanumeric character
        return value.matches(".*[^a-zA-Z0-9].*");
    }

    public static boolean isValidId(String test) {
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
        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = (Id) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
