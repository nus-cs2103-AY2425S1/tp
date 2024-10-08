package seedu.address.model.rentalinformation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Deposit {
    public static final String MESSAGE_CONSTRAINTS =
            "Deposit should only contain numbers, and in 2 decimal places if needed";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d{2})?$";

    public double deposit;

    public Deposit(String deposit) {
        requireNonNull(deposit);
        checkArgument(isValidMonthlyRent(deposit), MESSAGE_CONSTRAINTS);
        this.deposit = Double.parseDouble(deposit);
    }

    public static boolean isValidMonthlyRent(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
