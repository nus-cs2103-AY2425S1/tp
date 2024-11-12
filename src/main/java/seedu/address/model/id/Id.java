package seedu.address.model.id;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an id in the address book.
 * Guarantees: immutable; is always valid
 */
public class Id {
    public static final String MESSAGE_CONSTRAINTS =
            "ID should only contain numeric characters";

    /*
     * Only numbers are allowed
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String value;

    /**
     * Constructs an {@code Id}.
     *
     * @param id An id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);

        // Strips leading 0's
        value = Integer.toString((Integer.parseInt(id)));
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        // Null values are illegal values that should have been handled by
        // the command parser or the storage's json converter.
        assert test != null;

        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            Integer.valueOf(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Id // instanceof handles nulls
                && value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
