package seedu.address.model.patient;

import seedu.address.model.shared.Date;

/**
 * Represents a Patient's date of birth in the contacts book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateOfBirth extends Date {

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param input A valid date.
     */
    public DateOfBirth(String input) {
        super(input);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDateOfBirth = (DateOfBirth) other;
        return date.equals(otherDateOfBirth.date);
    }
}
