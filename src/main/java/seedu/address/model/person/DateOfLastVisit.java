package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's last visited date by the social worker in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfLastVisit(String)}
 */
public class DateOfLastVisit {

    public static final String MESSAGE_CONSTRAINTS = "Date of last visit should be in dd-MM-yyyy format.";

    public static final String VALIDATION_REGEX = "";

    public final String value;

    /**
     * Constructs a {@code DateOfLastVisit}.
     *
     * @param date A valid date.
     */
    public DateOfLastVisit(String date) {
        requireNonNull(date);
        checkArgument(isValidDateOfLastVisit(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date of last visit.
     */
    public static boolean isValidDateOfLastVisit(String test) {
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
        if (!(other instanceof DateOfLastVisit)) {
            return false;
        }

        DateOfLastVisit otherDateOfLastVisit = (DateOfLastVisit) other;
        return value.equals(otherDateOfLastVisit.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
