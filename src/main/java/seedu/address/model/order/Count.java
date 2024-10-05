package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the item count of a Person's Order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCount(String)}
 */
public class Count {


    public static final String MESSAGE_CONSTRAINTS =
            "Item count should be a positive integer";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";
    public final String value;

    /**
     * Constructs a {@code Count}.
     *
     * @param count A valid item count.
     */
    public Count(String count) {
        requireNonNull(count);
        checkArgument(isValidCount(count), MESSAGE_CONSTRAINTS);
        value = count;
    }

    /**
     * Returns true if a given string is a valid count number.
     */
    public static boolean isValidCount(String test) {
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
        if (!(other instanceof Count)) {
            return false;
        }

        Count otherCount = (Count) other;
        return value.equals(otherCount.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
