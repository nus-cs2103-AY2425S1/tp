package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's contact number in teletutor.
 * Guarantees: immutable; is valid as declared in {@link #isValidContact(String)}
 */
public class ContactNumber {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code ContactNumber}.
     *
     * @param contact A valid phone number.
     */
    public ContactNumber(String contact) {
        requireNonNull(contact);
        checkArgument(isValidContact(contact), MESSAGE_CONSTRAINTS);
        value = contact;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidContact(String test) {
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
        if (!(other instanceof ContactNumber otherContact)) {
            return false;
        }

        return value.equals(otherContact.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
