package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
public class Income {

    public static final String MESSAGE_CONSTRAINTS = "Income levels cannot be negative or have a non-numerical "
            + "character.";

    public final int value;

    public Income(int value) {
        requireNonNull(value);
        this.value = value;
    }

    public static boolean isValidIncome(String incomeStringToBeTested) {
        try {
            int income = Integer.parseInt(incomeStringToBeTested);
            return income >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%d per annum", value);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == this) {
            return true;
        } else if (!(otherObj instanceof Income)) {
            return false;
        }
        return this.value == ((Income) otherObj).value;
    }

}
