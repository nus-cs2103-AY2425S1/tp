package seedu.ddd.model.common;

import seedu.ddd.commons.util.AppUtil;
import seedu.ddd.commons.util.StringUtil;

/**
 * Represents an Id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {
    public static final String MESSAGE_CONSTRAINTS =
            "Id should be a non-negative integer, and it should not be blank";

    public final int id;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid int id.
     */
    public Id(int id) {
        AppUtil.checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
    }

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid string id.
     */
    public Id(String id) {
        AppUtil.checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = Integer.parseInt(id);
    }

    /**
     * Returns true if a given integer is a valid id.
     */
    public static boolean isValidId(int test) {
        return test >= 0;
    }

    /**
     * Returns true if a given string can be a valid id.
     */
    public static boolean isValidId(String test) {
        return StringUtil.isUnsignedInteger(test);
    }


    @Override
    public String toString() {
        return String.valueOf(id);
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
        return id == otherId.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
