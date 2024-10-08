package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class MonthlyRent {
    public static final String MESSAGE_CONSTRAINTS =
            "Monthly Rent should only contain numbers, and in 2 decimal places if needed";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{2})?$";

    public double monthlyRent;

    public MonthlyRent(String monthlyRent) {
        requireNonNull(monthlyRent);
        checkArgument(isValidMonthlyRent(monthlyRent), MESSAGE_CONSTRAINTS);
        this.monthlyRent = Double.parseDouble(monthlyRent);
    }

    public static boolean isValidMonthlyRent(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
