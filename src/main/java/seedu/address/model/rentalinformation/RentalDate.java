package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.commons.util.RentalUtil;

public class RentalDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Rental start or end date should be in the format of dd/mm/yyyy";
    public static final String VALIDATION_REGEX = "^(0[0-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d{2}$";

    public LocalDate rentalDate;

    public RentalDate(String rentalDate) {
        requireNonNull(rentalDate);
        checkArgument(isValidRentalDate(rentalDate), MESSAGE_CONSTRAINTS);
        this.rentalDate = RentalUtil.convertStringToLocalDate(rentalDate);
    }

    public static boolean isValidRentalDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
