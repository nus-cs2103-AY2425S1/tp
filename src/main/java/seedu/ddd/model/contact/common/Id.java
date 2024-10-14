package seedu.ddd.model.contact.common;

import seedu.ddd.commons.util.AppUtil;

/**
 * Represents a Contact's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(int)}}
 */
public class Id {
    public static final String MESSAGE_CONSTRAINTS =
            "ID should be a non-negative integer, and it should not be blank";

    public final int id;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid id.
     */
    public Id(int id) {
        AppUtil.checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        this.id = id;
    }

    /**
     * Returns true if a given integer is a valid id.
     */
    public static boolean isValidId(int test) {
        return test >= 0;
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
