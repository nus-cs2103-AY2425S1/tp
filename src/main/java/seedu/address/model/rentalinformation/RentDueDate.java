package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class RentDueDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Rent due date should only contain numbers in the range from 1 to 31";
    public static final String VALIDATION_REGEX = "^([1-9]|[12][0-9]|3[01])$";

    public String rentDueDate;

    public RentDueDate(String rentDueDate) {
        requireNonNull(rentDueDate);
        checkArgument(isValidDueDate(rentDueDate), MESSAGE_CONSTRAINTS);
        this.rentDueDate = rentDueDate;
    }

    public static boolean isValidDueDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
