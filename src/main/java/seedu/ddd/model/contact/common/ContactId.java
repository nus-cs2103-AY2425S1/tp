package seedu.ddd.model.contact.common;

import seedu.ddd.commons.util.AppUtil;
import seedu.ddd.commons.util.StringUtil;

/**
 * Represents a Contact's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContactId(int)}}
 */
public class ContactId {
    public static final String MESSAGE_CONSTRAINTS =
            "ID should be a non-negative integer, and it should not be blank";

    public final int contactId;

    /**
     * Constructs a {@code ContactId}.
     *
     * @param contactId A valid int id.
     */
    public ContactId(int contactId) {
        AppUtil.checkArgument(isValidContactId(contactId), MESSAGE_CONSTRAINTS);
        this.contactId = contactId;
    }

    /**
     * Constructs a {@code ContactId}.
     *
     * @param contactId A valid string id.
     */
    public ContactId(String contactId) {
        AppUtil.checkArgument(isValidContactId(contactId), MESSAGE_CONSTRAINTS);
        this.contactId = Integer.parseInt(contactId);
    }

    /**
     * Returns true if a given integer is a valid id.
     */
    public static boolean isValidContactId(int test) {
        return test >= 0;
    }

    /**
     * Returns true if a given string can be a valid id.
     */
    public static boolean isValidContactId(String test) {
        return StringUtil.isUnsignedInteger(test);
    }


    @Override
    public String toString() {
        return String.valueOf(contactId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactId)) {
            return false;
        }

        ContactId otherContactId = (ContactId) other;
        return contactId == otherContactId.contactId;
    }

    @Override
    public int hashCode() {
        return contactId;
    }
}
