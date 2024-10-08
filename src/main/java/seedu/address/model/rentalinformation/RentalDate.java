package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.commons.util.RentalUtil;

/**
 * Represents a Rental Information's rental date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRentalDate(String)}
 */
public class RentalDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Rental start or end date should be in the format of dd/mm/yyyy";
    public static final String VALIDATION_REGEX = "^(0[0-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";

    public final LocalDate rentalDate;

    /**
     * Constructs a {@code RentalDate}.
     *
     * @param rentalDate A valid rental date value.
     */
    public RentalDate(String rentalDate) {
        requireNonNull(rentalDate);
        checkArgument(isValidRentalDate(rentalDate), MESSAGE_CONSTRAINTS);
        this.rentalDate = RentalUtil.convertStringToLocalDate(rentalDate);
    }

    /**
     * Validates a rental date string against a predefined regex pattern.
     *
     * @param test The string to be validated as a rental date.
     * @return {@code true} if the string matches the validation regex;
     *         {@code false} otherwise.
     */
    public static boolean isValidRentalDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
