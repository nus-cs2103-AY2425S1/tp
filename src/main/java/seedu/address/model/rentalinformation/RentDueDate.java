package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Rental Information's rent due date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class RentDueDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Rent due date should only contain numbers in the range from 1 to 31";
    public static final String VALIDATION_REGEX = "^([1-9]|[12][0-9]|3[01])$";

    public final String rentDueDate;

    /**
     * Constructs a {@code RentalDate}.
     *
     * @param rentDueDate A valid rent due date value.
     */
    public RentDueDate(String rentDueDate) {
        requireNonNull(rentDueDate);
        checkArgument(isValidDueDate(rentDueDate), MESSAGE_CONSTRAINTS);
        this.rentDueDate = rentDueDate;
    }

    /**
     * Validates a rent due date string against a predefined regex pattern.
     *
     * @param test The string to be validated as a rent due date.
     * @return {@code true} if the string matches the validation regex;
     *         {@code false} otherwise.
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
