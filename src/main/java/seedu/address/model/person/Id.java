package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS =
            "PATIENT_ID must be alphanumeric and have a specific format "
                    + "(e.g., 'P' followed by a five-digit number) - Pxxxxx.\n"
                    + "It should not include special characters or spaces (e.g., 'P123 45' would be invalid).\n";
    public static final String VALIDATION_REGEX = "^P\\d{5}$";
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
